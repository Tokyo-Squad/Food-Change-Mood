package org.example.presentation.controller

import org.example.logic.KetoDietMealHelperUseCase
import org.example.presentation.io.ConsoleIO
import viewMoreDetailsAboutSpecificMeal

class KetoDietController(
    private val io: ConsoleIO,
    private val ketoDietMealHelperUseCase: KetoDietMealHelperUseCase
) {
    fun display() {
        while (true) {
            val randomMeal = ketoDietMealHelperUseCase()
            io.printOutput("Random Keto Diet:\n")
            viewMoreDetailsAboutSpecificMeal(randomMeal)
            io.printOutput(
                """
1- Like (continue)
2- Dislike (show another keto diet)
3- Exit
""".trimIndent()
            )
            when (io.readInput("Enter your choice: ")) {
                "1" -> {
                    io.printOutput("Meal liked! Continuing...")
                    ketoDietMealHelperUseCase.like(randomMeal)
                    return
                }
                "2" -> {
                    ketoDietMealHelperUseCase.dislike(randomMeal)
                    io.printOutput("Meal disliked. Fetching another suggestion...")
                }
                "3" -> {
                    io.printOutput("Exiting...")
                    return
                }
                else -> io.printOutput("Please enter a valid option (1, 2, or 3)")
            }
        }
    }
}