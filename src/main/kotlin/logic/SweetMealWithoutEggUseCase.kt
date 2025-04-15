package org.example.logic

import org.example.model.Meal
import org.example.utils.containAnyOf

class SweetMealWithoutEggUseCase(
    private val csvRepository: CsvRepository,
)  {
    private val disLikedFood = mutableSetOf<Meal>()

     fun getRandomsEggFreeSweet(): Meal {
        val eggFreeSweets = getSweetMealsContainEgg()
        if (eggFreeSweets.isEmpty()) {
            throw NoSuchElementException("No egg-free sweets available")
        }
        return generateSequence { eggFreeSweets.random() }
            .filterNot { disLikedFood.contains(it) }
            .first()

    }

     private fun getSweetMealsContainEgg(): List<Meal> {
        return getSweetMeals().filter { meal ->
            meal.containAnyOf("egg")
        }
    }

     private fun getSweetMeals(): List<Meal> {
        val sweetTypes = listOf("sweet", "dessert", "cake", "candy", "cookie", "pie", "chocolate", "sugar")
        return getAllMeals().filter { meal ->
            meal.containAnyOf(sweetTypes)
        }

    }

     private fun getAllMeals(): List<Meal> {
        return csvRepository.getMeals()
    }


     fun disLikeMeal(meal: Meal) {
        disLikedFood.add(meal)
    }
}
