package org.example.dependencyinjection

import org.example.logic.EasyFoodSuggestionUseCase
import org.example.logic.GetMealByIdUseCase
import org.example.logic.GetMealsByAddDateUseCase
import org.example.logic.SweetMealWithoutEggUseCase
import org.example.logic.GetRandomMealUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetMealsByAddDateUseCase(get()) }

    single { GetMealByIdUseCase(get()) }

    single { SweetMealWithoutEggUseCase(csvRepository = get()) }

    single { EasyFoodSuggestionUseCase(get()) }

    single { GetRandomMealUseCase(get()) }

}