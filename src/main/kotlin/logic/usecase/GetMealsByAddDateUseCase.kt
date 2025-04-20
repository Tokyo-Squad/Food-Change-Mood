package org.example.logic.usecase

import kotlinx.datetime.LocalDate
import org.example.logic.CsvRepository
import org.example.model.Meal
import org.example.utils.MealAppException

class GetMealsByAddDateUseCase(
    private val repo: CsvRepository
) {
    operator fun invoke(dateInput: String): Result<List<Meal>> {
        return try {
            val date = LocalDate.parse(dateInput)
            val foundMeals = getMealsByAddDate(date)
            if (foundMeals.isEmpty()) {
                Result.failure(MealAppException.NoMealsFoundException("No meals found for the date: $dateInput"))
            } else {
                Result.success(foundMeals)
            }
        } catch (e: Exception) {
            Result.failure(MealAppException.InvalidDateFormatException("Invalid date format. Please use yyyy-MM-dd."))
        }
    }

    private fun getMealsByAddDate(date: LocalDate): List<Meal> {
        return repo.getMeals().filter { it.submitted == date }
    }
}