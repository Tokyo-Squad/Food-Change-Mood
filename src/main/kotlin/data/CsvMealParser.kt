package org.example.data

import kotlinx.datetime.LocalDate
import org.example.model.Meal
import org.example.model.Nutrition

class CsvMealParser {

    fun parseLine(line: Array<String>): Meal? {
        if (line.size != 12) return null

        return try {
            val nutritionValues = parseNutritionList(line[MealIndex.NUTRITION])
            val nutrition = Nutrition(
                calories = nutritionValues.getOrElse(NutritionIndex.CALORIES) { 0.0 },
                totalFat = nutritionValues.getOrElse(NutritionIndex.TOTAL_FAT) { 0.0 },
                sugar = nutritionValues.getOrElse(NutritionIndex.SUGAR) { 0.0 },
                sodium = nutritionValues.getOrElse(NutritionIndex.SODIUM) { 0.0 },
                protein = nutritionValues.getOrElse(NutritionIndex.PROTEIN) { 0.0 },
                saturatedFat = nutritionValues.getOrElse(NutritionIndex.SATURATED_FAT) { 0.0 },
                carbohydrates = nutritionValues.getOrElse(NutritionIndex.CARBOHYDRATES) { 0.0 }
            )

            Meal(
                name = line[MealIndex.NAME],
                id = line[MealIndex.ID].toInt(),
                preparationTime = line[MealIndex.MINUTES].toInt(),
                contributorId = line[MealIndex.CONTRIBUTOR_ID].toInt(),
                submitted = parseSubmittedDate(line[MealIndex.SUBMITTED]),
                tags = parseStringList(line[MealIndex.TAGS]),
                nutrition = nutrition,
                numberOfSteps = line[MealIndex.N_STEPS].toInt(),
                steps = parseStringList(line[MealIndex.STEPS]),
                description = cleanDescription(line[MealIndex.DESCRIPTION]),
                ingredients = parseStringList(line[MealIndex.INGREDIENTS]),
                numberOfIngredients = line[MealIndex.N_INGREDIENTS].toInt()
            )
        } catch (e: Exception) {
            println("Error parsing line: ${line.joinToString(", ")} - ${e.message}")
            null
        }
    }

    private fun parseSubmittedDate(rawDate: String): LocalDate =
        LocalDate.parse(rawDate.trim())

    private fun parseStringList(raw: String): List<String> =
        raw.removeSurrounding("[", "]")
            .split(",")
            .map { it.trim().removeSurrounding("'").removeSurrounding("\"") }
            .filter { it.isNotBlank() }

    private fun parseNutritionList(raw: String): List<Double> =
        raw.removeSurrounding("[", "]")
            .split(",")
            .mapNotNull { it.trim().toDoubleOrNull() }

    private fun cleanDescription(description: String?): String =
        description?.trim()
            ?.takeIf { it.isNotBlank() }
            ?: "No description available"
}