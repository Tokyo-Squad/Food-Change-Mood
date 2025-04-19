package org.example.presentation

import org.example.logic.*
import viewMoreDetailsAboutSpecificMeal

fun playIngredientGame(gameUseCase: PlayIngredientGameUseCase) {
    val result = gameUseCase.invoke { meal, options ->
        println("Guess an ingredient in '${meal.name}':")

        options.forEachIndexed { index, option ->
            println("${index + 1}. $option")
        }

        print("Enter your choice (1-${options.size}): ")
        val choice = readlnOrNull()?.toIntOrNull()

        when {
            choice == null -> {
                println("Invalid input. Please enter a number.")
                null
            }
            choice !in 1..options.size -> {
                println("Please enter a number between 1 and ${options.size}.")
                null
            }
            else -> options[choice - 1]
        }
    }

    println("Game Over! Final Score: ${result.finalScore}, Correct Answers: ${result.correctAnswers}, Reason: ${result.message}")
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


fun guessGame(attempts: Int = 3, getRandomMealUseCase: GetRandomMealUseCase) {
    val meal = getRandomMealUseCase()
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

fun getHealthyFastFoodMealsConsole(useCase: GetHealthyFastFoodMealsUseCase) {
    println("\n=== Healthy Fast Food Meals (15 mins or less) ===")

    val result = useCase.invoke()

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
        val randomMeal = ketoDietMealHelperUseCase()

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
                ketoDietMealHelperUseCase.like(meal = randomMeal)
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
                    useCase.like(meal = sweet)
                    return
                }

                "D" -> {
                    useCase.dislike(sweet)
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
    val countryInput = readlnOrNull()?.trim() ?: return
    val meals = useCase(countryInput)

    if (meals.isEmpty()) {
        println("No meals found for this country.")
        return
    }

    println("\nFound ${meals.size} ${countryInput.lowercase()} meals:")
    meals.forEachIndexed { i, meal ->
        println("${i + 1}. ${meal.name} | ${meal.preparationTime} mins")
        println("   Ingredients: ${meal.ingredients.take(3).joinToString()}...")
    }
}

fun getPotatoMeals(useCase: GetRandomPotatoMealsUseCase) {
    println("\n=== Potato-Based Meals ===")

    val potatoMeals = useCase()

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

    val seafoodMeals = useCase()

    if (seafoodMeals.isEmpty()) {
        println("No seafood meals found.")
        return
    }

    seafoodMeals.forEach { (rank, meal) ->
        println("$rank. ${meal.name} | ${meal.nutrition.protein}g protein")
    }
}