package org.example.utils

sealed class MealAppException(message: String) : Exception(message) {

    class NoMealsFoundException(message: String = "No meals found.") : MealAppException(message)

    class InvalidDateFormatException(message: String = "Invalid date format.") : MealAppException(message)

    class MalformedCsvRowException(message: String) : MealAppException(message)

    class FileReadException(message: String) : MealAppException(message)

    class FileNotFoundException(message: String) : MealAppException(message)

    class NoSuchElementException(message: String = "No such element found.") : MealAppException(message)

}