package org.example.model

data class Meal(
    val name: String,
    val id: Int,
    val preparationTime: Int,
    val contributorId: Int,
    val submitted: java.time.LocalDate,
    val tags: List<String>,
    val nutrition: Nutrition,
    val numberOfSteps: Int,
    val steps: List<String>,
    val description: String?,
    val ingredients: List<String>,
    val numberOfIngredients: Int
)

data class Nutrition(
    val calories: Double,
    val totalFat: Double,
    val sugar: Double,
    val sodium: Double,
    val protein: Double,
    val saturatedFat: Double,
    val carbohydrates: Double
)
