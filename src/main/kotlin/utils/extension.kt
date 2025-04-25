package org.example.utils

import org.example.model.Meal
import kotlin.random.Random

fun Meal.containAnyOf(types: List<String>): Boolean {

    return name.containsAny(types, ignoreCase = true) ||
            description?.containsAny(types, ignoreCase = true) ?: false ||
            tags.any { tag -> tag.containsAny(types, ignoreCase = true) } ||
            ingredients.any { ingredient -> ingredient.containsAny(types, ignoreCase = true) } ||
            steps.any { step -> step.containsAny(types, ignoreCase = true) }
}

fun Meal.containAnyOf(text: String): Boolean {
    return name.contains(text, ignoreCase = true) ||
            description?.contains(text, ignoreCase = true) ?: false ||
            tags.any { tag -> tag.contains(text, ignoreCase = true) } ||
            ingredients.any { ingredient -> ingredient.contains(text, ignoreCase = true) } ||
            steps.any { step -> step.contains(text, ignoreCase = true) }
}

private fun String.containsAny(keywords: List<String>, ignoreCase: Boolean): Boolean {
    return keywords.any { keyword ->
        if (ignoreCase) this.contains(keyword, ignoreCase = true)
        else this.contains(keyword)
    }
}

/**
 * Returns a specified number of random elements from the list without repetition.
 * This method is optimized for cases where only a small subset of elements is needed
 * from a potentially large list.
 *
 * @param count The number of random elements to return
 * @return A list containing random elements from the original list
 */
fun <T> List<T>.randomElementsUnique(count: Int): List<T> {

    if (this.size < count) return this

    val random = Random(System.currentTimeMillis())
    val uniqueIndices = generateUniqueRandomIndices(count, random, this.size)

    val result = ArrayList<T>(count)
    for (index in uniqueIndices) {
        result.add(this[index])
    }

    return result
}

/**
 * Helper function.
 * Generates a set of [count] distinct random integers in the range [0, listSize).
 *
 * @param count     Number of unique indices to generate.
 * @param random    Source of randomness.
 * @param listSize  Exclusive upper bound for generated indices.
 * @return Set of unique indices.
 */
private fun generateUniqueRandomIndices(
    count: Int,
    random: Random,
    listSize: Int
): Set<Int> {
    val indices = mutableSetOf<Int>()
    while (indices.size < count) {
        indices.add(random.nextInt(listSize))
    }
    return indices
}

fun Meal.allSearchFields(): List<String> =
    listOf(name) + tags + ingredients

fun String.toWords(): List<String> =
    lowercase().split(Regex("\\s+"))

