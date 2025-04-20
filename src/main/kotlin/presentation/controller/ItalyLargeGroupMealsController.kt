package org.example.presentation.controller

import org.example.logic.usecase.GetLargeGroupItalyMealUseCase
import org.example.presentation.io.ConsoleIO
import kotlin.math.min

class ItalyLargeGroupMealsController(
    private val io: ConsoleIO,
    private val largeGroupItalyMealUseCase: GetLargeGroupItalyMealUseCase
) {
    fun display() {
// Fetch the complete list of meals from the use case.
        val allMeals = largeGroupItalyMealUseCase()
        val pageSize = 20
        var pageNumber = 0


        // Nested function to display meals for a given page.
        fun display(page: Int) {
            val start = page * pageSize
            val end = start + pageSize
            val italianLargeGroupMeals = allMeals.slice(start until min(end, allMeals.size))

            io.printOutput("\n=== Page ${page + 1} ===")
            italianLargeGroupMeals.forEachIndexed { index, meal ->
                io.printOutput(
                    """
                ${start + index + 1}. ${meal.name.capitalize()}
                   • Serves: Large group
                   • Prep time: ${meal.preparationTime} mins
                   • Main ingredients: ${meal.ingredients.take(5).joinToString(", ")}
                   • Tags: ${meal.tags.filter { it.contains("italian", ignoreCase = true) || it.contains("group", ignoreCase = true) || it.contains("party", ignoreCase = true) }
                        .joinToString(", ")}
                """.trimIndent()
                )
            }

            io.printOutput("\nTotal meals shown: ${min(end, allMeals.size)} of ${allMeals.size}")
            io.printOutput("Press 'n' for next page, 'p' for previous page, or any other key to exit")
        }

        display(pageNumber)

        // Loop until the user decides to exit.
        while (true) {
            when (io.readInput("> ")?.trim()?.lowercase()) {
                "n" -> {
                    if ((pageNumber + 1) * pageSize < allMeals.size) {
                        pageNumber++
                        display(pageNumber)
                    } else {
                        io.printOutput("You've reached the end of the list!")
                    }
                }
                "p" -> {
                    if (pageNumber > 0) {
                        pageNumber--
                        display(pageNumber)
                    } else {
                        io.printOutput("You're already on the first page!")
                    }
                }
                else -> {
                    io.printOutput("Exiting...")
                    return
                }
            }
        }
    }
}
