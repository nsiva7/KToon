package com.amanjeet.ktoon

/**
 * Configuration options for decoding TOON format data.
 *
 * @param indent       Expected number of spaces per indentation level (default: 2)
 * @param delimiter    Expected delimiter for tabular array rows and inline
 *                     primitive arrays (default: COMMA)
 * @param lengthMarker Expected format for array length markers in headers. When
 *                     true, expects arrays rendered as [#N] instead of [N] (default: false)
 */
data class DecodeOptions(
    val indent: Int = 2,
    val delimiter: Delimiter = Delimiter.COMMA,
    val lengthMarker: Boolean = false
) {
    companion object {
        /**
         * Default decoding options: 2 spaces indent, comma delimiter, no length marker
         */
        val DEFAULT = DecodeOptions()

        /**
         * Creates DecodeOptions with custom indent, using default delimiter and length marker.
         */
        fun withIndent(indent: Int) = DecodeOptions(indent = indent)

        /**
         * Creates DecodeOptions with custom delimiter, using default indent and length marker.
         */
        fun withDelimiter(delimiter: Delimiter) = DecodeOptions(delimiter = delimiter)

        /**
         * Creates DecodeOptions with custom length marker, using default indent and delimiter.
         */
        fun withLengthMarker(lengthMarker: Boolean) = DecodeOptions(lengthMarker = lengthMarker)
    }
}