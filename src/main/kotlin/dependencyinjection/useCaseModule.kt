package org.example.dependencyinjection

import org.example.logic.*
import org.koin.dsl.module

val useCaseModule = module {
    single { GetHealthyFastFoodMealsUseCase(get<CsvRepository>()) }
    single { GetMealsByAddDateUseCase(get()) }
    single { SweetMealWithoutEggUseCase(csvRepository = get()) }
    single { EasyFoodSuggestionUseCase(get()) }
    single { PlayIngredientGameUseCase(get()) }
    single { HighCalorieMealSuggestionUseCase(get()) }
}