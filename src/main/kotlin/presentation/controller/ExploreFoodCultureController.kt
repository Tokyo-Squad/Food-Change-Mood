package org.example.presentation.controller

import org.example.logic.usecase.ExploreCountriesFoodCultureUseCase
import org.example.presentation.io.ConsoleIO

class ExploreFoodCultureController(
    private val io: ConsoleIO,
    private val useCase: ExploreCountriesFoodCultureUseCase
) {
    fun display() {
        val countryName = io.readInput("\n=== Food Culture Explorer ===\nEnter country name: ") ?: return
        val meals = useCase.invoke(countryName)
        if (meals.isEmpty()) {
            io.printOutput("No meals found for this country.")
            return
        }
        io.printOutput("\nFound ${meals.size} meals:")
        meals.forEachIndexed { index, meal ->
            io.printOutput("${index + 1}. ${meal.name} | ${meal.preparationTime}mins")
        }
    }
}