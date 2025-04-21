package org.example.logic.usecase

import org.example.model.IngredientGameResult
import org.example.model.Meal
import org.example.utils.MealAppException

class PlayIngredientGameUseCase(
    private val generateQuestionUseCase: GenerateIngredientQuestionUseCase
) {
    operator fun invoke(
        initialScore: Int = 0,
        maxCorrectAnswers: Int = 15,
        pointsPerCorrect: Int = 1000,
        promptUser: (meal: Meal, options: List<String>) -> String?
    ): IngredientGameResult {
        var score = initialScore
        var correctCount = 0
        var retries = 0
        val maxRetries = 10

        while (correctCount < maxCorrectAnswers) {
            try {
                val question = generateQuestionUseCase()
                val userAnswer = promptUser(question.meal, question.options)

                if (userAnswer == question.correctAnswer) {
                    score += pointsPerCorrect
                    correctCount++
                } else {
                    return IngredientGameResult(score, correctCount, "Wrong answer. Correct was '${question.correctAnswer}'")
                }

            } catch (e: MealAppException.NoMealsAvailableException) {
                return IngredientGameResult(
                    score,
                    correctCount,
                    "Game stopped: ${e.message}"
                )
            } catch (e: MealAppException.InsufficientWrongAnswersException) {
                retries++
                if (retries >= maxRetries) {
                    return IngredientGameResult(
                        score,
                        correctCount,
                        "Game stopped: Too many failed attempts to generate a valid question."
                    )
                }
                continue
            }
        }

        return IngredientGameResult(score, correctCount, "The max correct answers reached.")
    }
}