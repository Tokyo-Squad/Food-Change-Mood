package org.example.data

import com.opencsv.CSVReader
import java.io.File
import java.io.FileReader
import java.io.IOException

class CsvMealReader(private val fileName: String) {

    // This function will read meals from the CSV file
    fun readMeals(): Sequence<Array<String>> {
        // Check if the file exists before trying to read it
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
            println("Error reading file: ${e.message}")
            emptySequence()
        }
    }
}