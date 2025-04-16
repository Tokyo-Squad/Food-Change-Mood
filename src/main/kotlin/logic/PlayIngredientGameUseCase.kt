package org.example.logic

import org.example.model.Meal
import org.example.utils.randomElementsUnique

class PlayIngredientGameUseCase(
    private val repository: CsvRepository
) {
    companion object {
        const val MAX_CORRECT_ANSWERS = 15
        const val POINTS_PER_CORRECT = 1000
    }

    fun playGame(
        promptUser: (meal: Meal, options: List<String>) -> String?
    ): Triple<Int, Int, String> {
        val meals = repository.getMeals().filter { it.ingredients.isNotEmpty() }
        var score = 0
        var correctCount = 0

        while (correctCount < MAX_CORRECT_ANSWERS) {
            val meal = meals.random()
            val correctIngredient = meal.ingredients.random()

            // Combined filter to exclude current meal and correct ingredient
            val wrongIngredients = meals.asSequence().flatMap { otherMeal ->
                otherMeal.ingredients.asSequence().filter { otherMeal.id != meal.id && it != correctIngredient }
            }.distinct().toList().randomElementsUnique(2)

            // Ensure we always get two wrong ingredients
            if (wrongIngredients.size < 2) continue

            // Shuffle the options to include both correct and wrong ingredients
            val options = (wrongIngredients + correctIngredient)
            val userAnswer = promptUser(meal, options) // Get the user's answer

            if (userAnswer == correctIngredient) {
                score += POINTS_PER_CORRECT
                correctCount++
            } else {
                return Triple(score, correctCount, "Wrong answer. Correct was '$correctIngredient'")
            }
        }

        return Triple(score, correctCount, "the Max correct answers reached")
    }
}