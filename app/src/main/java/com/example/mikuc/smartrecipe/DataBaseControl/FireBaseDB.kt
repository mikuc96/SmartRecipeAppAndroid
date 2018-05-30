package com.example.mikuc.smartrecipe.DataBaseControl

import com.example.mikuc.smartrecipe.DataModels.RecipeModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import android.util.Log
import android.widget.Toast
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

    fun setListener(listener:FireBaseDbInterfaceRefreshAdapter)
    {
        inter=listener
    }



    companion object {
        var recipesList:ArrayList<RecipeModel>?= ArrayList()
        var firstName:String?=null
        var lastName:String?=null

    }

    fun addRecipe(recipe:RecipeModel)
    {
        database?.child("Recipes")?.child(recipe.key)?.setValue(recipe)
    }

    fun searchQuery()
    {
        val data=database?.child("Recipes")?.child("name")?.equalTo("Grd")

        Log.d("Tag", data.toString())

    }

    private fun getUserFirstNameListener()
    {
        database?.child("firstName")?.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(p0: DataSnapshot?) {

                firstName=p0?.value.toString()

            }

            override fun onCancelled(p0: DatabaseError?) {

            }
        })
    }
    private fun getUserLastNameListener()
    {
        database?.child("lastName")?.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(p0: DataSnapshot?) {

                lastName=p0?.value.toString()

            }

            override fun onCancelled(p0: DatabaseError?) {

            }
        })
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

    init {

        database = FirebaseDatabase.getInstance().getReference(mAuth?.currentUser?.uid)
        addRecipeListener()
        getUserFirstNameListener()
        getUserLastNameListener()

        Log.d("FireBaseDbb init", recipesList?.size.toString())
        inter?.refreshAdapter()

    }


}