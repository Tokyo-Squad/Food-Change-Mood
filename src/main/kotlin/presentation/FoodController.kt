package org.example.presentation

import org.example.presentation.controller.*

data class FoodController(
    val easyFoodSuggestionController: EasyFoodSuggestionController,
    val exploreFoodCultureController: ExploreFoodCultureController,
    val gymHelperController: GymHelperController,
    val healthyFastFoodMealsController: HealthyFastFoodMealsController,
    val highCalorieMealSuggestionController: HighCalorieMealSuggestionController,
    val ingredientGameController: IngredientGameController,
    val iraqiMealsController: IraqiMealsController,
    val italyLargeGroupMealsController: ItalyLargeGroupMealsController,
    val ketoDietController: KetoDietController,
    val mealsByAddDateController: MealsByAddDateController,
    val mealSearchController: MealSearchController,
    val potatoMealsController: PotatoMealsController,
    val seafoodMealsByProteinController: SeafoodMealsByProteinController,
    val sweetMealsController: SweetMealsController
)
