package org.example.dependencyinjection

import org.example.logic.*
import org.koin.dsl.module
import org.example.logic.EasyFoodSuggestionUseCase
import org.example.logic.GetMealsByAddDateUseCase
import org.example.logic.SweetMealWithoutEggUseCase
import org.example.logic.HighCalorieMealSuggestionUseCase


val useCaseModule = module {
    single { GetHealthyFastFoodMealsUseCase(get<CsvRepository>()) }
    single { GetMealsByAddDateUseCase(get()) }
    single { SweetMealWithoutEggUseCase(csvRepository = get()) }
    single { EasyFoodSuggestionUseCase(get()) }
    single { GetLargeGroupItalyMealUseCase(csvRepository = get()) }
    single { GetIraqiMealsUseCase(csvRepository = get()) }
    single { GymHelperUseCase(csvRepository = get()) }
    single { ExploreCountriesFoodCultureUseCase(get()) }
    single { PlayIngredientGameUseCase(get()) }
    single { HighCalorieMealSuggestionUseCase(get()) }
    single { MealSearchUseCase(get()) }
}