package org.example.utils

// Custom exception for no meals found
class NoMealsFoundException(message: String) : Exception(message)

// Custom exception for meal not found
class MealNotFoundException(message: String) : Exception(message)

// Custom exception for invalid date format
class InvalidDateFormatException(message: String) : Exception(message)