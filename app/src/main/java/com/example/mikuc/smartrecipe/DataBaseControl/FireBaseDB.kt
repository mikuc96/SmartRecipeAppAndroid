package com.example.mikuc.smartrecipe.DataBaseControl

import com.example.mikuc.smartrecipe.DataModels.RecipeModel
import com.google.firebase.auth.FirebaseAuth
import android.content.ContentValues.TAG
import com.google.firebase.database.*
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener


interface FireBaseDbInterfaceRefreshAdapter{
    fun RefreshAdapter()
}


class FireBaseDB {


    private var mAuth: FirebaseAuth? = FirebaseAuth.getInstance()
    private var database:DatabaseReference?=null
    var inter:FireBaseDbInterfaceRefreshAdapter?=null
    init {

        if(mAuth?.currentUser!=null) database = FirebaseDatabase.getInstance().getReference(mAuth?.currentUser?.uid)

    }

    fun setListener(lisener:FireBaseDbInterfaceRefreshAdapter)
    {
        inter=lisener
    }



    var recipesList:ArrayList<RecipeModel>?= ArrayList()



    fun addRecipe(recipe:RecipeModel)
    {
        database?.child("Recipes")?.child(hashCode().toString())?.setValue(recipe)
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
                inter?.RefreshAdapter()
            }
        })
    }


    init {
        addRecipeListener()
        Log.d("FireBaseDbb init", recipesList?.size.toString())
    }


}