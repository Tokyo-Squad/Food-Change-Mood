package org.example.logic

import org.example.model.Meal

interface ReactionProvider {
    fun like(meal: Meal)
    fun dislike(meal: Meal)
}

