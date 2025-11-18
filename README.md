# KToon - Token-Oriented Object Notation (Kotlin Implementation)

[![Kotlin](https://img.shields.io/badge/kotlin-1.9.21-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![JitPack](https://jitpack.io/v/nsiva7/KToon.svg)](https://jitpack.io/#nsiva7/KToon)

**Token-Oriented Object Notation (TOON)** is a compact, human-readable data encoding format designed specifically for Large Language Models (LLMs) to reduce token count while maintaining readability and structure.

KToon is the **enhanced Kotlin implementation** of the TOON format, offering both encoding and decoding capabilities with idiomatic Kotlin features and seamless Java interoperability.

## What is TOON?

TOON (Token-Oriented Object Notation) addresses a critical challenge in AI development: **LLM tokens cost money**. While JSON is widely used, it's verbose and token-expensive. TOON provides the same structured data with significantly fewer tokens.

### Key TOON Features

- 💸 **Token-efficient:** 30-60% fewer tokens than JSON for uniform arrays
- 🤿 **LLM-friendly guardrails:** explicit lengths and field lists help models validate output  
- 🍱 **Minimal syntax:** removes redundant punctuation (braces, brackets, most quotes)
- 📐 **Indentation-based structure:** replaces braces with whitespace for better readability
- 🧺 **Tabular arrays:** declare keys once, then stream rows without repetition
- 🎯 **Bidirectional:** Full encode/decode support for seamless data conversion
- ☕ **Java interop:** fully compatible with Java projects

### Performance Comparison

**JSON** (verbose):
```json
{
  "friends": ["ana", "luis", "sam"],
  "hikes": [
    {"id": 1, "name": "Blue Lake Trail", "distance": 7.5, "companion": "ana"},
    {"id": 2, "name": "Ridge Overlook", "distance": 9.2, "companion": "luis"},
    {"id": 3, "name": "Wildflower Loop", "distance": 5.1, "companion": "sam"}
  ]
}
```

**TOON** (compact):
```toon
friends[3]: ana,luis,sam
hikes[3]{id,name,distance,companion}:
  1,Blue Lake Trail,7.5,ana
  2,Ridge Overlook,9.2,luis  
  3,Wildflower Loop,5.1,sam
```

**Result:** 73.9% accuracy with 39.6% fewer tokens!

## Installation

### JitPack (Recommended)

**Gradle (Kotlin DSL):**
```kotlin
repositories {
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.nsiva7:KToon:TAG")
}
```

**Gradle (Groovy DSL):**
```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.nsiva7:KToon:TAG'
}
```

**Maven:**
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.nsiva7</groupId>
    <artifactId>KToon</artifactId>
    <version>TAG</version>
</dependency>
```

> **Note:** Replace `TAG` with the latest release version from [GitHub Releases](https://github.com/nsiva7/KToon/releases)

## Quick Start

### Import KToon

```kotlin
import com.amanjeet.ktoon.KToon
```

## 🎯 Encoding Examples

### Basic Object Encoding

```kotlin
data class User(val id: Int, val name: String, val active: Boolean)

val user = User(123, "Ada", true)
val toonString = KToon.encode(user)

println(toonString)
// Output:
// id: 123
// name: Ada  
// active: true
```

### Nested Object Encoding

```kotlin
data class Address(val city: String, val country: String)
data class User(val id: Int, val name: String, val address: Address)

val user = User(123, "Alice", Address("San Francisco", "USA"))
val toonString = KToon.encode(user)

println(toonString)
// Output:
// id: 123
// name: Alice
// address:
//   city: San Francisco
//   country: USA
```

### Array Encoding

```kotlin
val tags = listOf("kotlin", "programming", "ai")
val data = mapOf("tags" to tags)
val toonString = KToon.encode(data)

println(toonString)
// Output: tags[3]: kotlin,programming,ai
```

### Tabular Array Encoding (Most Efficient!)

```kotlin
data class Product(val sku: String, val name: String, val price: Double, val inStock: Boolean)

val products = listOf(
    Product("A001", "Laptop", 999.99, true),
    Product("B002", "Mouse", 29.99, false),
    Product("C003", "Keyboard", 79.99, true)
)

val toonString = KToon.encode(mapOf("products" to products))

println(toonString)
// Output:
// products[3]{sku,name,price,inStock}:
//   A001,Laptop,999.99,true
//   B002,Mouse,29.99,false
//   C003,Keyboard,79.99,true
```

### JSON String Encoding

```kotlin
val json = """
{
  "user": {
    "id": 123,
    "name": "Bob",
    "roles": ["admin", "editor"]
  }
}
"""

val toonString = KToon.encodeJson(json)

println(toonString)
// Output:
// user:
//   id: 123
//   name: Bob
//   roles[2]: admin,editor
```

## 🔄 Decoding Examples

### Basic Object Decoding

```kotlin
val toonString = """
id: 123
name: Ada
active: true
"""

// Decode to data class
val user: User = KToon.decode<User>(toonString)
println("User: ${user.name}, ID: ${user.id}, Active: ${user.active}")

// Decode to Map
val userMap = KToon.decodeToMap(toonString)
println("Name: ${userMap["name"]}, ID: ${userMap["id"]}")

// Decode to JSON
val jsonString = KToon.decodeToJson(toonString)
println(jsonString)
// Output: {"id":123,"name":"Ada","active":true}
```

### Nested Object Decoding

```kotlin
val toonString = """
user:
  id: 123
  name: Alice
  address:
    city: San Francisco
    country: USA
"""

data class Address(val city: String, val country: String)
data class UserWithAddress(val id: Int, val name: String, val address: Address)

val user = KToon.decode<UserWithAddress>(toonString)
println("${user.name} lives in ${user.address.city}, ${user.address.country}")
```

### Array Decoding

```kotlin
val toonString = "tags[3]: kotlin,programming,ai"

// Decode to Map containing List
val data = KToon.decodeToMap(toonString)
val tags = data["tags"] as List<String>
println("Tags: ${tags.joinToString(", ")}")

// Direct decode to custom type
data class TagContainer(val tags: List<String>)
val container = KToon.decode<TagContainer>(toonString)
println("Container tags: ${container.tags}")
```

### Tabular Array Decoding

```kotlin
val toonString = """
products[3]{sku,name,price,inStock}:
  A001,Laptop,999.99,true
  B002,Mouse,29.99,false
  C003,Keyboard,79.99,true
"""

// Decode to data class with list
data class ProductCatalog(val products: List<Product>)
val catalog = KToon.decode<ProductCatalog>(toonString)

catalog.products.forEach { product ->
    println("${product.name}: $${product.price} (${if (product.inStock) "In Stock" else "Out of Stock"})")
}
```

### Root Array Decoding

```kotlin
val toonString = "[3]: apple,banana,cherry"

val fruits = KToon.decodeToList(toonString)
println("Fruits: ${fruits.joinToString(", ")}")
// Output: Fruits: apple, banana, cherry
```

### Round-Trip Conversion

```kotlin
// Start with Kotlin object
val originalUser = User(456, "Charlie", true)

// Encode to TOON
val toonString = KToon.encode(originalUser)
println("TOON: $toonString")

// Decode back to object  
val decodedUser = KToon.decode<User>(toonString)
println("Decoded: $decodedUser")

// Verify they're the same
assert(originalUser == decodedUser)
println("✅ Round-trip successful!")
```

## ⚙️ Custom Encoding Options

### Delimiter Options

```kotlin
// Import required classes
// import com.amanjeet.ktoon.EncodeOptions
// import com.amanjeet.ktoon.Delimiter

data class Item(val sku: String, val name: String, val qty: Int, val price: Double)

val data = mapOf("items" to listOf(
    Item("A1", "Widget", 2, 9.99),
    Item("B2", "Gadget", 1, 14.5)
))

// Tab delimiter
val tabOptions = EncodeOptions(delimiter = Delimiter.TAB)
println(KToon.encode(data, tabOptions))
// Output: items[2	]{sku	name	qty	price}:
//   A1	Widget	2	9.99
//   B2	Gadget	1	14.5

// Pipe delimiter  
val pipeOptions = EncodeOptions(delimiter = Delimiter.PIPE)
println(KToon.encode(data, pipeOptions))
// Output: items[2|]{sku|name|qty|price}:
//   A1|Widget|2|9.99
//   B2|Gadget|1|14.5
```

### Length Marker

```kotlin
val options = EncodeOptions(lengthMarker = true)
val data = mapOf("tags" to listOf("reading", "gaming", "coding"))

println(KToon.encode(data, options))
// Output: tags[#3]: reading,gaming,coding
```

### Custom Indentation

```kotlin
val options = EncodeOptions(indent = 4)
val data = mapOf("user" to mapOf("id" to 123, "name" to "Ada"))

println(KToon.encode(data, options))
// Output:
// user:
//     id: 123
//     name: Ada
```

## 📚 Complete API Reference

### KToon Object

```kotlin
object KToon {
    // ========== ENCODING ==========
    fun encode(input: Any?): String
    fun encode(input: Any?, options: EncodeOptions): String
    fun encodeJson(json: String): String
    fun encodeJson(json: String, options: EncodeOptions): String
    
    // ========== DECODING ==========
    fun decodeToJson(toon: String): String
    inline fun <reified T> decode(toon: String): T
    fun <T> decode(toon: String, clazz: Class<T>): T
    fun decodeToJsonNode(toon: String): JsonNode
    fun decodeToMap(toon: String): Map<String, Any?>
    fun decodeToList(toon: String): List<Any?>
}
```

### EncodeOptions

```kotlin
data class EncodeOptions(
    val indent: Int = 2,
    val delimiter: Delimiter = Delimiter.COMMA,
    val lengthMarker: Boolean = false
)

// Factory methods
EncodeOptions.withIndent(4)
EncodeOptions.withDelimiter(Delimiter.PIPE)
EncodeOptions.withLengthMarker(true)
```

### Delimiter

```kotlin
enum class Delimiter {
    COMMA,  // , (default)
    TAB,    // \t  
    PIPE    // |
}
```

## 🔄 Type Conversions

KToon automatically handles Kotlin and Java types during encoding and decoding:

| Input Type | Encoding Output | Decoding Support |
|------------|----------------|-----------------|
| Number (finite) | Decimal form; `-0` → `0`; whole numbers as integers | ✅ Int, Long, Double, Float |
| Number (`NaN`, `±Infinity`) | `null` | ✅ Handled as null |
| `BigInteger` | Integer if within Long range, otherwise string | ✅ Auto-conversion |
| `BigDecimal` | Decimal number | ✅ Precision preserved |
| `LocalDateTime` | ISO date-time string in quotes | ✅ Full temporal support |
| `LocalDate` | ISO date string in quotes | ✅ Date parsing |
| `LocalTime` | ISO time string in quotes | ✅ Time parsing |
| `ZonedDateTime` | ISO zoned date-time string in quotes | ✅ Timezone support |
| `OffsetDateTime` | ISO offset date-time string in quotes | ✅ Offset parsing |
| `Instant` | ISO instant string in quotes | ✅ Instant conversion |
| `java.util.Date` | ISO instant string in quotes | ✅ Legacy date support |
| Nullable types (`T?`) | Unwrapped value or `null` if null | ✅ Null safety preserved |
| `Sequence<T>` | Materialized to array | ✅ Collection conversion |
| `Map<String, Any>` | Object with string keys | ✅ Map reconstruction |
| `Collection`, arrays | Arrays (simple or tabular) | ✅ Type-safe collections |

## 🎯 Kotlin Features

KToon leverages Kotlin's powerful features for both encoding and decoding:

- **🏗️ Data classes:** Natural serialization and deserialization
- **🛡️ Null safety:** Native handling of nullable types (no `Optional` needed)
- **🔗 Reified generics:** Type-safe decoding with `decode<T>()`
- **📚 Extension functions:** Clean, expressive API
- **⚡ Sequences:** Efficient handling of lazy collections
- **🧠 Type inference:** Minimal boilerplate for conversions
- **🏷️ Named parameters:** Flexible configuration options

## ☕ Java Interoperability

KToon works seamlessly with Java for both encoding and decoding:

```java
// Import statements
// import com.amanjeet.ktoon.KToon;
// import com.amanjeet.ktoon.EncodeOptions;
// import com.amanjeet.ktoon.Delimiter;

// Encoding
String toonString = KToon.INSTANCE.encode(myObject);

// Encoding with options
EncodeOptions options = new EncodeOptions(2, Delimiter.COMMA, false);
String toonString = KToon.INSTANCE.encode(myObject, options);

// Decoding to Map
Map<String, Object> data = KToon.INSTANCE.decodeToMap(toonString);

// Decoding to specific class
MyClass obj = KToon.INSTANCE.decode(toonString, MyClass.class);

// Converting to JSON
String json = KToon.INSTANCE.decodeToJson(toonString);
```

## 🛠️ Development

### Building from Source

```bash
git clone https://github.com/nsiva7/KToon.git
cd KToon
./gradlew build
```

### Running Tests

```bash
# Run all tests
./gradlew test

# Run specific test classes
./gradlew test --tests "KToonTest"
./gradlew test --tests "*ToonDecoderTest*"
```

### Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📋 TOON Format Specification

### Original Specification
- **TOON Format:** Created by [Johann Schopplich](https://github.com/johannschopplich)
- **Official Specification:** [toon-format/toon](https://github.com/toon-format/toon)
- **TypeScript Implementation:** [@johannschopplich/toon](https://github.com/johannschopplich/toon)

## 🌍 Implementations in Other Languages

- **Kotlin:** [KToon](https://github.com/nsiva7/KToon) (this project)
- **Java:** [JToon](https://github.com/felipestanzani/JToon)
- **TypeScript/JavaScript:** [@johannschopplich/toon](https://github.com/johannschopplich/toon) (original)
- **Elixir:** [toon_ex](https://github.com/kentaro/toon_ex)
- **PHP:** [toon-php](https://github.com/HelgeSverre/toon-php)
- **Python:** [python-toon](https://github.com/xaviviro/python-toon) or [pytoon](https://github.com/bpradana/pytoon)
- **Ruby:** [toon-ruby](https://github.com/andrepcg/toon-ruby)
- **.NET:** [toon.NET](https://github.com/ghost1face/toon.NET)
- **Swift:** [TOONEncoder](https://github.com/mattt/TOONEncoder)
- **Go:** [gotoon](https://github.com/alpkeskin/gotoon)
- **Rust:** [toon-rs](https://github.com/JadJabbour/toon-rs)

## 📄 License

[MIT](./LICENSE) License © 2025-PRESENT [Siva Nimmala](https://github.com/nsiva7)

## 🙏 Credits

This project is an enhanced Kotlin implementation of the TOON specification originally created by [Johann Schopplich](https://github.com/johannschopplich). The enhanced version includes comprehensive decoding capabilities and extensive test coverage.

**Special thanks to:**
- [Johann Schopplich](https://github.com/johannschopplich) for creating the TOON format
- The original [KToon](https://github.com/TechnicalAmanjeet/KToon) project by [TechnicalAmanjeet](https://github.com/TechnicalAmanjeet) which served as the foundation
- The TOON community for developing implementations in multiple languages

