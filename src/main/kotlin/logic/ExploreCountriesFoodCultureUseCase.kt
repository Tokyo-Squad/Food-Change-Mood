package org.example.logic

import org.example.model.Meal
import org.example.utils.bitapFuzzySearch
import org.example.utils.preprocessForFuzzySearch

class ExploreCountriesFoodCultureUseCase(
    private val csvRepository: CsvRepository,
) {

    operator fun invoke(country: String): List<Meal> {
        println("--- please wait ---")
        val normalizedCountry = preprocessForFuzzySearch(country)
        return csvRepository.getMeals()
            .filter { meal ->
                bitapFuzzySearch(meal.name, normalizedCountry) ||
                        meal.tags.any { bitapFuzzySearch(it, normalizedCountry) } ||
                        meal.ingredients.any { bitapFuzzySearch(it, normalizedCountry) }
            }
            .take(20)
    }
}