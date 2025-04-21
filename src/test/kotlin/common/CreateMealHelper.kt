package common

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.model.Meal
import org.example.model.Nutrition

fun createMeal(
    id: Int,
    name: String = "Test Meal $id",
    preparationTime: Int = 30,
    contributorId: Int = 1,
    submitted: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    tags: List<String> = emptyList(),
    numberOfSteps: Int = 1,
    steps: List<String> = listOf("Test step"),
    description: String? = "Test description",
    ingredients: List<String> = emptyList(),
    numberOfIngredients: Int = ingredients.size,
    nutrition: Nutrition = createNutrition(
        calories = 100.0,
        totalFat = 10.0,
        sugar = 5.0,
        sodium = 200.0,
        protein = 15.0,
        saturatedFat = 2.0,
        carbohydrates = 30.0
    )
): Meal {
    return Meal(
        name = name,
        id = id,
        preparationTime = preparationTime,
        contributorId = contributorId,
        submitted = submitted,
        tags = tags,
        nutrition = nutrition,
        numberOfSteps = numberOfSteps,
        steps = steps,
        description = description,
        ingredients = ingredients,
        numberOfIngredients = numberOfIngredients
    )
}

fun createNutrition(
    calories: Double = 100.0,
    totalFat: Double = 10.0,
    sugar: Double = 5.0,
    sodium: Double = 200.0,
    protein: Double = 15.0,
    saturatedFat: Double = 2.0,
    carbohydrates: Double = 30.0
): Nutrition {
    return Nutrition(
        calories = calories,
        totalFat = totalFat,
        sugar = sugar,
        sodium = sodium,
        protein = protein,
        saturatedFat = saturatedFat,
        carbohydrates = carbohydrates
    )
}