package org.example.presentation

import org.example.logic.GetMealByIdUseCase
import org.example.logic.GetMealsByAddDateUseCase

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
                    println("Meal Details:")
                    println("ID: ${it.id}")
                    println("Name: ${it.name}")
                    println("Preparation Time: ${it.preparationTime} minutes")
                    println("Submitted: ${it.submitted}")
                    println("Description: ${it.description ?: "No description available"}")
                    println("Ingredients: ${it.ingredients.joinToString(", ")}")
                }
            }
            .onFailure {
                println(it.message)
            }
    } else {
        println("Invalid input. Please enter a valid meal ID.")
    }
}