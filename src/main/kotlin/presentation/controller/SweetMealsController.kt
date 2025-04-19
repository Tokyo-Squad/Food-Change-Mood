package org.example.presentation.controller

import org.example.logic.SweetMealWithoutEggUseCase
import org.example.presentation.io.ConsoleIO
import viewMoreDetailsAboutSpecificMeal

class SweetMealsController(
    private val io: ConsoleIO,
    private val useCase: SweetMealWithoutEggUseCase
) {
    fun displayEggFreeSweets() {
        io.printOutput("\n=== Egg-Free Sweets Finder ===")
        while (true) {
            try {
                val sweet = useCase.getRandomsEggFreeSweet()
                io.printOutput("${sweet.name} | ${sweet.description ?: "No description"} ")
                val input = io.readInput("Like (L) to see details, Dislike (D) for another, Exit (E): ")?.uppercase()

                when (input) {
                    "L" -> {
                        // Option to display more details
                        viewMoreDetailsAboutSpecificMeal(sweet)
                        useCase.like(sweet)
                        return
                    }
                    "D" -> {
                        useCase.dislike(sweet)
                        io.printOutput("Finding another sweet...")
                    }
                    "E" -> return
                    else -> io.printOutput("Invalid input! Try again.")
                }
            } catch (e: NoSuchElementException) {
                io.printOutput("No more egg-free sweets available.")
                return
            }
        }
    }
}
