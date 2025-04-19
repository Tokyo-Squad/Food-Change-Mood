package org.example.presentation

import org.example.logic.GetMealsByAddDateUseCase

fun getMealsByAddDateConsole(getMealsByAddDateUseCase: GetMealsByAddDateUseCase) {
    print("Date (yyyy-MM-dd): ")

    getMealsByAddDateUseCase(readLine() ?: "").fold(
        onSuccess = { meals ->
            if (meals.isEmpty()) return println("No meals found")

            meals.forEach { println("${it.id}: ${it.name}") }
            print("\nID for details: ")

            readLine()?.toIntOrNull()?.let { id ->
                meals.find { it.id == id }?.let(::viewMoreDetailsAboutSpecificMealConsole)
                    ?: println("Invalid ID")
            }
        },
        onFailure = { println(it.message) }
    )
}
