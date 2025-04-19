package dependencyinjection

import org.example.data.CsvMealParser
import org.example.data.CsvMealReader
import org.example.data.CsvRepositoryImpl
import org.example.logic.CsvRepository
import org.example.logic.EasyFoodSuggestionUseCase
import org.example.logic.KetoDietMealHelperUseCase
import org.example.logic.SweetMealWithoutEggUseCase
import org.example.utils.MealSearchIndex
import org.example.presentation.ConsoleUi
import org.koin.dsl.module

val projectModule = module {
    single { CsvMealReader("food.csv") }
    single { CsvMealParser() }
    single<CsvRepository> { CsvRepositoryImpl(get(), get()) }
    single { MealSearchIndex(get()) }
    single { KetoDietMealHelperUseCase(csvRepository = get()) }
    single { SweetMealWithoutEggUseCase(csvRepository = get(), sweetMealsNotContainEgg = get()) }
    single { EasyFoodSuggestionUseCase(get()) }
    single { ConsoleUi() }
}