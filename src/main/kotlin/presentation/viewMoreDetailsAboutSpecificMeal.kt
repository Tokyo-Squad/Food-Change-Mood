package org.example.presentation

import org.example.model.Meal

fun viewMoreDetailsAboutSpecificMeal(meal: Meal) {
    println("name: ${meal.name}\n" +
            "description : ${meal.description}\n" +
            "ingredients: ${meal.ingredients}\n" +
            "number of ingredients : ${meal.numberOfIngredients}\n" +
            "steps : ${meal.steps}\n" +
            "number of steps : ${meal.numberOfSteps}\n" +
            "tags: ${meal.tags}\n"
    )
}