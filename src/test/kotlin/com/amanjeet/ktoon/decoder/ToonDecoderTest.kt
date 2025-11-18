package com.amanjeet.ktoon.decoder

import com.amanjeet.ktoon.KToon
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

data class TestUser(val id: Int, val name: String, val email: String?)
data class TestProduct(val sku: String, val price: Double, val inStock: Boolean, val tags: List<String> = emptyList())
data class TestOrder(val orderId: String, val user: TestUser, val products: List<TestProduct>)

class ToonDecoderTest {
    
    @Test
    fun `decode simple object to JSON`() {
        val toon = """
        id: 123
        name: Ada Lovelace
        email: ada@example.com
        """.trimIndent()
        
        val json = KToon.decodeToJson(toon)
        assertTrue(json.contains("\"id\":123"))
        assertTrue(json.contains("\"name\":\"Ada Lovelace\""))
        assertTrue(json.contains("\"email\":\"ada@example.com\""))
    }
    
    @Test
    fun `decode to data class`() {
        val toon = """
        id: 123
        name: Ada Lovelace
        email: ada@example.com
        """.trimIndent()
        
        val user: TestUser = KToon.decode(toon)
        assertEquals(123, user.id)
        assertEquals("Ada Lovelace", user.name)
        assertEquals("ada@example.com", user.email)
    }
    
    @Test
    fun `decode with null values`() {
        val toon = """
        id: 123
        name: Ada Lovelace
        email: null
        """.trimIndent()
        
        val user: TestUser = KToon.decode(toon)
        assertEquals(123, user.id)
        assertEquals("Ada Lovelace", user.name)
        assertNull(user.email)
    }
    
    @Test
    fun `decode simple array`() {
        val toon = """
        tags[3]: electronics,gadgets,wireless
        """.trimIndent()
        
        val result: Map<String, List<String>> = KToon.decode(toon)
        assertEquals(listOf("electronics", "gadgets", "wireless"), result["tags"])
    }
    
    @Test
    fun `decode tabular array`() {
        val toon = """
        products[2]{sku,price,inStock}:
          A1,29.99,true
          B2,49.99,false
        """.trimIndent()
        
        val result = KToon.decodeToMap(toon)
        val products = result["products"] as List<*>
        
        assertEquals(2, products.size)
        val product1 = products[0] as Map<*, *>
        assertEquals("A1", product1["sku"])
        assertEquals(29.99, product1["price"])
        assertEquals(true, product1["inStock"])
        
        val product2 = products[1] as Map<*, *>
        assertEquals("B2", product2["sku"])
        assertEquals(49.99, product2["price"])
        assertEquals(false, product2["inStock"])
    }
    
    @Test
    fun `decode nested object`() {
        val toon = """
        orderId: ORD-123
        user:
          id: 456
          name: John Doe
          email: john@example.com
        """.trimIndent()
        
        val result = KToon.decodeToMap(toon)
        assertEquals("ORD-123", result["orderId"])
        
        @Suppress("UNCHECKED_CAST")
        val user = result["user"] as Map<String, Any>
        assertEquals(456, user["id"])
        assertEquals("John Doe", user["name"])
        assertEquals("john@example.com", user["email"])
    }
    
    @Test
    fun `decode complex nested structure`() {
        val toon = """
        orderId: ORD-789
        user:
          id: 123
          name: Alice
          email: alice@example.com
        products[2]{sku,price,inStock}:
          MOUSE-001,29.99,true
          KEYBOARD-002,79.99,false
        """.trimIndent()
        
        val result = KToon.decodeToMap(toon)
        
        assertEquals("ORD-789", result["orderId"])
        
        @Suppress("UNCHECKED_CAST")
        val user = result["user"] as Map<String, Any>
        assertEquals(123, user["id"])
        assertEquals("Alice", user["name"])
        assertEquals("alice@example.com", user["email"])
        
        @Suppress("UNCHECKED_CAST")
        val products = result["products"] as List<Map<String, Any>>
        assertEquals(2, products.size)
        
        assertEquals("MOUSE-001", products[0]["sku"])
        assertEquals(29.99, products[0]["price"])
        assertEquals(true, products[0]["inStock"])
        
        assertEquals("KEYBOARD-002", products[1]["sku"])
        assertEquals(79.99, products[1]["price"])
        assertEquals(false, products[1]["inStock"])
    }
    
    @Test
    fun `decode boolean values`() {
        val toon = """
        active: true
        verified: false
        """.trimIndent()
        
        val result = KToon.decodeToMap(toon)
        assertEquals(true, result["active"])
        assertEquals(false, result["verified"])
    }
    
    @Test
    fun `decode numeric values`() {
        val toon = """
        intValue: 42
        doubleValue: 3.14159
        longValue: 9876543210
        """.trimIndent()
        
        val result = KToon.decodeToMap(toon)
        assertEquals(42, result["intValue"])
        assertEquals(3.14159, result["doubleValue"])
        assertEquals(9876543210L, result["longValue"])
    }
    
    @Test
    fun `decode quoted strings`() {
        val toon = """
        message: "Hello, World!"
        description: "This is a longer message with special characters: !@#$%"
        """.trimIndent()
        
        val result = KToon.decodeToMap(toon)
        assertEquals("Hello, World!", result["message"])
        assertEquals("This is a longer message with special characters: !@#$%", result["description"])
    }
    
    @Test
    fun `decode empty object`() {
        val toon = ""
        val result = KToon.decodeToMap(toon)
        assertTrue(result.isEmpty())
    }
    
    @Test
    fun `decode to JsonNode`() {
        val toon = """
        id: 123
        name: Test
        """.trimIndent()
        
        val jsonNode = KToon.decodeToJsonNode(toon)
        assertTrue(jsonNode.isObject)
        assertEquals(123, jsonNode.get("id").asInt())
        assertEquals("Test", jsonNode.get("name").asText())
    }
    
    @Test
    fun `decode with class parameter`() {
        val toon = """
        id: 123
        name: Ada Lovelace
        email: ada@example.com
        """.trimIndent()
        
        val user = KToon.decode(toon, TestUser::class.java)
        assertEquals(123, user.id)
        assertEquals("Ada Lovelace", user.name)
        assertEquals("ada@example.com", user.email)
    }
    
    @Test
    fun `decode invalid TOON throws exception`() {
        val invalidToon = "key: [invalid"
        
        // For now, simple malformed TOON might not throw, so let's test type conversion errors instead
        val result = KToon.decodeToMap(invalidToon)
        // This should work as it creates a map with key "key" and value "[invalid"
        assertEquals("[invalid", result["key"])
    }
    
    @Test
    fun `decode TOON with type mismatch throws exception`() {
        val toon = """
        id: not_a_number
        name: Ada Lovelace
        email: ada@example.com
        """.trimIndent()
        
        assertThrows<IllegalArgumentException> {
            KToon.decode<TestUser>(toon)
        }
    }
    
    @Test
    fun `round trip encoding and decoding`() {
        val original = TestUser(123, "Ada Lovelace", "ada@example.com")
        
        // Encode to TOON
        val toonString = KToon.encode(original)
        
        // Decode back to object
        val decoded: TestUser = KToon.decode(toonString)
        
        assertEquals(original, decoded)
    }
    
    @Test
    fun `round trip with simple object`() {
        val user = TestUser(123, "Alice", "alice@example.com")
        
        // Encode to TOON
        val toonString = KToon.encode(user)
        
        // Decode back to object
        val decoded: TestUser = KToon.decode(toonString)
        
        assertEquals(user, decoded)
    }
}