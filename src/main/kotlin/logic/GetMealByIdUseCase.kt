package org.example.logic

import org.example.model.Meal

class GetMealByIdUseCase(private val repo: CsvRepository) {

    operator fun invoke(id: Int): Result<Meal> {
        val meal = repo.getMealById(id)
        return if (meal != null) {
            Result.success(meal) // Return success with the meal
        } else {
            Result.failure(MealNotFoundException("Meal with ID $id not found."))
        }
    }

    // Custom exception for meal not found
    class MealNotFoundException(message: String) : Exception(message)
}