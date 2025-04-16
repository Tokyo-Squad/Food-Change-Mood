package org.example.logic

import org.example.model.Meal

class ExploreCountriesFoodCultureUseCase(
    private val csvRepository: CsvRepository,
) {

    fun findMealsByCountry(country: String): List<Meal> {
        val normalizedCountry = country.lowercase().trim()

        return csvRepository.getMeals()
            .filter { meal ->
                meal.name.contains(normalizedCountry, ignoreCase = true) ||
                        meal.tags.any { it.contains(normalizedCountry, ignoreCase = true) } ||
                        meal.description?.contains(normalizedCountry, ignoreCase = true) ?: false ||
                        meal.ingredients.any { it.contains(normalizedCountry, ignoreCase = true) }
            }
            .shuffled()
            .take(20)
    }
}