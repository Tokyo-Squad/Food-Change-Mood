package org.example.utiles

import org.example.model.Nutrition

fun formatNutrition(nutrition: Nutrition): String {
    return """
            Calories: ${nutrition.calories} kcal
            Protein: ${nutrition.protein}g
            Carbs: ${nutrition.carbohydrates}g
            Fat: ${nutrition.totalFat}g
        """.trimIndent()
}

fun String.capitalizeWords() = split(" ").joinToString(" ") { it.capitalize() }
fun String.capitalizeFirstLetter() = replaceFirstChar { it.uppercase() }