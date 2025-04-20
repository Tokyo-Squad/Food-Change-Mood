package org.example.logic.repository

import org.example.model.Meal

interface MealRepository {
    fun getMeals(): List<Meal>
}