package org.example.presentation

import org.example.logic.GetMealByIdUseCase
import org.example.logic.GetMealsByAddDateUseCase
import org.example.logic.GymHelperUseCase

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

fun getGymHelper(gymHelper: GymHelperUseCase){
    print("please enter your target calories: ")
    val targetCalories = readln().toFloatOrNull()
    print("please enter your target calories: ")
    val targetProtein = readln().toFloatOrNull()

    if(targetCalories != null && targetProtein != null ){
        gymHelper.invoke(targetCalories = targetCalories, targetProtein = targetProtein).forEachIndexed { index, meal ->
            println("${index + 1} - $meal")
        }
    }
    else
        throw NullPointerException("please enter correct value")



}