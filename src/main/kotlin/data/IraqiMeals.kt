package org.example.data

import org.example.model.Meal

class IraqiMeals {

    fun isIraqiMeal(meal: Meal): Boolean {
        val hasIraqiTag = meal.tags.any { it.equals("iraqi", ignoreCase = true) }
        val containsIraqInDescription = meal.description?.equals("iraq", ignoreCase = true) ?: false
        return hasIraqiTag || containsIraqInDescription
    }
    fun getAllIraqiMeals(meals: List<Meal>): List<Meal> {
        return meals.filter { val meal = isIraqiMeal(it)
            meal
        }
    }
}
