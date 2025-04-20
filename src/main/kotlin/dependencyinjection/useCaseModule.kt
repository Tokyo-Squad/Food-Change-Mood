package org.example.dependencyinjection

import org.example.logic.repository.MealRepository
import org.koin.dsl.module
import org.example.logic.usecase.EasyFoodSuggestionUseCase
import org.example.logic.usecase.GetMealsByAddDateUseCase
import org.example.logic.usecase.HighCalorieMealSuggestionUseCase
import org.example.logic.usecase.*


val useCaseModule = module {
    single { GetHealthyFastFoodMealsUseCase(get<MealRepository>()) }
    single { GetMealsByAddDateUseCase(get()) }
    single { GetSweetMealUseCase(mealRepository = get()) }
    single { GetSweetMealsNotContainEggUseCase(sweetMeal = get()) }
    single { SweetMealWithoutEggUseCase(sweetMealsNotContainEgg = get()) }
    single { EasyFoodSuggestionUseCase(get()) }
    single { GetLargeGroupItalyMealUseCase(mealRepository = get()) }
    single { GetIraqiMealsUseCase(mealRepository = get()) }
    single { GymHelperUseCase(mealRepository = get()) }
    single { ExploreCountriesFoodCultureUseCase(get()) }
    single { PlayIngredientGameUseCase(get()) }
    single { HighCalorieMealSuggestionUseCase(get()) }
    single { GetMealsByNameUseCase(get()) }
    single { GetRandomPotatoMealsUseCase(get()) }
    single { GetSeafoodMealsSortedByProteinUseCase(get()) }
    single { GetRandomMealUseCase(get()) }
    single { GenerateIngredientQuestionUseCase(get()) }
    single { KetoDietMealHelperUseCase(mealRepository = get()) }
    single { GetRandomPotatoMealsUseCase(get()) }
    single { GetSeafoodMealsSortedByProteinUseCase(get()) }

}