package org.example.logic.usecase

import org.example.logic.repository.MealRepository
import org.example.model.Meal
import org.example.model.QuestionWithWrongAnswers
import org.example.utils.MealAppException
import org.example.utils.randomElementsUnique

class GenerateIngredientQuestionUseCase(
    private val repository: MealRepository
) {
    companion object {
        private const val WRONG_ANSWERS = 2
    }

    private val meals: List<Meal> by lazy {
        repository.getMeals().filter { it.ingredients.isNotEmpty() }
    }

    operator fun invoke(): QuestionWithWrongAnswers {

        if (meals.isEmpty()) {
            throw MealAppException.NoMealsAvailableException("No meals available in the repository.")
        }
        val meal = meals.random()
        val correct = meal.ingredients.random()

        val wrong = getWrongIngredients(meals, meal, correct)

        if (wrong.size < WRONG_ANSWERS) {
            throw MealAppException.InsufficientWrongAnswersException("Not enough wrong ingredient options.")
        }
        val options = prepareOptions(correct, wrong)

        return QuestionWithWrongAnswers(meal, correct, options)
    }

    private fun getWrongIngredients(meals: List<Meal>, currentMeal: Meal, correct: String): List<String> {
        return meals.asSequence()
            .flatMap { it.ingredients}
            .filter { it != correct && it !in currentMeal.ingredients }
            .distinct()
            .toList()
            .randomElementsUnique(WRONG_ANSWERS)
    }

    private fun prepareOptions(correct: String, wrong: List<String>): List<String> {
        return (wrong + correct).shuffled()
    }
}