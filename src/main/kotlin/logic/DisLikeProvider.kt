package org.example.logic

import org.example.model.Meal

interface DisLikeProvider {
    fun dislike(meal: Meal)
}

