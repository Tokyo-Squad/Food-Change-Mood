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
            val wrongIngredients = getWrongIngredients(meals, meal, correctIngredient)

            if (wrongIngredients.size < 2) continue

            val options = prepareOptions(correctIngredient, wrongIngredients)
            val userAnswer = promptUser(meal, options)

            if (userAnswer == correctIngredient) {
                score += POINTS_PER_CORRECT
                correctCount++
            } else {
                return endGame(score, correctCount, "Wrong answer. Correct was '$correctIngredient'")
            }
        }

        return endGame(score, correctCount, "the Max correct answers reached")
    }

    private fun getWrongIngredients(meals: List<Meal>, currentMeal: Meal, correct: String): List<String> {
        return meals.asSequence()
            .flatMap { it.ingredients.asSequence() }
            .filter { it != correct && it !in currentMeal.ingredients }
            .distinct()
            .toList()
            .randomElementsUnique(2)
    }

    private fun prepareOptions(correct: String, wrong: List<String>): List<String> {
        return (wrong + correct).shuffled()
    }

    private fun endGame(score: Int, correctCount: Int, message: String): Triple<Int, Int, String> {
        return Triple(score, correctCount, message)
    }
}