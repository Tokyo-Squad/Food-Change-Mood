package org.example.utils

object FuzzySearch {
    private const val ALPHABET_SIZE = 256

    fun bitapSearch(text: String, pattern: String): Boolean {
        val m = pattern.length
        val mask = LongArray(ALPHABET_SIZE) { 0L }
        val patternMask = 1L shl (m - 1)


        for (i in 0 until m) {
            mask[pattern[i].code] = mask[pattern[i].code] or (1L shl i)
        }

        var current = 0L
        for (element in text) {
            current = ((current shl 1) or 1L) and mask[element.code]
            if (current and patternMask != 0L) return true
        }

        return false
    }

    fun preprocessString(str: String): String {
        return str.lowercase()
            .replace(Regex("[^a-z0-9]"), "")
    }
}