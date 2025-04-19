package org.example.utils

/**
 * Bitap fuzzy search algorithm implementation
 * Returns true if the pattern is found approximately in the text
 */
fun bitapFuzzySearch(text: String, pattern: String): Boolean {
    require(pattern.isNotEmpty()) { "Pattern cannot be empty" }

    val mask = createBitmask(pattern)
    val patternMask = 1L shl (pattern.length - 1)

    return text.fold(0L) { current, char ->
        ((current shl 1) or 1L) and mask[char.code].also {
            if (it and patternMask != 0L) return true
        }
    }.let { false }
}

/**
 * Creates the bitmask array for Bitap algorithm
 */
private fun createBitmask(pattern: String): LongArray {
    return LongArray(256).apply {
        pattern.forEachIndexed { i, char ->
            this[char.code] = this[char.code] or (1L shl i)
        }
    }
}

/**
 * Normalizes strings for fuzzy matching:
 * 1. Lowercases the string
 * 2. Removes non-alphanumeric characters
 */
fun preprocessForFuzzySearch(str: String): String =
    str.lowercase()
        .replace(Regex("[^a-z0-9]"), "")