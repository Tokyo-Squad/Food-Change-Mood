package org.example.data

import org.example.logic.repository.MealRepository
import org.example.model.Meal
import org.example.utils.MealAppException

class MealRepositoryImpl(
    private val csvMealReader: CsvMealReader,
    private val parser: CsvMealParser
) : MealRepository {

    override fun getMeals(): List<Meal> {
        return try {
            csvMealReader.readMealsFromFile()
                .map { line ->
                    parser.parseLine(line) ?: throw MealAppException.MalformedCsvRowException("Line is null after parsing.")
                }
                .toList()
        } catch (e: MealAppException.FileNotFoundException) {
            println(" File not found: ${e.message}")
            emptyList()
        } catch (e: MealAppException.FileReadException) {
            println(" Failed to read file: ${e.message}")
            emptyList()
        } catch (e: MealAppException.MalformedCsvRowException) {
            println(" Malformed CSV row: ${e.message}")
            emptyList()
        } catch (e: MealAppException.InvalidDateFormatException) {
            println(" Invalid date in CSV: ${e.message}")
            emptyList()
        }
    }
}