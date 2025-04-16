package org.example.logic

import org.example.model.Meal
import org.example.utils.randomElementsUnique

class PlayIngredientGameUseCase(
    private val repository: CsvRepository
) {
    companion object {
        const val WRONG_ANSWERS = 2
    }
    class PlayIngredientGameUseCase(
        private val repository: CsvRepository
    ) {
        fun playGame(
            initialScore: Int = 0,
            maxCorrectAnswers: Int = 15,
            pointsPerCorrect: Int = 1000,
            promptUser: (meal: Meal, options: List<String>) -> String?
        ): Triple<Int, Int, String> {
            val meals = repository.getMeals().filter { it.ingredients.isNotEmpty() }
            var score = initialScore
            var correctCount = 0

            while (correctCount < maxCorrectAnswers) {
                val meal = meals.random()
                val correctIngredient = meal.ingredients.random()
                val wrongIngredients = getWrongIngredients(meals, meal, correctIngredient)

                if (wrongIngredients.size < WRONG_ANSWERS) continue

                val options = prepareOptions(correctIngredient, wrongIngredients)
                val userAnswer = promptUser(meal, options)

                if (userAnswer == correctIngredient) {
                    score += pointsPerCorrect
                    correctCount++
                } else {
                    return endGame(score, correctCount, "Wrong answer. Correct was '$correctIngredient'")
                }
            }

            return endGame(score, correctCount, "The max correct answers reached")
        }

        private fun getWrongIngredients(meals: List<Meal>, currentMeal: Meal, correct: String): List<String> {
            return meals.asSequence()
                .flatMap { it.ingredients.asSequence() }
                .filter { it != correct && it !in currentMeal.ingredients }
                .distinct()
                .toList()
                .randomElementsUnique(WRONG_ANSWERS)
        }

        private fun prepareOptions(correct: String, wrong: List<String>): List<String> {
            return (wrong + correct).shuffled()
        }

        private fun endGame(score: Int, correctCount: Int, message: String): Triple<Int, Int, String> {
            return Triple(score, correctCount, message)
        }
    }
}