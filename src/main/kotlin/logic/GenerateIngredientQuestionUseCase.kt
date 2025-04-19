package org.example.logic

import org.example.model.Meal
import org.example.model.QuestionWithWrongAnswers
import org.example.utils.randomElementsUnique

class GenerateIngredientQuestionUseCase(
    private val repository: CsvRepository
) {
    companion object {
        private const val WRONG_ANSWERS = 2
    }

    private val meals: List<Meal> by lazy {
        repository.getMeals().filter { it.ingredients.isNotEmpty() }
    }

    operator fun invoke(): QuestionWithWrongAnswers? {
        val meal = meals.random()
        val correct = meal.ingredients.random()

        val wrong = getWrongIngredients(meals, meal, correct)

        if (wrong.size < WRONG_ANSWERS) return null

        val options = prepareOptions(correct, wrong)

        return QuestionWithWrongAnswers(meal, correct, options)
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
}