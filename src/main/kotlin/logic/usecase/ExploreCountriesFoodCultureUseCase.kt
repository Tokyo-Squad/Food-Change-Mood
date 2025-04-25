package org.example.logic.usecase

import org.example.logic.repository.MealRepository
import org.example.model.Meal
import org.example.utils.allSearchFields
import org.example.utils.toWords

class ExploreCountriesFoodCultureUseCase(
    private val mealRepository: MealRepository,
) {
    operator fun invoke(country: String): List<Meal> {
        if (country.isBlank()) return emptyList()

        val searchTerm = country.trim().lowercase()
        return mealRepository.getMeals()
            .filter { meal -> matchesCountry(meal, searchTerm) }
            .take(20)
    }

    private fun matchesCountry(meal: Meal, searchTerm: String): Boolean {
        return meal.allSearchFields().any { text ->
            text.toWords().any { word ->
                word == searchTerm || (searchTerm.length >= 3 && word.contains(searchTerm))
            }
        }
    }

}