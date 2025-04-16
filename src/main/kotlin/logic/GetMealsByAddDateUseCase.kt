package org.example.logic

import kotlinx.datetime.LocalDate
import org.example.model.Meal
import org.example.utils.InvalidDateFormatException
import org.example.utils.NoMealsFoundException

class GetMealsByAddDateUseCase(
    private val repo: CsvRepository
) {
    operator fun invoke(dateInput: String): Result<List<Pair<Int, String>>> {
        return try {
            val date = LocalDate.parse(dateInput) // Ensure the input is in "yyyy-MM-dd" format
            val foundMeals = getMealsByAddDate(date)
            if (foundMeals.isEmpty()) {
                Result.failure(NoMealsFoundException("No meals found for the date: $dateInput"))
            } else {
                val resultData = foundMeals.map { Pair(it.id, it.name) }
                Result.success(resultData) // Return success with the list of meals
            }
        } catch (e: Exception) {
            Result.failure(InvalidDateFormatException("Invalid date format. Please use yyyy-MM-dd."))
        }
    }

    private fun getMealsByAddDate(date:LocalDate): List<Meal> {
        return repo.getMeals().filter { it.submitted == date }
    }
}
