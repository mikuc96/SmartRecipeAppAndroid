package com.example.mikuc.smartrecipe.DataModels

data class Ingredient(val name:String, val amount:String, val unit:String)
{
    constructor(): this("","","")
}