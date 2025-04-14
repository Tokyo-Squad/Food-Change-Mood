package org.example.logic

import org.example.model.Meal

interface CsvRepository {
    fun getMeals(): List<Meal>

}