package org.example.utiles

import org.example.model.FormattedMeal
import org.example.model.Meal

class MealFormatter {

    fun format(meal: Meal): FormattedMeal {
        return FormattedMeal(
            name = meal.name.capitalizeWords(),
            preparationTime = "${meal.preparationTime} mins",
            ingredients = meal.ingredients.joinToString("\n- ", "- "),
            steps = meal.steps.mapIndexed { index, step ->
                "${index + 1}. ${step.trim().capitalizeFirstLetter()}"
            }.joinToString("\n"),
            nutritionSummary = formatNutrition(meal.nutrition),
            tags = meal.tags.filterNot { it.contains("-") || it == "easy" }
                .joinToString(", ") { it.replace("-", " ").capitalize() }
        )

    }

}