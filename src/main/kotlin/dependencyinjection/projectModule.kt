package dependencyinjection

import org.example.data.CsvMealParser
import org.example.data.CsvMealReader
import org.example.data.CsvRepositoryImpl
import org.example.logic.CsvRepository
import org.example.presentation.ConsoleUi
import org.koin.dsl.module

val projectModule = module {
    single { CsvMealReader("food.csv") }
    single { CsvMealParser() }
    single<CsvRepository> { CsvRepositoryImpl(get(), get()) }
    single { ConsoleUi() }
}