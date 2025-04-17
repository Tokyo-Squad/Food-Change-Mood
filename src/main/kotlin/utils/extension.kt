package org.example.utils

import org.example.model.Meal

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