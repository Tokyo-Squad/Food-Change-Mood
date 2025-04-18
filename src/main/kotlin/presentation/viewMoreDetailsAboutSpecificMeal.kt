import org.example.model.Meal

fun viewMoreDetailsAboutSpecificMeal(meal: Meal) {
    println(
        """
        === MEAL: ${meal.name} ===
        ID: ${meal.id} | Time: ${meal.preparationTime}mins | Date: ${meal.submitted}
        Description: ${meal.description ?: "N/A"}
        
        Ingredients (${meal.numberOfIngredients}): ${meal.ingredients.joinToString(", ")}
        Steps (${meal.numberOfSteps}): ${meal.steps.joinToString(" → ")}
        Tags: ${meal.tags.joinToString(", ")}
        
        Nutrition: 
        Cal: ${formatNumber(meal.nutrition.calories)}kcal | Prot: ${formatNumber(meal.nutrition.protein)}g
        Carbs: ${formatNumber(meal.nutrition.carbohydrates)}g | Fat: ${formatNumber(meal.nutrition.totalFat)}g
        Sugar: ${formatNumber(meal.nutrition.sugar)}g | Sodium: ${formatNumber(meal.nutrition.sodium)}mg
        ===============================
    """.trimIndent()
    )
}

private fun formatNumber(number: Double) = String.format("%.1f", number)