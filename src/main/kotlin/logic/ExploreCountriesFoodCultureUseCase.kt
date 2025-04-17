package org.example.logic

import org.example.model.Meal
import org.example.utils.containAnyOf
import org.example.utils.randomElementsUnique

class ExploreCountriesFoodCultureUseCase(
    private val csvRepository: CsvRepository,
) {

    fun invoke(country: String): List<Meal> {
        val normalizedCountry = country.lowercase().trim()

        return csvRepository.getMeals()
            .filter { meal ->
                meal.containAnyOf(normalizedCountry)
            }.randomElementsUnique(20)
    }
}