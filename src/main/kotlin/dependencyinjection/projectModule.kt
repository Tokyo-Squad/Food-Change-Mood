package dependencyinjection

import org.example.data.CsvMealParser
import org.example.data.CsvMealReader
import org.example.data.CsvRepositoryImpl
import org.example.logic.CsvRepository
import org.example.logic.GetMealByIdUseCase
import org.example.logic.GetMealsByAddDateUseCase
import org.example.logic.SweetMealWithoutEggUseCase
import org.example.logic.EasyFoodSuggestionUseCase
import org.koin.dsl.module

val projectModule = module {
    single { CsvMealReader("./food.csv") }

    single { CsvMealParser() }

    single<CsvRepository> { CsvRepositoryImpl(get(), get()) }

    single { GetMealsByAddDateUseCase(get()) }
    
    single { GetMealByIdUseCase(get()) }

    single { SweetMealWithoutEggUseCase(csvRepository = get()) }
    
    single { EasyFoodSuggestionUseCase(get()) }

}