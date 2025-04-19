package org.example.presentation

import org.example.logic.KetoDietMealHelperUseCase

fun getKetoDietHelperConsole(ketoDietMealHelperUseCase: KetoDietMealHelperUseCase) {
    while (true) {
        val randomMeal = ketoDietMealHelperUseCase()

        println("Random Keto Diet:\n")
        viewMoreDetailsAboutSpecificMealConsole(randomMeal)
        println(
            """
            1- Like (continue)
            2- Dislike (show another keto diet)
            3- Exit
            """.trimIndent()
        )

        when (readLine()) {
            "1" -> {
                println("Meal liked! Continuing...")
                ketoDietMealHelperUseCase.like(meal = randomMeal)
                return
            }

            "2" -> {
                ketoDietMealHelperUseCase.dislike(randomMeal)
                println("Meal disliked. Fetching another suggestion...")
            }

            "3" -> {
                println("Exiting...")
                return
            }

            else -> println("Please enter a valid option (1, 2, or 3)")
        }
    }
}