package com.example.mikuc.smartrecipe.DataBaseControl

import com.example.mikuc.smartrecipe.DataModels.RecipeModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import android.util.Log
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener


interface FireBaseDbInterfaceRefreshAdapter{
    fun refreshAdapter()
}


class FireBaseDB {


    private var mAuth: FirebaseAuth? = FirebaseAuth.getInstance()
    var database:DatabaseReference?=null
    var inter:FireBaseDbInterfaceRefreshAdapter?=null

    fun setListener(lisener:FireBaseDbInterfaceRefreshAdapter)
    {
        inter=lisener
    }



    companion object {
        var recipesList:ArrayList<RecipeModel>?= ArrayList()

    }



    fun addRecipe(recipe:RecipeModel)
    {
        database?.child("Recipes")?.child(recipe.key)?.setValue(recipe)
    }

    private fun addRecipeListener(){
        database?.child("Recipes")?.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(p0: DataSnapshot?) {
                if(p0!!.exists()){
                    recipesList=ArrayList()
                    for(i in p0.children){

                        val data=i.getValue(RecipeModel::class.java)
                        recipesList!!.add(data!!)

                        Log.d("FireBaseDbb for", recipesList?.size.toString())
                    }
                }
                inter?.refreshAdapter()
            }
        })
        inter?.refreshAdapter()
    }

    fun removeRecipe(key:String){

        database?.child("Recipes")?.child(key)?.removeValue()

    }

    fun newInitialization()
    {
        database = FirebaseDatabase.getInstance().getReference(mAuth?.currentUser?.uid)
    }
    init {
        database = FirebaseDatabase.getInstance().getReference(mAuth?.currentUser?.uid)
        addRecipeListener()

        Log.d("FireBaseDbb init", recipesList?.size.toString())
        inter?.refreshAdapter()

    }


}