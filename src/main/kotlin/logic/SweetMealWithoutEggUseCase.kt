package org.example.logic

import org.example.model.Meal
import org.example.utils.MealAppException

class SweetMealWithoutEggUseCase(
    private val sweetMealsNotContainEgg: GetSweetMealsNotContainEggUseCase
) : ReactionProvider {
    private val disLikedFood = mutableSetOf<Meal>()
    private val likedFood = mutableSetOf<Meal>()

     fun getRandomsEggFreeSweet(): Meal {
        val eggFreeSweets = sweetMealsNotContainEgg()
        if (eggFreeSweets.isEmpty()) {
            throw MealAppException.NoSuchElementException("No egg-free sweets available")
        }
        return generateSequence { eggFreeSweets.random() }
            .filterNot { disLikedFood.contains(it) }
            .first()

    }

    override fun like(meal: Meal) {
        likedFood.add(meal)
    }

    override fun dislike(meal: Meal) {
        disLikedFood.add(meal)
    }
}
