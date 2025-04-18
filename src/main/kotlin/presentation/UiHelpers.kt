package org.example.presentation

import org.example.logic.*
import viewMoreDetailsAboutSpecificMeal


fun playIngredientGame(gameUseCase: PlayIngredientGameUseCase) {
    val result = gameUseCase.playGame { meal, options ->
        println("Guess an ingredient in '${meal.name}':")

        options.forEachIndexed { i, option -> println("${i + 1}. $option") }

        val choice = readlnOrNull()?.toIntOrNull()

        // Return the selected ingredient, or null if the input is invalid
        options.getOrNull((choice ?: 1) - 1) // Default to the first option if invalid
    }

    println("Game Over! Final Score: ${result.first}, Correct Answers: ${result.second}, Reason: ${result.third}")
}

fun getMealsByName(getMealsByNameUseCase: GetMealsByNameUseCase) {
    print("Search meals: ")
    val query = readlnOrNull()?.trim() ?: ""

    val meals = getMealsByNameUseCase(query)

    if (meals.isEmpty()) {
        println("No meals found for '$query'")
    } else {
        println("\nFound ${meals.size} meals:")
        meals.forEachIndexed { index, meal ->
            println(
                """
            ${index + 1}. ${meal.name}
               Time: ${meal.preparationTime} min
               Ingredients: ${meal.numberOfIngredients}
               Steps: ${meal.numberOfSteps}
               Tags: ${meal.tags.take(3).joinToString()}
            """.trimIndent()
            )
        }
    }
}

fun getMealsByAddDateConsole(getMealsByAddDateUseCase: GetMealsByAddDateUseCase) {
    print("Date (yyyy-MM-dd): ")

    getMealsByAddDateUseCase(readLine() ?: "").fold(
        onSuccess = { meals ->
            if (meals.isEmpty()) return println("No meals found")

            meals.forEach { println("${it.id}: ${it.name}") }
            print("\nID for details: ")

            readLine()?.toIntOrNull()?.let { id ->
                meals.find { it.id == id }?.let(::viewMoreDetailsAboutSpecificMeal)
                    ?: println("Invalid ID")
            }
        },
        onFailure = { println(it.message) }
    )
}


fun guessGame(attempts: Int = 3, repo: CsvRepository) {
    val meal = repo.getMeals().random()
    var tries = attempts

    println("\n=== Time Guess: ${meal.name} ===")

    while (tries-- > 0) {
        print("Guess mins ($tries tries left): ")
        when (val guess = readLine()?.toIntOrNull()) {
            null -> println("Numbers only!")
            meal.preparationTime -> return println("Correct! ${meal.preparationTime} mins.")
            else -> println("Too ${if (guess < meal.preparationTime) "low" else "high"}!")
        }
    }
    println("Game Over! Answer: ${meal.preparationTime} mins.")
}

fun getHighCalorieMealSuggestionConsole(useCase: HighCalorieMealSuggestionUseCase) {
    println("\n=== High-Calorie Meals (>700 cal) ===")

    useCase.getNextSuggestion()?.let { meal ->
        println("${meal.name} (${meal.nutrition.calories} cal)")
        print("View details (Y/N)? ")

        when (readLine()?.firstOrNull()?.uppercase()) {
            "Y" -> viewMoreDetailsAboutSpecificMeal(meal)
            "N" -> getHighCalorieMealSuggestionConsole(useCase)
            else -> println("Invalid choice")
        }
    } ?: println("No more high-calorie meals available.")
}

fun showItalyLargeGroupMeals(largeGroupItalyMealUseCase: GetLargeGroupItalyMealUseCase) {
    println("\n=== ITALIAN GROUP MEALS ===")
    val italianLargeGroupMeals = largeGroupItalyMealUseCase.invoke()

    italianLargeGroupMeals.forEachIndexed { index, meal ->
        println(
            """
            ${index + 1}. ${meal.name.capitalize()}
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

    println("\nTotal meals found: ${italianLargeGroupMeals.size}")
}

fun getHealthyFastFoodMealsConsole(useCase: GetHealthyFastFoodMealsUseCase) {
    println("\n=== Healthy Fast Food Meals (15 mins or less) ===")

    val result = useCase.getHealthyFastFoodMeals()

    if (result.isEmpty()) {
        println("No healthy fast food meals found.")
    } else {
        println("Healthy Fast Food Meals:")
        result.forEach { meal ->
            println("ID: ${meal.id}, Name: ${meal.name}, Time: ${meal.preparationTime} mins")
        }
    }
}


fun getKetoDietHelper(ketoDietMealHelperUseCase: KetoDietMealHelperUseCase) {
    while (true) {
        val randomMeal = ketoDietMealHelperUseCase.getRandomFriendlyMeal()

        println("Random Keto Diet:\n")
        viewMoreDetailsAboutSpecificMeal(randomMeal)
        println(
            """
            1- Like (continue)
            2- Dislike (show another keto diet)
            3- Exit
            """.trimIndent()
        )

        when (readLine()) {
            "1" -> {
                println("Meal liked! Continuing...")
                return
            }

            "2" -> {
                ketoDietMealHelperUseCase.dislike(randomMeal)
                println("Meal disliked. Fetching another suggestion...")
            }

            "3" -> {
                println("Exiting...")
                return
            }

            else -> println("Please enter a valid option (1, 2, or 3)")
        }
    }
}

fun getIraqiMeals(iraqiMealUseCase: GetIraqiMealsUseCase) {
    println("\n=== Iraqi Meals ===")

    val iraqiMeals = iraqiMealUseCase.invoke()

    if (iraqiMeals.isEmpty()) {
        println("No Iraqi meals found.")
        return
    }

    iraqiMeals.forEachIndexed { index, meal ->
        println("${index + 1}. ${meal.name} | ${meal.preparationTime}mins")
    }
}

fun getGymHelper(gymHelper: GymHelperUseCase) {
    println("\n=== GYM MEAL PLANNER ===")
    print("Enter target calories: ")
    val targetCalories = readln().toFloatOrNull()
    print("Enter target protein (grams): ")
    val targetProtein = readln().toFloatOrNull()

    try {
        if (targetCalories != null && targetProtein != null) {
            println("\nSuggested Meals matching your goals:")
            println("Target: ${targetCalories}kcal | ${targetProtein}g protein\n")

            gymHelper.invoke(targetCalories = targetCalories, targetProtein = targetProtein)
                .forEachIndexed { index, meal ->
                    println(
                        """
                        ${index + 1}. ${meal.name.capitalize()}
                           • Calories: ${meal.nutrition.calories}kcal
                           • Protein: ${meal.nutrition.protein}g
                           • Carbs: ${meal.nutrition.carbohydrates}g
                           • Fat: ${meal.nutrition.totalFat}g
                           • Time to prepare: ${meal.preparationTime}mins
                    """.trimIndent()
                    )
                }
        } else {
            throw IllegalArgumentException("Please enter valid numbers for calories and protein")
        }
    } catch (e: NumberFormatException) {
        println("\n⚠ Error: Please enter valid numbers only")
    } catch (e: IllegalArgumentException) {
        println("\n⚠ Error: ${e.message}")
    }
}

fun getEasyFoodSuggestionConsole(useCase: EasyFoodSuggestionUseCase) {
    println("\n=== Easy Meals (≤30mins, ≤5 ingredients, ≤6 steps) ===")

    useCase.getEasyMeals().let { meals ->
        if (meals.isEmpty()) println("No easy meals found.")
        else meals.forEachIndexed { i, meal ->
            println("${i + 1}. ${meal.name} | ${meal.preparationTime}mins | ${meal.numberOfIngredients}ing | ${meal.numberOfSteps}steps")
        }
    }
}


fun getSweetsWithNoEggs(useCase: SweetMealWithoutEggUseCase) {
    println("\n=== Egg-Free Sweets Finder ===")

    while (true) {
        try {
            val sweet = useCase.getRandomsEggFreeSweet()
            println("${sweet.name} | ${sweet.description ?: "No description"}")

            print("Like (L) to see details, Dislike (D) for another, Exit (E): ")
            when (readLine()?.uppercase()) {
                "L" -> {
                    viewMoreDetailsAboutSpecificMeal(sweet)
                    return
                }

                "D" -> {
                    useCase.disLikeMeal(sweet)
                    println("Finding another sweet...")
                }

                "E" -> return
                else -> println("Invalid input! Try again.")
            }
        } catch (e: NoSuchElementException) {
            println("No more egg-free sweets available.")
            return
        }
    }
}


fun exploreFoodCulture(useCase: ExploreCountriesFoodCultureUseCase) {
    print("\n=== Food Culture Explorer ===\nEnter country name: ")

    val meals = useCase.invoke(readLine() ?: return)

    if (meals.isEmpty()) {
        println("No meals found for this country.")
        return
    }

    println("\nFound ${meals.size} meals:")
    meals.forEachIndexed { i, meal ->
        println("${i + 1}. ${meal.name} | ${meal.preparationTime}mins")
    }
}

fun getPotatoMeals(useCase: GetRandomPotatoMealsUseCase, repo: CsvRepository) {
    println("\n=== Potato-Based Meals ===")

    val potatoMeals = useCase.invoke(repo.getMeals())

    if (potatoMeals.isEmpty()) {
        println("No potato meals found.")
        return
    }

    potatoMeals.forEachIndexed { i, meal ->
        println("${i + 1}. ${meal.name} | ${meal.preparationTime}mins")
    }
}

fun getSeafoodMealsByProtein(useCase: GetSeafoodMealsSortedByProteinUseCase, repo: CsvRepository) {
    println("\n=== Seafood Meals by Protein Content ===")

    val seafoodMeals = useCase.invoke(repo.getMeals())

    if (seafoodMeals.isEmpty()) {
        println("No seafood meals found.")
        return
    }

    seafoodMeals.forEach { (rank, meal) ->
        println("$rank. ${meal.name} | ${meal.nutrition.protein}g protein")
    }
}