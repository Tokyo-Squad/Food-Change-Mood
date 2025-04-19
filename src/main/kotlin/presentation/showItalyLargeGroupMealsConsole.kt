package org.example.presentation

import org.example.logic.GetLargeGroupItalyMealUseCase

fun showItalyLargeGroupMealsConsole(largeGroupItalyMealUseCase: GetLargeGroupItalyMealUseCase) {
    println("\n=== ITALIAN GROUP MEALS ===")
    val pageSize = 20
    var pageNumber = 0
    val allMeals = largeGroupItalyMealUseCase()

    fun displayMeals(page: Int) {
        val start = page * pageSize
        val end = start + pageSize
        val italianLargeGroupMeals = allMeals.slice(start until minOf(end, allMeals.size))

        println("\n=== Page ${page + 1} ===")
        italianLargeGroupMeals.forEachIndexed { index, meal ->
            println(
                """
                ${start + index + 1}. ${meal.name.capitalize()}
                   • Serves: Large group
                   • Prep time: ${meal.preparationTime} mins
                   • Main ingredients: ${meal.ingredients.take(5).joinToString(", ")}
                   • Tags: ${
                    meal.tags.filter { it.contains("italian") || it.contains("group") || it.contains("party") }
                        .joinToString(", ")
                }
            """.trimIndent()
            )
        }

        println("\nTotal meals shown: ${start + italianLargeGroupMeals.size} of ${allMeals.size}")
        println("Press 'n' for next page, 'p' for previous page, or any other key to exit")
    }

    displayMeals(pageNumber)

    while (true) {
        when (readLine()?.trim()?.lowercase()) {
            "n" -> {
                if ((pageNumber + 1) * pageSize < allMeals.size) {
                    pageNumber++
                    displayMeals(pageNumber)
                } else {
                    println("You've reached the end of the list!")
                }
            }
            "p" -> {
                if (pageNumber > 0) {
                    pageNumber--
                    displayMeals(pageNumber)
                } else {
                    println("You're already on the first page!")
                }
            }
            else -> {
                println("Exiting...")
                return
            }
        }
    }
}