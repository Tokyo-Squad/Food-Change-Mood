package org.example.presentation

import org.example.logic.*
import org.example.utils.InvalidDateFormatException
import org.example.utils.NoMealsFoundException
import org.koin.java.KoinJavaComponent.getKoin

class ConsoleUi {
    val repo: CsvRepository = getKoin().get()
    val healthyFastFoodUseCase = getKoin().get<GetHealthyFastFoodMealsUseCase>()
    val getMealsByNameUseCase= getKoin().get<GetMealsByNameUseCase>()
    val iraqiMealsUseCase = getKoin().get<GetIraqiMealsUseCase>()
    val easyFoodSuggestionUseCase = getKoin().get<EasyFoodSuggestionUseCase>()
    val sweetWithoutEggUseCase = getKoin().get<SweetMealWithoutEggUseCase>()
    val ketoDietHelperUseCase = getKoin().get<KetoDietMealHelperUseCase>()
    val mealsByAddDateUseCase = getKoin().get<GetMealsByAddDateUseCase>()
    val gymHelperUseCase = getKoin().get<GymHelperUseCase>()
    val exploreFoodCultureUseCase = getKoin().get<ExploreCountriesFoodCultureUseCase>()
    val ingredientGameUseCase = getKoin().get<PlayIngredientGameUseCase>()
    val potatoMealsUseCase = GetRandomPotatoMealsUseCase(csvRepository = repo)
    val highCalorieMealUseCase = getKoin().get<HighCalorieMealSuggestionUseCase>()
    val seafoodMealsUseCase = GetSeafoodMealsSortedByProteinUseCase(csvRepository = repo)
    val largeGroupItalyMealUseCase = getKoin().get<GetLargeGroupItalyMealUseCase>()

    fun start() {
        while (true) {
            displayMenu()
            handleUserChoice(getUserChoice())
        }
    }

    private fun displayMenu() {
        println("\nWelcome to Food Change Mood!")
        println("Choose an option:")
        println("1. Get Healthy Fast Food Meals (15 mins or less, low fat)")
        println("2. Search Meals by Name")
        println("3. Discover Iraqi Meals")
        println("4. Easy Food Suggestions (30 mins, ≤5 ingredients, ≤6 steps)")
        println("5. Preparation Time Guess Game")
        println("6. Sweets with No Eggs")
        println("7. Keto Diet Meal Helper")
        println("8. Search Foods by Add Date")
        println("9. Gym Helper (Calories & Protein Match)")
        println("10. Explore Other Countries' Food Culture")
        println("11. Ingredient Guessing Game")
        println("12. I Love Potato (Potato-based meals)")
        println("13. So Thin Problem (High Calorie Meals)")
        println("14. Seafood Meals by Protein Content")
        println("15. Italian Large Group Meals")
        println("0. Exit")
        println()
    }

    private fun getUserChoice(): Int {
        return readlnOrNull()?.toIntOrNull() ?: -1
    }

    private fun handleUserChoice(choice: Int) {
        try {
            when (choice) {
                1 -> getHealthyFastFoodMealsConsole(healthyFastFoodUseCase)
                2 -> getMealsByName(getMealsByNameUseCase)
                3 -> getIraqiMeals(iraqiMealsUseCase)
                4 -> getEasyFoodSuggestionConsole(easyFoodSuggestionUseCase)
                5 -> guessGame(3, repo)
                6 -> getSweetsWithNoEggs(sweetWithoutEggUseCase)
                7 -> getKetoDietHelper(ketoDietHelperUseCase)
                8 -> getMealsByAddDateConsole(mealsByAddDateUseCase)
                9 -> getGymHelper(gymHelperUseCase)
                10 -> exploreFoodCulture(exploreFoodCultureUseCase)
                11 -> playIngredientGame(ingredientGameUseCase)
                12 -> getPotatoMeals(potatoMealsUseCase) // will need a refactor to use UseCase only
                13 -> getHighCalorieMealSuggestionConsole(highCalorieMealUseCase)
                14 -> getSeafoodMealsByProtein(seafoodMealsUseCase, repo)
                15 -> showItalyLargeGroupMeals(largeGroupItalyMealUseCase)
                0 -> {
                    println("Thank you for using Food Change Mood! Goodbye!")
                    System.exit(0)
                }

                else -> println("Invalid option. Please try again.")
            }
        } catch (e: Exception) {
            handleError(e)
        }

        waitForUser()
    }

    private fun handleError(error: Exception) {
        println("\nAn error occurred: ${error.message}")
        when (error) {
            is InvalidDateFormatException -> println("Please use the format yyyy-MM-dd")
            is NoMealsFoundException -> println("No meals found with the specified criteria")
            else -> println("Please try again or choose a different option")
        }
    }

    private fun waitForUser() {
        println("\nPress Enter to continue...")
        readlnOrNull()
    }
}