package org.example.data

import org.example.logic.CsvRepository
import org.example.model.Meal

class CsvRepositoryImpl(
    private val csvMealReader: CsvMealReader,
    private val parser: CsvMealParser
) : CsvRepository {

    override fun getMeals(): List<Meal> {
        return csvMealReader.readMealsFromFile()
            .mapNotNull(parser::parseLine)
            .toList()
    }
}