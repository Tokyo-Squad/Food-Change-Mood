package org.example.dependencyinjection

import org.example.logic.*
import org.koin.dsl.module
import org.example.logic.usecase.EasyFoodSuggestionUseCase
import org.example.logic.usecase.GetMealsByAddDateUseCase
import org.example.logic.usecase.HighCalorieMealSuggestionUseCase
import org.example.logic.usecase.*


val useCaseModule = module {
    single { GetHealthyFastFoodMealsUseCase(get<CsvRepository>()) }
    single { GetMealsByAddDateUseCase(get()) }
    single { GetSweetMealUseCase(csvRepository = get()) }
    single { GetSweetMealsNotContainEggUseCase(sweetMeal = get()) }
    single { SweetMealWithoutEggUseCase(sweetMealsNotContainEgg = get()) }
    single { EasyFoodSuggestionUseCase(get()) }
    single { GetLargeGroupItalyMealUseCase(csvRepository = get()) }
    single { GetIraqiMealsUseCase(csvRepository = get()) }
    single { GymHelperUseCase(csvRepository = get()) }
    single { ExploreCountriesFoodCultureUseCase(get()) }
    single { PlayIngredientGameUseCase(get()) }
    single { HighCalorieMealSuggestionUseCase(get()) }
    single { GetMealsByNameUseCase(get()) }
    single { GetRandomPotatoMealsUseCase(get()) }
    single { GetSeafoodMealsSortedByProteinUseCase(get()) }
    single { GetRandomMealUseCase(get()) }
    single { GenerateIngredientQuestionUseCase(get()) }
    single { KetoDietMealHelperUseCase(csvRepository = get()) }
    single { GetRandomPotatoMealsUseCase(get()) }
    single { GetSeafoodMealsSortedByProteinUseCase(get()) }

}