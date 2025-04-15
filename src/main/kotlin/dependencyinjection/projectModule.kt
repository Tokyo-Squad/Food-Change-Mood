package org.example.debendency

import org.example.data.CsvMealParser
import org.example.data.CsvMealReader
import org.example.data.CsvRepositoryImpl
import org.example.logic.CsvRepository
import org.example.logic.KetoDietMealHelperUseCase
import org.koin.dsl.module

val projectModule = module {
    single { CsvMealReader("food.csv") }

    single { CsvMealParser() }

    single<CsvRepository> { CsvRepositoryImpl(get(), get()) }

    single { KetoDietMealHelperUseCase(csvRepository = get()) }
}