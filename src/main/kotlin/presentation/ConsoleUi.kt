package org.example.presentation

import org.example.logic.GetMealByIdUseCase
import org.example.logic.GetMealsByAddDateUseCase
import org.example.logic.HighCalorieMealSuggestionUseCase

fun getMealsByDateConsole(getMealsByAddDateUseCase: GetMealsByAddDateUseCase) {
    println("Enter a date (yyyy-MM-dd) to search for meals:")
    val dateInput = readLine() ?: ""
    val result = getMealsByAddDateUseCase(dateInput)
    result.onSuccess {
        println("Meals added on $dateInput:")
        result.getOrNull()?.forEach { (id, name) -> println("ID: $id, Name: $name") }
    }.onFailure {
        println(result.exceptionOrNull()?.message)
    }
}

fun getMealByIdConsole(getMealByIdUseCase: GetMealByIdUseCase) {
    println("Enter a meal ID to retrieve details:")
    val idInput = readLine()?.toIntOrNull()

    if (idInput != null) {
        val result = getMealByIdUseCase(idInput)
            .onSuccess {
                it.let {
                    viewMoreDetailsAboutSpecificMeal(it)
                }
            }
            .onFailure {
                println(it.message)
            }
    } else {
        println("Invalid input. Please enter a valid meal ID.")
    }
}

fun getHighCalorieMealSuggestionConsole(highCalorieMealSuggestionUseCase: HighCalorieMealSuggestionUseCase) {
    val suggestion = highCalorieMealSuggestionUseCase.getNextSuggestion()
    if (suggestion == null) {
        println("No more high-calorie meals available to suggest.")
        return
    }
    println("High-Calorie Meal Suggestion: ${suggestion.name}")
    println("Enter 'yes' to view details, 'no' for another suggestion")

    when(readLine()?.trim()?.lowercase()) {
        "yes", "y" -> {
            println("Meal Details:")
            println("Name       : ${suggestion.name}")
            println("Description: ${suggestion.description}")
            println("Calories   : ${suggestion.nutrition.calories}")
        }
        "no", "n" -> {
            getHighCalorieMealSuggestionConsole(highCalorieMealSuggestionUseCase)
        }
        else -> {
            println("Invalid input.")
        }
    }
}