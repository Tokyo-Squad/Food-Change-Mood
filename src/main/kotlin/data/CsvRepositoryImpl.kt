package org.example.data

import org.example.logic.CsvRepository
import org.example.model.Meal
import kotlinx.datetime.LocalDate

class CsvRepositoryImpl(
    private val csvMealReader: CsvMealReader,
    private val parser: CsvMealParser
) : CsvRepository {

    override fun getMeals(): List<Meal> {
        return csvMealReader.readMeals()
            .mapNotNull(parser::parseLine)
            .toList()
    }
}