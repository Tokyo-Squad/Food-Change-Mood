package org.example.presentation

import org.example.logic.SweetMealWithoutEggUseCase

fun getSweetsWithNoEggsConsole(useCase: SweetMealWithoutEggUseCase) {
    println("\n=== Egg-Free Sweets Finder ===")

    while (true) {
        try {
            val sweet = useCase.getRandomsEggFreeSweet()
            println("${sweet.name} | ${sweet.description ?: "No description"}")

            print("Like (L) to see details, Dislike (D) for another, Exit (E): ")
            when (readLine()?.uppercase()) {
                "L" -> {
                    viewMoreDetailsAboutSpecificMealConsole(sweet)
                    useCase.like(meal = sweet)
                    return
                }

                "D" -> {
                    useCase.dislike(sweet)
                    println("Finding another sweet...")
                }

                "E" -> return
                else -> println("Invalid input! Try again.")
            }
        } catch (e: NoSuchElementException) {
            println("No more egg-free sweets available.")
            return
        }
    }
}