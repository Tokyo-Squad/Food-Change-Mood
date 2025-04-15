package org.example.dependencyinjection

import org.example.logic.CsvRepository
import org.example.logic.GetHealthyFastFoodMealsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetHealthyFastFoodMealsUseCase(get<CsvRepository>()) }
}