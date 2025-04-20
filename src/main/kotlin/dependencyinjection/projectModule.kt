package dependencyinjection

import org.example.data.CsvMealParser
import org.example.data.CsvMealReader
import org.example.data.MealRepositoryImpl
import org.example.logic.repository.MealRepository
import org.example.utils.MealSearchIndex
import org.example.presentation.io.ConsoleIO
import org.example.presentation.io.RealConsoleIO
import org.koin.dsl.module

val projectModule = module {
    single { CsvMealReader("food.csv") }
    single { CsvMealParser() }
    single<MealRepository> { MealRepositoryImpl(get(), get()) }
    single { MealSearchIndex(get()) }
    single<ConsoleIO>{RealConsoleIO()}
}