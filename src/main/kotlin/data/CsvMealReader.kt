package org.example.data

import com.opencsv.CSVReader
import org.example.utils.MealAppException
import java.io.File
import java.io.FileReader
import java.io.IOException

class CsvMealReader(private val fileName: String) {

    fun readMealsFromFile(): Sequence<Array<String>> {
        val file = File(fileName)
        if (!file.exists()) {
            println("Error: File '$fileName' does not exist.")
            return emptySequence()
        }

        return try {

            val reader = CSVReader(FileReader(fileName))
            //  skip the header
            reader.readNext()

            generateSequence { reader.readNext() }

        } catch (e: IOException) {
            throw MealAppException.FileReadException("Error reading file '$fileName': ${e.message}")
        }
    }
}