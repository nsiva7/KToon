package com.amanjeet.ktoon.decoder

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
     * Decodes a TOON format string to a JSON string.
     * 
     * @param toon The TOON format string to decode
     * @return JSON string representation
     * @throws IllegalArgumentException if the TOON format is invalid
     */
    fun decodeToJson(toon: String): String {
        val jsonNode = ToonParser.parse(toon)
        return mapper.writeValueAsString(jsonNode)
    }
    
    /**
     * Decodes a TOON format string to a specific type using Jackson's type conversion.
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
    inline fun <reified T> decode(toon: String): T {
        val jsonNode = ToonParser.parse(toon)
        return try {
            mapper.readValue<T>(mapper.writeValueAsString(jsonNode))
        } catch (e: Exception) {
            throw IllegalArgumentException("Cannot convert TOON to type ${T::class.simpleName}: ${e.message}", e)
        }
    }
    
    /**
     * Decodes a TOON format string to a specific class type.
     * 
     * This is useful when you don't have reified type information at compile time.
     * 
     * @param toon The TOON format string to decode
     * @param clazz The target class type
     * @return Instance of the target type
     * @throws IllegalArgumentException if the TOON format is invalid or cannot be converted
     */
    fun <T> decode(toon: String, clazz: Class<T>): T {
        val jsonNode = ToonParser.parse(toon)
        return try {
            mapper.readValue(mapper.writeValueAsString(jsonNode), clazz)
        } catch (e: Exception) {
            throw IllegalArgumentException("Cannot convert TOON to type ${clazz.simpleName}: ${e.message}", e)
        }
    }
    
    /**
     * Decodes a TOON format string to a JsonNode for advanced manipulation.
     * 
     * @param toon The TOON format string to decode
     * @return JsonNode representation
     * @throws IllegalArgumentException if the TOON format is invalid
     */
    fun decodeToJsonNode(toon: String): JsonNode {
        return ToonParser.parse(toon)
    }
    
    /**
     * Decodes a TOON format string to a generic Map<String, Any>.
     * 
     * This is useful for dynamic processing when you don't know the exact structure.
     * 
     * @param toon The TOON format string to decode
     * @return Map representation of the TOON data
     * @throws IllegalArgumentException if the TOON format is invalid
     */
    fun decodeToMap(toon: String): Map<String, Any?> {
        return decode<Map<String, Any?>>(toon)
    }
    
    /**
     * Decodes a TOON format string to a List when the root structure is an array.
     * 
     * @param toon The TOON format string to decode
     * @return List representation of the TOON data
     * @throws IllegalArgumentException if the TOON format is invalid or root is not an array
     */
    fun decodeToList(toon: String): List<Any?> {
        return decode<List<Any?>>(toon)
    }
}