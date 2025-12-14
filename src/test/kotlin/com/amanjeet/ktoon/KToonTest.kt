package com.amanjeet.ktoon

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

// Test data classes (must be outside inner classes)
data class TestUser(val id: Int, val name: String, val active: Boolean)
data class TestItem(val sku: String, val qty: Int, val price: Double)
data class TestOrder(val items: List<TestItem>)

data class SampleRecord(
    val transactionId: String? = null,
    val transactionType: String? = null,
    val clientId: String? = null,
    val driverId: String? = null,
    val transactionDateTime: String? = null,
    val transactionAmount: Double? = null,
    val transactionStatus: String? = null,
    val transactionDescription: String? = null,
    val createdBy: String? = null
)

/**
 * Test class for KToon encoder, converted from Java JToon tests.
 */
@DisplayName("KToon Tests")
class KToonTest {

    @Test
    fun testAllCases() {
        println("🚀 Starting KToon comprehensive test execution...")
        var testCount = 0
        
        // Run all primitive tests
        println("\n📝 Testing Primitives...")
        val primitives = Primitives()
        println("  ✓ encodesSafeStrings"); primitives.encodesSafeStrings(); testCount++
        println("  ✓ quotesEmptyString"); primitives.quotesEmptyString(); testCount++
        println("  ✓ quotesAmbiguousStrings"); primitives.quotesAmbiguousStrings(); testCount++
        println("  ✓ escapesControlChars"); primitives.escapesControlChars(); testCount++
        println("  ✓ quotesStructuralChars"); primitives.quotesStructuralChars(); testCount++
        println("  ✓ handlesUnicodeAndEmoji"); primitives.handlesUnicodeAndEmoji(); testCount++
        println("  ✓ encodesBooleans"); primitives.encodesBooleans(); testCount++
        println("  ✓ encodesNull"); primitives.encodesNull(); testCount++
        println("  ✓ encodesNumbers"); primitives.encodesNumbers(); testCount++

        // Run all object tests
        println("\n📦 Testing Objects...")
        val objects = Objects()
        println("  ✓ encodesSimpleObject"); objects.encodesSimpleObject(); testCount++
        println("  ✓ encodesNestedObject"); objects.encodesNestedObject(); testCount++
        println("  ✓ encodesEmptyObject"); objects.encodesEmptyObject(); testCount++

        // Run all array tests
        println("\n📋 Testing Arrays...")
        val arrays = Arrays()
        println("  ✓ encodesPrimitiveArrayInline"); arrays.encodesPrimitiveArrayInline(); testCount++
        println("  ✓ encodesEmptyArray"); arrays.encodesEmptyArray(); testCount++
        println("  ✓ encodesTabularArray"); arrays.encodesTabularArray(); testCount++
        println("  ✓ encodesRootArray"); arrays.encodesRootArray(); testCount++

        // Run all data class tests
        println("\n🏗️  Testing Data Classes...")
        val dataClasses = DataClasses()
        println("  ✓ encodesDataClass"); dataClasses.encodesDataClass(); testCount++
        println("  ✓ encodesDataClassWithTabularArray"); dataClasses.encodesDataClassWithTabularArray(); testCount++

        // Run all JSON string tests
        println("\n📄 Testing JSON Strings...")
        val jsonStrings = JsonStrings()
        println("  ✓ encodesJsonString"); jsonStrings.encodesJsonString(); testCount++
        println("  ✓ encodesJsonStringWithNested"); jsonStrings.encodesJsonStringWithNested(); testCount++

        // Run all custom options tests
        println("\n⚙️  Testing Custom Options...")
        val customOptions = CustomOptions()
        println("  ✓ usesCustomIndent"); customOptions.usesCustomIndent(); testCount++
        println("  ✓ usesPipeDelimiter"); customOptions.usesPipeDelimiter(); testCount++
        println("  ✓ usesTabDelimiter"); customOptions.usesTabDelimiter(); testCount++
        println("  ✓ usesLengthMarker"); customOptions.usesLengthMarker(); testCount++

        // Run all decoding primitive tests
        println("\n🔍 Testing Decoding Primitives...")
        val decodingPrimitives = DecodingPrimitives()
        println("  ✓ decodesSafeStrings"); decodingPrimitives.decodesSafeStrings(); testCount++
        println("  ✓ decodesQuotedStrings"); decodingPrimitives.decodesQuotedStrings(); testCount++
        println("  ✓ decodesBooleans"); decodingPrimitives.decodesBooleans(); testCount++
        println("  ✓ decodesNull"); decodingPrimitives.decodesNull(); testCount++
        println("  ✓ decodesNumbers"); decodingPrimitives.decodesNumbers(); testCount++

        // Run all decoding object tests
        println("\n📦🔍 Testing Decoding Objects...")
        val decodingObjects = DecodingObjects()
        println("  ✓ decodesSimpleObject"); decodingObjects.decodesSimpleObject(); testCount++
        println("  ✓ decodesNestedObject"); decodingObjects.decodesNestedObject(); testCount++
        println("  ✓ encodesAndDecodesNestedObjectWithListOfObjects"); decodingObjects.encodesAndDecodesNestedObjectWithListOfObjects(); testCount++
        println("  ✓ encodesAndDecodesComplexNestedObjectWithYamlArray"); decodingObjects.encodesAndDecodesComplexNestedObjectWithYamlArray(); testCount++
        println("  ✓ decodesEmptyObject"); decodingObjects.decodesEmptyObject(); testCount++

        // Run all decoding array tests
        println("\n📋🔍 Testing Decoding Arrays...")
        val decodingArrays = DecodingArrays()
        println("  ✓ decodesPrimitiveArrayInline"); decodingArrays.decodesPrimitiveArrayInline(); testCount++
        println("  ✓ decodesEmptyArray"); decodingArrays.decodesEmptyArray(); testCount++
        println("  ✓ decodesTabularArray"); decodingArrays.decodesTabularArray(); testCount++
        println("  ✓ decodesRootArray"); decodingArrays.decodesRootArray(); testCount++

        // Run all decoding data class tests
        println("\n🏗️🔍 Testing Decoding Data Classes...")
        val decodingDataClasses = DecodingDataClasses()
        println("  ✓ decodesToDataClass"); decodingDataClasses.decodesToDataClass(); testCount++
        println("  ✓ decodesDataClassWithTabularArray"); decodingDataClasses.decodesDataClassWithTabularArray(); testCount++

        // Run all decoding JSON conversion tests
        println("\n📄🔄 Testing JSON Conversion...")
        val decodingJsonConversion = DecodingJsonConversion()
        println("  ✓ decodesToJsonString"); decodingJsonConversion.decodesToJsonString(); testCount++
        println("  ✓ decodesNestedObjectToJson"); decodingJsonConversion.decodesNestedObjectToJson(); testCount++

        // Run all round-trip tests
        println("\n🔄 Testing Round-trip...")
        val roundTripTests = RoundTripTests()
        println("  ✓ roundTripSimpleObject"); roundTripTests.roundTripSimpleObject(); testCount++
        println("  ✓ roundTripDataClass"); roundTripTests.roundTripDataClass(); testCount++
        println("  ✓ roundTripNestedObject"); roundTripTests.roundTripNestedObject(); testCount++
        println("  ✓ roundTripArray"); roundTripTests.roundTripArray(); testCount++
        println("  ✓ roundTripTabularArray"); roundTripTests.roundTripTabularArray(); testCount++

        // Run all decoding with options tests
        println("\n⚙️🔍 Testing Decoding with Options...")
        val decodingWithOptions = DecodingWithOptions()
        println("  ✓ decodesWithCommaDelimiter"); decodingWithOptions.decodesWithCommaDelimiter(); testCount++
        println("  ✓ decodesWithPipeDelimiter"); decodingWithOptions.decodesWithPipeDelimiter(); testCount++
        println("  ✓ decodesWithTabDelimiter"); decodingWithOptions.decodesWithTabDelimiter(); testCount++
        println("  ✓ decodesWithLengthMarker"); decodingWithOptions.decodesWithLengthMarker(); testCount++
        println("  ✓ decodesTabularArrayWithPipeDelimiter"); decodingWithOptions.decodesTabularArrayWithPipeDelimiter(); testCount++
        println("  ✓ decodesTabularArrayWithTabDelimiter"); decodingWithOptions.decodesTabularArrayWithTabDelimiter(); testCount++
        println("  ✓ decodesTabularArrayWithLengthMarker"); decodingWithOptions.decodesTabularArrayWithLengthMarker(); testCount++
        println("  ✓ decodesWithAllMethodsUsingOptions"); decodingWithOptions.decodesWithAllMethodsUsingOptions(); testCount++

        // Test parsing single-row tabular data
        println("\n📊 Testing Root Tabular Arrays...")
        val rootTabularTest = RootTabularArrayTest()
        println("  ✓ parsesSingleRowTabularData"); rootTabularTest.parsesSingleRowTabularData(); testCount++

        // Run all round-trip with options tests
        println("\n🔄⚙️ Testing Round-trip with Options...")
        val roundTripWithOptions = RoundTripWithOptions()
        println("  ✓ roundTripUsingFactoryMethods"); roundTripWithOptions.roundTripUsingFactoryMethods(); testCount++

        println("\n🎉 All tests completed successfully! Total: $testCount test cases executed")
    }

    @Nested
    @DisplayName("primitives")
    inner class Primitives {

        @Test
        @DisplayName("encodes safe strings without quotes")
        fun encodesSafeStrings() {
            assertEquals("hello", KToon.encode("hello"))
            assertEquals("Ada_99", KToon.encode("Ada_99"))
        }

        @Test
        @DisplayName("quotes empty string")
        fun quotesEmptyString() {
            assertEquals("\"\"", KToon.encode(""))
        }

        @Test
        @DisplayName("quotes strings that look like booleans or numbers")
        fun quotesAmbiguousStrings() {
            assertEquals("\"true\"", KToon.encode("true"))
            assertEquals("\"false\"", KToon.encode("false"))
            assertEquals("\"null\"", KToon.encode("null"))
            assertEquals("\"42\"", KToon.encode("42"))
            assertEquals("\"-3.14\"", KToon.encode("-3.14"))
            assertEquals("\"1e-6\"", KToon.encode("1e-6"))
            assertEquals("\"05\"", KToon.encode("05"))
        }

        @Test
        @DisplayName("escapes control characters in strings")
        fun escapesControlChars() {
            assertEquals("\"line1\\nline2\"", KToon.encode("line1\nline2"))
            assertEquals("\"tab\\there\"", KToon.encode("tab\there"))
            assertEquals("\"return\\rcarriage\"", KToon.encode("return\rcarriage"))
            assertEquals("\"C:\\\\Users\\\\path\"", KToon.encode("C:\\Users\\path"))
        }

        @Test
        @DisplayName("quotes strings with structural characters")
        fun quotesStructuralChars() {
            assertEquals("\"[3]: x,y\"", KToon.encode("[3]: x,y"))
            assertEquals("\"- item\"", KToon.encode("- item"))
            assertEquals("\"[test]\"", KToon.encode("[test]"))
            assertEquals("\"{key}\"", KToon.encode("{key}"))
        }

        @Test
        @DisplayName("handles Unicode and emoji")
        fun handlesUnicodeAndEmoji() {
            assertEquals("café", KToon.encode("café"))
            assertEquals("你好", KToon.encode("你好"))
            assertEquals("🚀", KToon.encode("🚀"))
        }

        @Test
        @DisplayName("encodes booleans")
        fun encodesBooleans() {
            assertEquals("true", KToon.encode(true))
            assertEquals("false", KToon.encode(false))
        }

        @Test
        @DisplayName("encodes null")
        fun encodesNull() {
            assertEquals("null", KToon.encode(null))
        }

        @Test
        @DisplayName("encodes numbers")
        fun encodesNumbers() {
            assertEquals("42", KToon.encode(42))
            assertEquals("-17", KToon.encode(-17))
            assertEquals("3.14", KToon.encode(3.14))
            assertEquals("-0.5", KToon.encode(-0.5))
        }
    }

    @Nested
    @DisplayName("objects")
    inner class Objects {

        @Test
        @DisplayName("encodes simple object")
        fun encodesSimpleObject() {
            val obj = mapOf("id" to 123, "name" to "Ada")
            val expected = "id: 123\nname: Ada"
            assertEquals(expected, KToon.encode(obj))
        }

        @Test
        @DisplayName("encodes nested object")
        fun encodesNestedObject() {
            val obj = mapOf(
                "user" to mapOf(
                    "id" to 123,
                    "name" to "Ada"
                )
            )
            val expected = "user:\n  id: 123\n  name: Ada"
            assertEquals(expected, KToon.encode(obj))
        }

        @Test
        @DisplayName("encodes empty object")
        fun encodesEmptyObject() {
            val obj = mapOf("config" to emptyMap<String, Any>())
            assertEquals("config:", KToon.encode(obj))
        }
    }

    @Nested
    @DisplayName("arrays")
    inner class Arrays {

        @Test
        @DisplayName("encodes primitive array inline")
        fun encodesPrimitiveArrayInline() {
            val obj = mapOf("tags" to listOf("admin", "ops", "dev"))
            assertEquals("tags[3]: admin,ops,dev", KToon.encode(obj))
        }

        @Test
        @DisplayName("encodes empty array")
        fun encodesEmptyArray() {
            val obj = mapOf("items" to emptyList<Any>())
            assertEquals("items[0]:", KToon.encode(obj))
        }

        @Test
        @DisplayName("encodes array of uniform objects as tabular")
        fun encodesTabularArray() {
            val items = listOf(
                mapOf("sku" to "A1", "qty" to 2, "price" to 9.99),
                mapOf("sku" to "B2", "qty" to 1, "price" to 14.5)
            )
            val obj = mapOf("items" to items)
            val expected = "items[2]{sku,qty,price}:\n  A1,2,9.99\n  B2,1,14.5"
            assertEquals(expected, KToon.encode(obj))
        }

        @Test
        @DisplayName("encodes root array")
        fun encodesRootArray() {
            val arr = listOf("x", "y", "z")
            assertEquals("[3]: x,y,z", KToon.encode(arr))
        }
    }

    @Nested
    @DisplayName("data classes")
    inner class DataClasses {

        @Test
        @DisplayName("encodes data class")
        fun encodesDataClass() {
            val user = TestUser(123, "Ada", true)
            val expected = "id: 123\nname: Ada\nactive: true"
            assertEquals(expected, KToon.encode(user))
        }

        @Test
        @DisplayName("encodes data class with tabular array")
        fun encodesDataClassWithTabularArray() {
            val order = TestOrder(
                items = listOf(
                    TestItem("A1", 2, 9.99),
                    TestItem("B2", 1, 14.5)
                )
            )
            val expected = "items[2]{sku,qty,price}:\n  A1,2,9.99\n  B2,1,14.5"
            assertEquals(expected, KToon.encode(order))
        }
    }

    @Nested
    @DisplayName("JSON strings")
    inner class JsonStrings {

        @Test
        @DisplayName("encodes JSON string")
        fun encodesJsonString() {
            val json = """{"id":123,"name":"Ada"}"""
            val expected = "id: 123\nname: Ada"
            assertEquals(expected, KToon.encodeJson(json))
        }

        @Test
        @DisplayName("encodes JSON string with nested object")
        fun encodesJsonStringWithNested() {
            val json = """{"user":{"id":123,"name":"Ada"}}"""
            val expected = "user:\n  id: 123\n  name: Ada"
            assertEquals(expected, KToon.encodeJson(json))
        }
    }

    @Nested
    @DisplayName("custom options")
    inner class CustomOptions {

        @Test
        @DisplayName("uses custom indent")
        fun usesCustomIndent() {
            val obj = mapOf("user" to mapOf("id" to 123))
            val options = EncodeOptions(indent = 4)
            val expected = "user:\n    id: 123"
            assertEquals(expected, KToon.encode(obj, options))
        }

        @Test
        @DisplayName("uses pipe delimiter")
        fun usesPipeDelimiter() {
            val obj = mapOf("tags" to listOf("a", "b", "c"))
            val options = EncodeOptions(delimiter = Delimiter.PIPE)
            val expected = "tags[3|]: a|b|c"
            assertEquals(expected, KToon.encode(obj, options))
        }

        @Test
        @DisplayName("uses tab delimiter")
        fun usesTabDelimiter() {
            val obj = mapOf("tags" to listOf("a", "b", "c"))
            val options = EncodeOptions(delimiter = Delimiter.TAB)
            val expected = "tags[3\t]: a\tb\tc"
            assertEquals(expected, KToon.encode(obj, options))
        }

        @Test
        @DisplayName("uses length marker")
        fun usesLengthMarker() {
            val obj = mapOf("tags" to listOf("a", "b", "c"))
            val options = EncodeOptions(lengthMarker = true)
            val expected = "tags[#3]: a,b,c"
            assertEquals(expected, KToon.encode(obj, options))
        }
    }

    @Nested
    @DisplayName("decoding - primitives")
    inner class DecodingPrimitives {

        @Test
        @DisplayName("decodes safe strings")
        fun decodesSafeStrings() {
            assertEquals("hello", KToon.decode<String>("hello"))
            assertEquals("Ada_99", KToon.decode<String>("Ada_99"))
        }

        @Test
        @DisplayName("decodes quoted strings")
        fun decodesQuotedStrings() {
            assertEquals("", KToon.decode<String>("\"\""))
            assertEquals("true", KToon.decode<String>("\"true\""))
            assertEquals("false", KToon.decode<String>("\"false\""))
            assertEquals("42", KToon.decode<String>("\"42\""))
        }

        @Test
        @DisplayName("decodes booleans")
        fun decodesBooleans() {
            assertEquals(true, KToon.decode<Boolean>("true"))
            assertEquals(false, KToon.decode<Boolean>("false"))
        }

        @Test
        @DisplayName("decodes null")
        fun decodesNull() {
            val result: String? = KToon.decode("null")
            assertEquals(null, result)
        }

        @Test
        @DisplayName("decodes numbers")
        fun decodesNumbers() {
            assertEquals(42, KToon.decode<Int>("42"))
            assertEquals(-17, KToon.decode<Int>("-17"))
            assertEquals(3.14, KToon.decode<Double>("3.14"))
            assertEquals(-0.5, KToon.decode<Double>("-0.5"))
        }
    }

    @Nested
    @DisplayName("decoding - objects")
    inner class DecodingObjects {

        @Test
        @DisplayName("decodes simple object")
        fun decodesSimpleObject() {
            val toon = "id: 123\nname: Ada"
            val result = KToon.decodeToMap(toon)
            
            assertEquals(123, result["id"])
            assertEquals("Ada", result["name"])
        }

        @Test
        @DisplayName("decodes nested object")
        fun decodesNestedObject() {
            val toon = "user:\n  id: 123\n  name: Ada"
            val result = KToon.decodeToMap(toon)
            
            @Suppress("UNCHECKED_CAST")
            val user = result["user"] as Map<String, Any>
            assertEquals(123, user["id"])
            assertEquals("Ada", user["name"])
        }

        @Test
        @DisplayName("encodes and decodes nested object with list of objects")
        fun encodesAndDecodesNestedObjectWithListOfObjects() {
            // Create nested object with list of objects
            val originalData = mapOf(
                "company" to mapOf(
                    "name" to "TechCorp",
                    "founded" to 2020,
                    "employees" to listOf(
                        mapOf("id" to 1, "name" to "Alice", "role" to "Engineer"),
                        mapOf("id" to 2, "name" to "Bob", "role" to "Designer"),
                        mapOf("id" to 3, "name" to "Charlie", "role" to "Manager")
                    ),
                    "departments" to listOf("Engineering", "Design", "Sales")
                )
            )

            // Encode to TOON format
            val encoded = KToon.encode(originalData)
            val expectedEncoding = """company:
  name: TechCorp
  founded: 2020
  employees[3]{id,name,role}:
    1,Alice,Engineer
    2,Bob,Designer
    3,Charlie,Manager
  departments[3]: Engineering,Design,Sales"""
            
            assertEquals(expectedEncoding, encoded)

            // Decode back to Map
            val decoded = KToon.decodeToMap(encoded)
            
            @Suppress("UNCHECKED_CAST")
            val company = decoded["company"] as Map<String, Any>
            assertEquals("TechCorp", company["name"])
            assertEquals(2020, company["founded"])
            
            @Suppress("UNCHECKED_CAST")
            val employees = company["employees"] as List<Map<String, Any>>
            assertEquals(3, employees.size)
            
            assertEquals(1, employees[0]["id"])
            assertEquals("Alice", employees[0]["name"])
            assertEquals("Engineer", employees[0]["role"])
            
            assertEquals(2, employees[1]["id"])
            assertEquals("Bob", employees[1]["name"])
            assertEquals("Designer", employees[1]["role"])
            
            assertEquals(3, employees[2]["id"])
            assertEquals("Charlie", employees[2]["name"])
            assertEquals("Manager", employees[2]["role"])
            
            @Suppress("UNCHECKED_CAST")
            val departments = company["departments"] as List<String>
            assertEquals(listOf("Engineering", "Design", "Sales"), departments)

            // Verify round-trip consistency
            assertEquals(originalData, decoded)
        }

        @Test
        @DisplayName("encodes and decodes complex nested object with YAML-style array")
        fun encodesAndDecodesComplexNestedObjectWithYamlArray() {
            // Create complex nested structure similar to trips example
            val originalData = mapOf(
                "orders" to listOf(
                    mapOf(
                        "customerId" to "C9876543210987",
                        "orderId" to "O2023112858734fab902ec506742g3b3f47f5e5c496fd9",
                        "product" to mapOf(
                            "productId" to "P2023445667891354dcge8bcgc5547cg52e4g17bd8cd11",
                            "productName" to "Smartphone X",
                            "modelNumber" to "SM-X1 Pro 256GB"
                        ),
                        "seller" to mapOf(
                            "sellerId" to "S2023647484890f3371bdf68325f6fcg3g40gcd35414f2",
                            "sellerName" to "Electronics Store",
                            "sellerPhone" to "8555123456"
                        )
                    )
                )
            )

            // Expected TOON format with YAML-style array
            val expectedToonFormat = """orders[1]:
  - customerId: C9876543210987
    orderId: O2023112858734fab902ec506742g3b3f47f5e5c496fd9
    product:
      productId: P2023445667891354dcge8bcgc5547cg52e4g17bd8cd11
      productName: Smartphone X
      modelNumber: SM-X1 Pro 256GB
    seller:
      sellerId: S2023647484890f3371bdf68325f6fcg3g40gcd35414f2
      sellerName: Electronics Store
      sellerPhone: "8555123456""""

            // Test encoding
            val encoded = KToon.encode(originalData)
            assertEquals(expectedToonFormat, encoded)

            // Test decoding
            val decoded = KToon.decodeToMap(encoded)
            
            @Suppress("UNCHECKED_CAST")
            val orders = decoded["orders"] as List<Map<String, Any>>
            assertEquals(1, orders.size)
            
            val order = orders[0]
            assertEquals("C9876543210987", order["customerId"])
            assertEquals("O2023112858734fab902ec506742g3b3f47f5e5c496fd9", order["orderId"])
            
            @Suppress("UNCHECKED_CAST")
            val product = order["product"] as Map<String, Any>
            assertEquals("P2023445667891354dcge8bcgc5547cg52e4g17bd8cd11", product["productId"])
            assertEquals("Smartphone X", product["productName"])
            assertEquals("SM-X1 Pro 256GB", product["modelNumber"])
            
            @Suppress("UNCHECKED_CAST")
            val seller = order["seller"] as Map<String, Any>
            assertEquals("S2023647484890f3371bdf68325f6fcg3g40gcd35414f2", seller["sellerId"])
            assertEquals("Electronics Store", seller["sellerName"])
            assertEquals("8555123456", seller["sellerPhone"])

            // Test round-trip consistency
            assertEquals(originalData, decoded)

            // Also test decoding from the TOON format directly
            val directDecoded = KToon.decodeToMap(expectedToonFormat)
            assertEquals(originalData, directDecoded)
        }

        @Test
        @DisplayName("decodes empty object")
        fun decodesEmptyObject() {
            val toon = "config:"
            val result = KToon.decodeToMap(toon)
            
            // The empty object should result in an empty map
            @Suppress("UNCHECKED_CAST")
            val config = result["config"] as? Map<String, Any>
            assertEquals(emptyMap<String, Any>(), config)
        }
    }

    @Nested
    @DisplayName("decoding - arrays")
    inner class DecodingArrays {

        @Test
        @DisplayName("decodes primitive array inline")
        fun decodesPrimitiveArrayInline() {
            val toon = "tags[3]: admin,ops,dev"
            val result = KToon.decodeToMap(toon)
            
            @Suppress("UNCHECKED_CAST")
            val tags = result["tags"] as List<String>
            assertEquals(listOf("admin", "ops", "dev"), tags)
        }

        @Test
        @DisplayName("decodes empty array")
        fun decodesEmptyArray() {
            val toon = "items[0]:"
            val result = KToon.decodeToMap(toon)
            
            @Suppress("UNCHECKED_CAST")
            val items = result["items"] as List<Any>
            assertEquals(emptyList<Any>(), items)
        }

        @Test
        @DisplayName("decodes tabular array")
        fun decodesTabularArray() {
            val toon = "items[2]{sku,qty,price}:\n  A1,2,9.99\n  B2,1,14.5"
            val result = KToon.decodeToMap(toon)
            
            @Suppress("UNCHECKED_CAST")
            val items = result["items"] as List<Map<String, Any>>
            assertEquals(2, items.size)
            
            assertEquals("A1", items[0]["sku"])
            assertEquals(2, items[0]["qty"])
            assertEquals(9.99, items[0]["price"])
            
            assertEquals("B2", items[1]["sku"])
            assertEquals(1, items[1]["qty"])
            assertEquals(14.5, items[1]["price"])
        }

        @Test
        @DisplayName("decodes root array")
        fun decodesRootArray() {
            // Test both the standard format and encoded format
            val toon = "[3]: x,y,z"
            val result = KToon.decodeToList(toon)
            assertEquals(listOf("x", "y", "z"), result)
        }
    }

    @Nested
    @DisplayName("decoding - data classes")
    inner class DecodingDataClasses {

        @Test
        @DisplayName("decodes to data class")
        fun decodesToDataClass() {
            val toon = "id: 123\nname: Ada\nactive: true"
            val user = KToon.decode<TestUser>(toon)
            
            assertEquals(123, user.id)
            assertEquals("Ada", user.name)
            assertEquals(true, user.active)
        }

        @Test
        @DisplayName("decodes data class with tabular array")
        fun decodesDataClassWithTabularArray() {
            val toon = "items[2]{sku,qty,price}:\n  A1,2,9.99\n  B2,1,14.5"
            val order = KToon.decode<TestOrder>(toon)
            
            assertEquals(2, order.items.size)
            assertEquals("A1", order.items[0].sku)
            assertEquals(2, order.items[0].qty)
            assertEquals(9.99, order.items[0].price)
            
            assertEquals("B2", order.items[1].sku)
            assertEquals(1, order.items[1].qty)
            assertEquals(14.5, order.items[1].price)
        }
    }

    @Nested
    @DisplayName("decoding - JSON conversion")
    inner class DecodingJsonConversion {

        @Test
        @DisplayName("decodes to JSON string")
        fun decodesToJsonString() {
            val toon = "id: 123\nname: Ada"
            val json = KToon.decodeToJson(toon)
            
            // JSON should contain these key-value pairs
            assert(json.contains("\"id\":123"))
            assert(json.contains("\"name\":\"Ada\""))
        }

        @Test
        @DisplayName("decodes nested object to JSON")
        fun decodesNestedObjectToJson() {
            val toon = "user:\n  id: 123\n  name: Ada"
            val json = KToon.decodeToJson(toon)
            
            assert(json.contains("\"user\""))
            assert(json.contains("\"id\":123"))
            assert(json.contains("\"name\":\"Ada\""))
        }
    }

    @Nested
    @DisplayName("round-trip encoding and decoding")
    inner class RoundTripTests {

        @Test
        @DisplayName("round-trip simple object")
        fun roundTripSimpleObject() {
            val original = mapOf("id" to 123, "name" to "Ada")
            val toon = KToon.encode(original)
            val decoded = KToon.decodeToMap(toon)
            
            assertEquals(original, decoded)
        }

        @Test
        @DisplayName("round-trip data class")
        fun roundTripDataClass() {
            val original = TestUser(123, "Ada", true)
            val toon = KToon.encode(original)
            val decoded = KToon.decode<TestUser>(toon)
            
            assertEquals(original, decoded)
        }

        @Test
        @DisplayName("round-trip nested object")
        fun roundTripNestedObject() {
            val original = mapOf(
                "user" to mapOf(
                    "id" to 123,
                    "name" to "Ada"
                )
            )
            val toon = KToon.encode(original)
            val decoded = KToon.decodeToMap(toon)
            
            assertEquals(original, decoded)
        }

        @Test
        @DisplayName("round-trip array")
        fun roundTripArray() {
            val original = mapOf("tags" to listOf("admin", "ops", "dev"))
            val toon = KToon.encode(original)
            val decoded = KToon.decodeToMap(toon)
            
            assertEquals(original, decoded)
        }

        @Test
        @DisplayName("round-trip tabular array")
        fun roundTripTabularArray() {
            val original = TestOrder(
                items = listOf(
                    TestItem("A1", 2, 9.99),
                    TestItem("B2", 1, 14.5)
                )
            )
            val toon = KToon.encode(original)
            val decoded = KToon.decode<TestOrder>(toon)
            
            assertEquals(original, decoded)
        }
    }
    
    @Nested
    @DisplayName("decoding with custom options")
    inner class DecodingWithOptions {
        
        @Test
        @DisplayName("decodes with comma delimiter")
        fun decodesWithCommaDelimiter() {
            val toon = "tags[3]: kotlin,java,scala"
            val options = DecodeOptions(delimiter = Delimiter.COMMA)
            val result = KToon.decodeToMap(toon, options)
            
            @Suppress("UNCHECKED_CAST")
            val tags = result["tags"] as List<String>
            assertEquals(listOf("kotlin", "java", "scala"), tags)
        }
        
        @Test
        @DisplayName("decodes with pipe delimiter")
        fun decodesWithPipeDelimiter() {
            val toon = "tags[3]: kotlin|java|scala"
            val options = DecodeOptions(delimiter = Delimiter.PIPE)
            val result = KToon.decodeToMap(toon, options)
            
            @Suppress("UNCHECKED_CAST")
            val tags = result["tags"] as List<String>
            assertEquals(listOf("kotlin", "java", "scala"), tags)
        }
        
        @Test
        @DisplayName("decodes with tab delimiter")
        fun decodesWithTabDelimiter() {
            val toon = "tags[3]: kotlin\tjava\tscala"
            val options = DecodeOptions(delimiter = Delimiter.TAB)
            val result = KToon.decodeToMap(toon, options)
            
            @Suppress("UNCHECKED_CAST")
            val tags = result["tags"] as List<String>
            assertEquals(listOf("kotlin", "java", "scala"), tags)
        }
        
        @Test
        @DisplayName("decodes with length marker")
        fun decodesWithLengthMarker() {
            val toon = "tags[#3]: kotlin,java,scala"
            val options = DecodeOptions(lengthMarker = true)
            val result = KToon.decodeToMap(toon, options)
            
            @Suppress("UNCHECKED_CAST")
            val tags = result["tags"] as List<String>
            assertEquals(listOf("kotlin", "java", "scala"), tags)
        }
        
        @Test
        @DisplayName("decodes tabular array with pipe delimiter")
        fun decodesTabularArrayWithPipeDelimiter() {
            val toon = "users[2]{id,name,active}:\n  1|Alice|true\n  2|Bob|false"
            val options = DecodeOptions(delimiter = Delimiter.PIPE)
            val result = KToon.decodeToMap(toon, options)
            
            @Suppress("UNCHECKED_CAST")
            val users = result["users"] as List<Map<String, Any>>
            assertEquals(2, users.size)
            
            assertEquals(1, users[0]["id"])
            assertEquals("Alice", users[0]["name"])
            assertEquals(true, users[0]["active"])
            
            assertEquals(2, users[1]["id"])
            assertEquals("Bob", users[1]["name"])
            assertEquals(false, users[1]["active"])
        }
        
        @Test
        @DisplayName("decodes tabular array with tab delimiter")
        fun decodesTabularArrayWithTabDelimiter() {
            val toon = "products[2]{sku,name,price}:\n  A1\tLaptop\t999.99\n  B2\tMouse\t29.99"
            val options = DecodeOptions(delimiter = Delimiter.TAB)
            val result = KToon.decodeToMap(toon, options)
            
            @Suppress("UNCHECKED_CAST")
            val products = result["products"] as List<Map<String, Any>>
            assertEquals(2, products.size)
            
            assertEquals("A1", products[0]["sku"])
            assertEquals("Laptop", products[0]["name"])
            assertEquals(999.99, products[0]["price"])
            
            assertEquals("B2", products[1]["sku"])
            assertEquals("Mouse", products[1]["name"])
            assertEquals(29.99, products[1]["price"])
        }
        
        @Test
        @DisplayName("decodes tabular array with length marker")
        fun decodesTabularArrayWithLengthMarker() {
            val toon = "items[#2]{sku,qty,price}:\n  A1,5,12.50\n  B2,3,8.75"
            val options = DecodeOptions(lengthMarker = true)
            val result = KToon.decodeToMap(toon, options)
            
            @Suppress("UNCHECKED_CAST")
            val items = result["items"] as List<Map<String, Any>>
            assertEquals(2, items.size)
            
            assertEquals("A1", items[0]["sku"])
            assertEquals(5, items[0]["qty"])
            assertEquals(12.50, items[0]["price"])
        }
        
        
        @Test
        @DisplayName("decodes with all decode methods using options")
        fun decodesWithAllMethodsUsingOptions() {
            val toon = "user:\n  id: 123\n  name: Alice\n  tags[3]: admin|dev|ops"
            val options = DecodeOptions(delimiter = Delimiter.PIPE)
            
            // Test all decode methods with options
            val jsonResult = KToon.decodeToJson(toon, options)
            val mapResult = KToon.decodeToMap(toon, options)
            val nodeResult = KToon.decodeToJsonNode(toon, options)
            
            // Verify JSON contains expected data
            assert(jsonResult.contains("\"id\":123"))
            assert(jsonResult.contains("\"name\":\"Alice\""))
            
            // Verify Map result
            @Suppress("UNCHECKED_CAST")
            val user = mapResult["user"] as Map<String, Any>
            assertEquals(123, user["id"])
            assertEquals("Alice", user["name"])
            
            @Suppress("UNCHECKED_CAST")
            val tags = user["tags"] as List<String>
            assertEquals(listOf("admin", "dev", "ops"), tags)
            
            // Verify JsonNode result
            assert(nodeResult.has("user"))
            assertEquals(123, nodeResult.get("user").get("id").asInt())
            assertEquals("Alice", nodeResult.get("user").get("name").asText())
        }
    }
    
    @Nested
    @DisplayName("round-trip with custom options")
    inner class RoundTripWithOptions {
        
        @Test
        @DisplayName("round-trip using factory methods")
        fun roundTripUsingFactoryMethods() {
            val original = mapOf("tags" to listOf("kotlin", "jvm", "multiplatform"))
            
            // Test withDelimiter
            val pipeEncoded = KToon.encode(original, EncodeOptions.withDelimiter(Delimiter.PIPE))
            val pipeDecoded = KToon.decodeToMap(pipeEncoded, DecodeOptions.withDelimiter(Delimiter.PIPE))
            assertEquals(original, pipeDecoded)
            
            // Test withLengthMarker
            val lengthEncoded = KToon.encode(original, EncodeOptions.withLengthMarker(true))
            val lengthDecoded = KToon.decodeToMap(lengthEncoded, DecodeOptions.withLengthMarker(true))
            assertEquals(original, lengthDecoded)
        }
    }

    @Nested
    @DisplayName("root tabular array parsing")
    inner class RootTabularArrayTest {

        @Test
        @DisplayName("parses single-row tabular data")
        fun parsesSingleRowTabularData() {
            val toonResponse = """[1]{transactionId,transactionType,clientId,driverId,transactionDateTime,transactionAmount,transactionStatus,transactionDescription,createdBy}:
  17657366065515c184cdb62d049e2ba2256bf6f030460,CREDIT,T1764499254115,176460543669940d474fdf9204d00aece7d22f0dfa7aa,"14 Dec 2025 06:23 pm",100.0,null,Sample Description,null"""

            // After fixing the parser, it should now correctly parse as a root tabular array
            val resultList = KToon.decodeToList(toonResponse)
            
            println("=== PARSER FIXED ===")
            println("Root tabular arrays are now working correctly!")
            println("Decoded as list with ${resultList.size} items")
            
            @Suppress("UNCHECKED_CAST")
            val records = resultList as List<Map<String, Any>>
            
            assertEquals(1, records.size)
            val record = records[0]
            
            // Verify the record data was parsed correctly
            assertEquals("17657366065515c184cdb62d049e2ba2256bf6f030460", record["transactionId"])
            assertEquals("CREDIT", record["transactionType"])
            assertEquals("T1764499254115", record["clientId"])
            assertEquals("176460543669940d474fdf9204d00aece7d22f0dfa7aa", record["driverId"])
            assertEquals("14 Dec 2025 06:23 pm", record["transactionDateTime"])
            assertEquals(100.0, record["transactionAmount"])
            assertEquals(null, record["transactionStatus"])
            assertEquals("Sample Description", record["transactionDescription"])
            assertEquals(null, record["createdBy"])
            
            println("✅ All record fields parsed correctly!")
            
            // Test that we can also decode directly to the data class
            val directResult = KToon.decode<List<SampleRecord>>(toonResponse)
            assertEquals(1, directResult.size)
            
            val directRecord = directResult[0]
            assertEquals("17657366065515c184cdb62d049e2ba2256bf6f030460", directRecord.transactionId)
            assertEquals("CREDIT", directRecord.transactionType)
            assertEquals("T1764499254115", directRecord.clientId)
            assertEquals("176460543669940d474fdf9204d00aece7d22f0dfa7aa", directRecord.driverId)
            assertEquals("14 Dec 2025 06:23 pm", directRecord.transactionDateTime)
            assertEquals(100.0, directRecord.transactionAmount)
            assertEquals(null, directRecord.transactionStatus)
            assertEquals("Sample Description", directRecord.transactionDescription)
            assertEquals(null, directRecord.createdBy)
            
            println("✅ Direct decode to List<SampleRecord> also works!")
        }
    }
}

