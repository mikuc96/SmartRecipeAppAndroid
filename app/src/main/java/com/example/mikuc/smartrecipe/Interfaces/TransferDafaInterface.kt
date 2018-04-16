package com.example.mikuc.smartrecipe.Interfaces

import com.example.mikuc.smartrecipe.DataModels.Ingredient

interface transferDataFromIngreadientDialog{
    fun sendData(item: Ingredient)
}