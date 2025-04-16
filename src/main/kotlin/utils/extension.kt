package org.example.utils

import org.example.model.Meal
import kotlin.random.Random

fun Meal.containAnyOf(types: List<String>): Boolean {

    return  name.containsAny(types, ignoreCase = true) ||
            description?.containsAny(types, ignoreCase = true) ?: false ||
            tags.any { tag -> tag.containsAny(types, ignoreCase = true) } ||
            ingredients.any { ingredient -> ingredient.containsAny(types, ignoreCase = true) } ||
            steps.any { step -> step.containsAny(types, ignoreCase = true) }
}

fun Meal.containAnyOf(text: String): Boolean{
    return  name.contains(text, ignoreCase = true) ||
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
fun <T> List<T>.randomElementsUnique(count: Int): List<T> {
    // If requested count is larger than list size, return the entire list
    if (this.size < count) return this

    val random = Random(System.currentTimeMillis())
    val uniqueIndices = mutableSetOf<Int>()

    // Generate unique random indices
    while (uniqueIndices.size < count) {
        val index = random.nextInt(this.size)
        uniqueIndices.add(index)
    }

    // Build result list from the unique indices
    val result = ArrayList<T>(count)
    for (index in uniqueIndices) {
        result.add(this[index])
    }

    return result
}