package org.example.logic

import org.example.model.IngredientGameResult
import org.example.model.Meal

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

        while (correctCount < maxCorrectAnswers) {
            val question = generateQuestionUseCase() ?: continue

            val userAnswer = promptUser(question.meal, question.options)

            if (userAnswer == question.correctAnswer) {
                score += pointsPerCorrect
                correctCount++
            } else {
                return IngredientGameResult(score, correctCount, "Wrong answer. Correct was '${question.correctAnswer}'")
            }
        }

        return IngredientGameResult(score, correctCount, "The max correct answers reached.")
    }
}
