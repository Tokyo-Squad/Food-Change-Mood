package org.example.dependencyinjection

import org.example.presentation.FoodApplicationUI
import org.example.presentation.FoodController
import org.example.presentation.controller.*
import org.koin.dsl.module

val uiModule = module {
    single { EasyFoodSuggestionController(get(), get()) }
    single { ExploreFoodCultureController(get(), get()) }
    single { GymHelperController(get(), get()) }
    single { HealthyFastFoodMealsController(get(), get()) }
    single { HighCalorieMealSuggestionController(get(), get()) }
    single { IngredientGameController(get(), get()) }
    single { IraqiMealsController(get(), get()) }
    single { ItalyLargeGroupMealsController(get(), get()) }
    single { KetoDietController(get(), get()) }
    single { MealsByAddDateController(get(), get()) }
    single { MealSearchController(get(), get()) }
    single { PotatoMealsController(get(), get()) }
    single { SeafoodMealsByProteinController(get(), get()) }
    single { SweetMealsController(get(), get()) }
    single {
        FoodController(
            get(), get(), get(),
            get(), get(), get(),
            get(), get(), get(),
            get(), get(), get(),
            get(), get()
        )
    }
    single { FoodApplicationUI(get()) }
}