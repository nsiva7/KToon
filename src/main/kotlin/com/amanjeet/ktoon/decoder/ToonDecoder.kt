package com.amanjeet.ktoon.decoder

import com.amanjeet.ktoon.DecodeOptions
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

/**
 * Main decoder interface for converting TOON format to various target types.
 * 
 * This class provides high-level decoding functionality, delegating the actual
 * TOON parsing to ToonParser and using Jackson for type conversion.
 */
object ToonDecoder {
    @JvmStatic
    val mapper = ObjectMapper().registerKotlinModule()
    
    /**
     * Decodes a TOON format string to a JSON string using default options.
     * 
     * @param toon The TOON format string to decode
     * @return JSON string representation
     * @throws IllegalArgumentException if the TOON format is invalid
     */
    fun decodeToJson(toon: String): String = decodeToJson(toon, DecodeOptions.DEFAULT)
    
    /**
     * Decodes a TOON format string to a JSON string using custom options.
     * 
     * @param toon The TOON format string to decode
     * @param options Decoding options (indent, delimiter, length marker)
     * @return JSON string representation
     * @throws IllegalArgumentException if the TOON format is invalid
     */
    fun decodeToJson(toon: String, options: DecodeOptions): String {
        val jsonNode = ToonParser.parse(toon, options)
        return mapper.writeValueAsString(jsonNode)
    }
    
    /**
     * Decodes a TOON format string to a specific type using Jackson's type conversion and default options.
     * 
     * Usage examples:
     * ```kotlin
     * data class User(val id: Int, val name: String)
     * val user: User = ToonDecoder.decode(toonString)
     * 
     * val userList: List<User> = ToonDecoder.decode(toonString)
     * val userMap: Map<String, Any> = ToonDecoder.decode(toonString)
     * ```
     * 
     * @param T The target type to decode to
     * @param toon The TOON format string to decode
     * @return Instance of type T
     * @throws IllegalArgumentException if the TOON format is invalid or cannot be converted to T
     */
    inline fun <reified T> decode(toon: String): T = decode<T>(toon, DecodeOptions.DEFAULT)
    
    /**
     * Decodes a TOON format string to a specific type using Jackson's type conversion and custom options.
     * 
     * @param T The target type to decode to
     * @param toon The TOON format string to decode
     * @param options Decoding options (indent, delimiter, length marker)
     * @return Instance of type T
     * @throws IllegalArgumentException if the TOON format is invalid or cannot be converted to T
     */
    inline fun <reified T> decode(toon: String, options: DecodeOptions): T {
        val jsonNode = ToonParser.parse(toon, options)
        return try {
            mapper.readValue<T>(mapper.writeValueAsString(jsonNode))
        } catch (e: Exception) {
            throw IllegalArgumentException("Cannot convert TOON to type ${T::class.simpleName}: ${e.message}", e)
        }
    }
    
    /**
     * Decodes a TOON format string to a specific class type using default options.
     * 
     * This is useful when you don't have reified type information at compile time.
     * 
     * @param toon The TOON format string to decode
     * @param clazz The target class type
     * @return Instance of the target type
     * @throws IllegalArgumentException if the TOON format is invalid or cannot be converted
     */
    fun <T> decode(toon: String, clazz: Class<T>): T = decode(toon, clazz, DecodeOptions.DEFAULT)
    
    /**
     * Decodes a TOON format string to a specific class type using custom options.
     * 
     * @param toon The TOON format string to decode
     * @param clazz The target class type
     * @param options Decoding options (indent, delimiter, length marker)
     * @return Instance of the target type
     * @throws IllegalArgumentException if the TOON format is invalid or cannot be converted
     */
    fun <T> decode(toon: String, clazz: Class<T>, options: DecodeOptions): T {
        val jsonNode = ToonParser.parse(toon, options)
        return try {
            mapper.readValue(mapper.writeValueAsString(jsonNode), clazz)
        } catch (e: Exception) {
            throw IllegalArgumentException("Cannot convert TOON to type ${clazz.simpleName}: ${e.message}", e)
        }
    }
    
    /**
     * Decodes a TOON format string to a JsonNode for advanced manipulation using default options.
     * 
     * @param toon The TOON format string to decode
     * @return JsonNode representation
     * @throws IllegalArgumentException if the TOON format is invalid
     */
    fun decodeToJsonNode(toon: String): JsonNode = decodeToJsonNode(toon, DecodeOptions.DEFAULT)
    
    /**
     * Decodes a TOON format string to a JsonNode for advanced manipulation using custom options.
     * 
     * @param toon The TOON format string to decode
     * @param options Decoding options (indent, delimiter, length marker)
     * @return JsonNode representation
     * @throws IllegalArgumentException if the TOON format is invalid
     */
    fun decodeToJsonNode(toon: String, options: DecodeOptions): JsonNode {
        return ToonParser.parse(toon, options)
    }
    
    /**
     * Decodes a TOON format string to a generic Map<String, Any> using default options.
     * 
     * This is useful for dynamic processing when you don't know the exact structure.
     * 
     * @param toon The TOON format string to decode
     * @return Map representation of the TOON data
     * @throws IllegalArgumentException if the TOON format is invalid
     */
    fun decodeToMap(toon: String): Map<String, Any?> = decodeToMap(toon, DecodeOptions.DEFAULT)
    
    /**
     * Decodes a TOON format string to a generic Map<String, Any> using custom options.
     * 
     * @param toon The TOON format string to decode
     * @param options Decoding options (indent, delimiter, length marker)
     * @return Map representation of the TOON data
     * @throws IllegalArgumentException if the TOON format is invalid
     */
    fun decodeToMap(toon: String, options: DecodeOptions): Map<String, Any?> {
        return decode<Map<String, Any?>>(toon, options)
    }
    
    /**
     * Decodes a TOON format string to a List when the root structure is an array using default options.
     * 
     * @param toon The TOON format string to decode
     * @return List representation of the TOON data
     * @throws IllegalArgumentException if the TOON format is invalid or root is not an array
     */
    fun decodeToList(toon: String): List<Any?> = decodeToList(toon, DecodeOptions.DEFAULT)
    
    /**
     * Decodes a TOON format string to a List when the root structure is an array using custom options.
     * 
     * @param toon The TOON format string to decode
     * @param options Decoding options (indent, delimiter, length marker)
     * @return List representation of the TOON data
     * @throws IllegalArgumentException if the TOON format is invalid or root is not an array
     */
    fun decodeToList(toon: String, options: DecodeOptions): List<Any?> {
        return decode<List<Any?>>(toon, options)
    }
}