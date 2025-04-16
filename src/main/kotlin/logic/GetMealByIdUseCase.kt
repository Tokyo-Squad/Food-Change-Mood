package org.example.logic

import org.example.model.Meal
import org.example.utils.MealNotFoundException

class GetMealByIdUseCase(private val repo: CsvRepository) {

    operator fun invoke(id: Int): Result<Meal> {
        val meal = getMealById(id)
        return if (meal != null) {
            Result.success(meal) // Return success with the meal
        } else {
            Result.failure(MealNotFoundException("Meal with ID $id not found."))
        }
    }


    private fun getMealById(id:Int): Meal? {
        return repo.getMeals().find { it.id == id }
    }
}