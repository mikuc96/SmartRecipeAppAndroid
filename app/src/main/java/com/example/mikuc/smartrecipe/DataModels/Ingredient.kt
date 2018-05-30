package com.example.mikuc.smartrecipe.DataModels

data class Ingredient(var name:String, var amount:String, var unit:String)
{
    constructor(): this("","","")
}