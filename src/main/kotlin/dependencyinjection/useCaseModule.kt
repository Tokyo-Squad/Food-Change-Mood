package org.example.dependencyinjection

import org.example.logic.EasyFoodSuggestionUseCase
import org.example.logic.GetMealsByAddDateUseCase
import org.example.logic.SweetMealWithoutEggUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetMealsByAddDateUseCase(get()) }

    single { SweetMealWithoutEggUseCase(csvRepository = get()) }

    single { EasyFoodSuggestionUseCase(get()) }
}