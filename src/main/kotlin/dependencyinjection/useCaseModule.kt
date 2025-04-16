package org.example.dependencyinjection

import org.example.logic.CsvRepository
import org.example.logic.GetHealthyFastFoodMealsUseCase
import org.koin.dsl.module
import org.example.logic.EasyFoodSuggestionUseCase
import org.example.logic.GetMealByIdUseCase
import org.example.logic.GetMealsByAddDateUseCase
import org.example.logic.SweetMealWithoutEggUseCase


val useCaseModule = module {
    single { GetHealthyFastFoodMealsUseCase(get<CsvRepository>()) }
    single { GetMealsByAddDateUseCase(get()) }

    single { GetMealByIdUseCase(get()) }

    single { SweetMealWithoutEggUseCase(csvRepository = get()) }

    single { EasyFoodSuggestionUseCase(get()) }
}