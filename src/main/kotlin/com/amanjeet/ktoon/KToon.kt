package com.amanjeet.ktoon

import com.fasterxml.jackson.databind.JsonNode
import com.amanjeet.ktoon.encoder.ValueEncoder
import com.amanjeet.ktoon.normalizer.JsonNormalizer
import com.amanjeet.ktoon.decoder.ToonDecoder

/**
 * Main API for encoding and decoding between Kotlin/Java objects, JSON, and TOON format.
 *
 * KToon is a structured text format that represents JSON-like data in a more
 * human-readable way, with support for tabular arrays and inline formatting.
 *
 * ## Encoding Examples:
 *
 * ```kotlin
 * // Encode a Kotlin object with default options
 * val result = KToon.encode(myObject)
 *
 * // Encode with custom options
 * val options = EncodeOptions(indent = 4, delimiter = Delimiter.PIPE, lengthMarker = true)
 * val result = KToon.encode(myObject, options)
 *
 * // Encode pre-parsed JSON
 * val json: JsonNode = objectMapper.readTree(jsonString)
 * val result = KToon.encode(json)
 *
 * // Encode a plain JSON string directly
 * val result = KToon.encodeJson("""{"id":123,"name":"Ada"}""")
 * ```
 *
 * ## Decoding Examples:
 *
 * ```kotlin
 * // Decode TOON to JSON string
 * val json = KToon.decodeToJson(toonString)
 *
 * // Decode TOON to specific data class
 * data class User(val id: Int, val name: String)
 * val user: User = KToon.decode(toonString)
 *
 * // Decode TOON to Map
 * val map: Map<String, Any?> = KToon.decodeToMap(toonString)
 * ```
 */
object KToon {
    /**
     * Encodes a Kotlin/Java object to TOON format using default options.
     *
     * The object is first normalized (Java/Kotlin types are converted to JSON-compatible
     * representations), then encoded to TOON format.
     *
     * @param input The object to encode (can be null)
     * @return The TOON-formatted string
     */
    fun encode(input: Any?): String = encode(input, EncodeOptions.DEFAULT)

    /**
     * Encodes a Kotlin/Java object to TOON format using custom options.
     *
     * The object is first normalized (Java/Kotlin types are converted to JSON-compatible
     * representations), then encoded to TOON format.
     *
     * @param input   The object to encode (can be null)
     * @param options Encoding options (indent, delimiter, length marker)
     * @return The TOON-formatted string
     */
    fun encode(input: Any?, options: EncodeOptions): String {
        val normalizedValue: JsonNode = JsonNormalizer.normalize(input)
        return ValueEncoder.encodeValue(normalizedValue, options)
    }

    /**
     * Encodes a plain JSON string to TOON format using default options.
     *
     * This is a convenience overload that parses the JSON string and encodes it
     * without requiring callers to create a [JsonNode] or intermediate objects.
     *
     * @param json The JSON string to encode (must be valid JSON)
     * @return The TOON-formatted string
     * @throws IllegalArgumentException if the input is not valid JSON
     */
    fun encodeJson(json: String): String = encodeJson(json, EncodeOptions.DEFAULT)

    /**
     * Encodes a plain JSON string to TOON format using custom options.
     *
     * Parsing is delegated to [JsonNormalizer.parse] to maintain
     * separation of concerns.
     *
     * @param json    The JSON string to encode (must be valid JSON)
     * @param options Encoding options (indent, delimiter, length marker)
     * @return The TOON-formatted string
     * @throws IllegalArgumentException if the input is not valid JSON
     */
    fun encodeJson(json: String, options: EncodeOptions): String {
        val parsed: JsonNode = JsonNormalizer.parse(json)
        return ValueEncoder.encodeValue(parsed, options)
    }

    // ==================== DECODING METHODS ====================

    /**
     * Decodes a TOON format string to a JSON string.
     *
     * This method converts TOON format back to standard JSON format.
     *
     * @param toon The TOON format string to decode
     * @return JSON string representation
     * @throws IllegalArgumentException if the TOON format is invalid
     */
    fun decodeToJson(toon: String): String = ToonDecoder.decodeToJson(toon)

    /**
     * Decodes a TOON format string to a specific type using Jackson's type conversion.
     *
     * This method uses Kotlin's reified generics to maintain type information at runtime.
     *
     * ## Usage Examples:
     *
     * ```kotlin
     * data class User(val id: Int, val name: String, val email: String)
     * val user: User = KToon.decode(toonString)
     *
     * val userList: List<User> = KToon.decode(toonString)
     * val stringMap: Map<String, String> = KToon.decode(toonString)
     * ```
     *
     * @param T The target type to decode to
     * @param toon The TOON format string to decode
     * @return Instance of type T
     * @throws IllegalArgumentException if the TOON format is invalid or cannot be converted to T
     */
    inline fun <reified T> decode(toon: String): T = ToonDecoder.decode<T>(toon)

    /**
     * Decodes a TOON format string to a specific class type.
     *
     * This is useful when you don't have reified type information at compile time,
     * such as when working with dynamic class loading or reflection.
     *
     * @param toon The TOON format string to decode
     * @param clazz The target class type
     * @return Instance of the target type
     * @throws IllegalArgumentException if the TOON format is invalid or cannot be converted
     */
    fun <T> decode(toon: String, clazz: Class<T>): T = ToonDecoder.decode(toon, clazz)

    /**
     * Decodes a TOON format string to a JsonNode for advanced manipulation.
     *
     * This is useful when you need to manipulate the parsed structure before
     * converting it to a specific type, or when working with dynamic JSON structures.
     *
     * @param toon The TOON format string to decode
     * @return JsonNode representation
     * @throws IllegalArgumentException if the TOON format is invalid
     */
    fun decodeToJsonNode(toon: String): JsonNode = ToonDecoder.decodeToJsonNode(toon)

    /**
     * Decodes a TOON format string to a generic Map<String, Any?>.
     *
     * This is useful for dynamic processing when you don't know the exact structure
     * at compile time, or when you want to work with the data as a simple map.
     *
     * @param toon The TOON format string to decode
     * @return Map representation of the TOON data
     * @throws IllegalArgumentException if the TOON format is invalid
     */
    fun decodeToMap(toon: String): Map<String, Any?> = ToonDecoder.decodeToMap(toon)

    /**
     * Decodes a TOON format string to a List when the root structure is an array.
     *
     * This method should be used when the TOON string represents an array at the root level.
     *
     * @param toon The TOON format string to decode
     * @return List representation of the TOON data
     * @throws IllegalArgumentException if the TOON format is invalid or root is not an array
     */
    fun decodeToList(toon: String): List<Any?> = ToonDecoder.decodeToList(toon)
}

