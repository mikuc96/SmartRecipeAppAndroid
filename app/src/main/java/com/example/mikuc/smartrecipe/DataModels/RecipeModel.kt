package com.example.mikuc.smartrecipe.DataModels


data class RecipeModel(val name:String,
                       val hardness:String,
                       val ingredients: ArrayList<Ingredient>,
                       val description:String,
                       val time:String,
                       val people:String,
                       val key:String)
{
    constructor():this("","", arrayListOf(),"","","",""){}
}
