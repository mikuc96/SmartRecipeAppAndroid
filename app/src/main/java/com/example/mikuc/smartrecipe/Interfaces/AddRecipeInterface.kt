package com.example.mikuc.smartrecipe.Interfaces

import com.example.mikuc.smartrecipe.DataModels.RecipeModel

interface AddRecipeInterface{
    fun startShowRecipeFragment()
    fun addRecipeToFireBaseDb(recipe: RecipeModel)
}