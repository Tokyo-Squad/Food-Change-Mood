package org.example.logic

import org.example.model.Meal
import  kotlinx.datetime.LocalDate

interface CsvRepository {
    fun getMeals(): List<Meal>
    fun getMealsByAddDate(date:LocalDate):List<Meal>
    fun getMealById(id:Int): Meal?
}