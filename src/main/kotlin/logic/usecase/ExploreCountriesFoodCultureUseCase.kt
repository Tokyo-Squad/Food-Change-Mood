package org.example.logic.usecase

import org.example.logic.repository.MealRepository
import org.example.model.Meal
import org.example.utils.bitapFuzzySearch
import org.example.utils.preprocessForFuzzySearch

class ExploreCountriesFoodCultureUseCase(
    private val mealRepository: MealRepository,
) {

    operator fun invoke(country: String): List<Meal> {
        println("--- please wait ---")
        val normalizedCountry = preprocessForFuzzySearch(country)
        return mealRepository.getMeals()
            .filter { meal ->
                bitapFuzzySearch(meal.name, normalizedCountry) ||
                        meal.tags.any { bitapFuzzySearch(it, normalizedCountry) } ||
                        meal.ingredients.any { bitapFuzzySearch(it, normalizedCountry) }
            }
            .take(20)
    }
}