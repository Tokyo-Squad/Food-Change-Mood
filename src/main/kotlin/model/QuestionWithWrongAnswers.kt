package org.example.model

data class QuestionWithWrongAnswers(
    val meal: Meal,
    val correctAnswer: String,
    val options: List<String>
)