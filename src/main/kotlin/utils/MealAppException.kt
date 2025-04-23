package org.example.utils

sealed class MealAppException(message: String) : Exception(message) {

    class NoMealsFoundException(message: String = "No meals found.") : MealAppException(message)

    class InvalidDateFormatException(message: String = "Invalid date format.") : MealAppException(message)

    class MalformedCsvRowException(message: String) : MealAppException(message)

    class FileReadException(message: String) : MealAppException(message)

    class FileNotFoundException(message: String) : MealAppException(message)

    class NoSuchElementException(message: String = "No such element found.") : MealAppException(message)

    class InvalidArgumentException(message: String = "Illegal argument provided.") : MealAppException(message)

    class InvalidNumberFormatException(message: String = "Invalid number format.") : MealAppException(message)

    class NoMealsAvailableException(message: String) : Exception(message)

    class InsufficientWrongAnswersException(message: String) : MealAppException(message)


}