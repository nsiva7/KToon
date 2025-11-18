package com.amanjeet.ktoon.decoder

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

/**
 * Core TOON format parser that converts TOON strings to JsonNode structures.
 * 
 * This parser handles the TOON format grammar:
 * - Objects: key: value or key:\n  indented content
 * - Arrays: key[N]: value1,value2,... or key[N]:\n  item1\n  item2
 * - Tabular arrays: key[N]{col1,col2}: val1,val2\n  val3,val4
 * - Primitives: strings, numbers, booleans, null
 */
object ToonParser {
    private val mapper = ObjectMapper().registerKotlinModule()
    
    /**
     * Parses a TOON format string into a JsonNode structure.
     * 
     * @param toon The TOON format string to parse
     * @return JsonNode representing the parsed structure
     * @throws IllegalArgumentException if the TOON format is invalid
     */
    fun parse(toon: String): JsonNode {
        if (toon.isBlank()) {
            return mapper.createObjectNode()
        }
        
        val lines = toon.lines()
        val context = ParseContext(lines)
        
        // Check if this is a root array (starts with [n]:)
        val firstLine = context.currentLine().trim()
        if (firstLine.matches(Regex("""\[\d*\]:\s*.*"""))) {
            return parseRootArray(context)
        }
        
        return parseValue(context, 0)
    }
    
    private fun parseRootArray(context: ParseContext): JsonNode {
        val line = context.currentLine().trim()
        val arrayPattern = Regex("""\[(\d*)\]:\s*(.*)""")
        val match = arrayPattern.matchEntire(line)
        
        if (match != null) {
            val valuePart = match.groupValues[2]
            
            val array = mapper.createArrayNode()
            
            if (valuePart.isNotEmpty()) {
                // Inline array values
                splitValues(valuePart).forEach { value ->
                    array.add(parsePrimitive(value))
                }
            }
            
            return array
        }
        
        return mapper.createArrayNode()
    }
    
    private fun parseValue(context: ParseContext, baseIndent: Int): JsonNode {
        val line = context.currentLine()
        
        // If this is a primitive value without a key, parse it directly
        if (!line.contains(':') || line.trim().startsWith('-')) {
            return parsePrimitive(line.trim())
        }
        
        // Parse as object or array
        return parseObject(context, baseIndent)
    }
    
    private fun parseObject(context: ParseContext, baseIndent: Int): ObjectNode {
        val obj = mapper.createObjectNode()
        
        while (context.hasMore() && getIndentation(context.currentLine()) >= baseIndent) {
            val line = context.currentLine().trim()
            
            if (line.isEmpty() || line.startsWith("#")) {
                context.advance()
                continue
            }
            
            val colonIndex = line.indexOf(':')
            if (colonIndex == -1) {
                context.advance()
                continue
            }
            
            val keyPart = line.substring(0, colonIndex).trim()
            val valuePart = line.substring(colonIndex + 1).trim()
            
            val (key, arrayInfo) = parseKey(keyPart)
            
            context.advance()
            
            val value = when {
                valuePart.isNotEmpty() -> {
                    // Inline value
                    if (arrayInfo != null) {
                        parseInlineArray(valuePart, arrayInfo)
                    } else {
                        parsePrimitive(valuePart)
                    }
                }
                arrayInfo != null -> {
                    // Multi-line array (could be empty)
                    parseMultiLineArray(context, getIndentation(context.previousLine()) + 2, arrayInfo)
                }
                else -> {
                    // Multi-line object or empty object
                    val currentIndent = getIndentation(context.previousLine())
                    if (!context.hasMore() || getIndentation(context.currentLine()) <= currentIndent) {
                        // Empty object - no indented content follows
                        mapper.createObjectNode()
                    } else {
                        // Multi-line object with content
                        parseValue(context, currentIndent + 2)
                    }
                }
            }
            
            obj.set<JsonNode>(key, value)
        }
        
        return obj
    }
    
    private fun parseKey(keyPart: String): Pair<String, ArrayInfo?> {
        val arrayPattern = Regex("""(.+)\[([^}]*)\](\{([^}]+)\})?""")
        val match = arrayPattern.matchEntire(keyPart)
        
        return if (match != null) {
            val key = match.groupValues[1]
            val lengthPart = match.groupValues[2]
            val columnsPart = match.groupValues[4].takeIf { it.isNotEmpty() }
            
            val length = when {
                lengthPart.startsWith("#") -> lengthPart.substring(1).toIntOrNull()
                lengthPart.isEmpty() -> null
                else -> lengthPart.toIntOrNull()
            }
            
            val columns = columnsPart?.split(",")?.map { it.trim() }
            
            key to ArrayInfo(length, columns)
        } else {
            keyPart to null
        }
    }
    
    private fun parseInlineArray(valuePart: String, arrayInfo: ArrayInfo): ArrayNode {
        val array = mapper.createArrayNode()
        
        if (arrayInfo.columns != null) {
            // Tabular array - single row
            val values = splitValues(valuePart)
            val obj = mapper.createObjectNode()
            arrayInfo.columns.forEachIndexed { index, column ->
                if (index < values.size) {
                    obj.set<JsonNode>(column, parsePrimitive(values[index]))
                }
            }
            array.add(obj)
        } else {
            // Simple array
            splitValues(valuePart).forEach { value ->
                array.add(parsePrimitive(value))
            }
        }
        
        return array
    }
    
    private fun parseMultiLineArray(context: ParseContext, baseIndent: Int, arrayInfo: ArrayInfo): ArrayNode {
        val array = mapper.createArrayNode()
        
        while (context.hasMore() && getIndentation(context.currentLine()) >= baseIndent) {
            val line = context.currentLine().trim()
            
            if (line.isEmpty()) {
                context.advance()
                continue
            }
            
            if (arrayInfo.columns != null) {
                // Tabular array
                val values = splitValues(line)
                val obj = mapper.createObjectNode()
                arrayInfo.columns.forEachIndexed { index, column ->
                    if (index < values.size) {
                        obj.set<JsonNode>(column, parsePrimitive(values[index]))
                    }
                }
                array.add(obj)
            } else {
                // Simple array or nested objects
                if (line.contains(':')) {
                    // Nested object
                    val nestedObj = parseObject(context, getIndentation(context.currentLine()))
                    array.add(nestedObj)
                    continue
                } else {
                    // Simple value
                    array.add(parsePrimitive(line))
                }
            }
            
            context.advance()
        }
        
        return array
    }
    
    private fun parsePrimitive(value: String): JsonNode {
        val trimmed = value.trim()
        
        return when {
            trimmed == "null" -> mapper.nullNode()
            trimmed == "true" -> mapper.nodeFactory.booleanNode(true)
            trimmed == "false" -> mapper.nodeFactory.booleanNode(false)
            trimmed.startsWith('"') && trimmed.endsWith('"') -> {
                mapper.nodeFactory.textNode(trimmed.substring(1, trimmed.length - 1))
            }
            trimmed.toIntOrNull() != null -> mapper.nodeFactory.numberNode(trimmed.toInt())
            trimmed.toLongOrNull() != null -> mapper.nodeFactory.numberNode(trimmed.toLong())
            trimmed.toDoubleOrNull() != null -> mapper.nodeFactory.numberNode(trimmed.toDouble())
            else -> mapper.nodeFactory.textNode(trimmed)
        }
    }
    
    private fun splitValues(line: String): List<String> {
        val values = mutableListOf<String>()
        var current = StringBuilder()
        var inQuotes = false
        var i = 0
        
        while (i < line.length) {
            val char = line[i]
            
            when {
                char == '"' -> {
                    inQuotes = !inQuotes
                    current.append(char)
                }
                char == ',' && !inQuotes -> {
                    values.add(current.toString().trim())
                    current.clear()
                }
                char == '\t' && !inQuotes -> {
                    values.add(current.toString().trim())
                    current.clear()
                }
                char == '|' && !inQuotes -> {
                    values.add(current.toString().trim())
                    current.clear()
                }
                else -> current.append(char)
            }
            
            i++
        }
        
        if (current.isNotEmpty()) {
            values.add(current.toString().trim())
        }
        
        return values
    }
    
    private fun getIndentation(line: String): Int {
        return line.takeWhile { it.isWhitespace() }.length
    }
    
    private data class ArrayInfo(
        val length: Int?,
        val columns: List<String>?
    )
    
    private class ParseContext(private val lines: List<String>) {
        private var index = 0
        
        fun hasMore(): Boolean = index < lines.size
        
        fun currentLine(): String = if (hasMore()) lines[index] else ""
        
        fun previousLine(): String = if (index > 0) lines[index - 1] else ""
        
        fun advance() {
            index++
        }
    }
}