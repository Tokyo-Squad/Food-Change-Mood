package dependencyinjection

import org.example.data.CsvMealParser
import org.example.data.CsvMealReader
import org.example.data.CsvRepositoryImpl
import org.example.logic.CsvRepository
import org.example.utils.MealSearchIndex
import org.example.presentation.io.ConsoleIO
import org.example.presentation.io.RealConsoleIO
import org.koin.dsl.module

val projectModule = module {
    single { CsvMealReader("food.csv") }
    single { CsvMealParser() }
    single<CsvRepository> { CsvRepositoryImpl(get(), get()) }
    single { MealSearchIndex(get()) }
    single<ConsoleIO>{RealConsoleIO()}
}