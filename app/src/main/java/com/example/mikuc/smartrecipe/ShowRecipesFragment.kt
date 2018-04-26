package com.example.mikuc.smartrecipe

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.mikuc.smartrecipe.DataBaseControl.FireBaseDB
import com.example.mikuc.smartrecipe.DataBaseControl.FireBaseDbInterfaceRefreshAdapter
import com.example.mikuc.smartrecipe.DataModels.RecipeModel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_show_recipes.*

class ShowRecipesFragment : Fragment(), FireBaseDbInterfaceRefreshAdapter {



    companion object {
        fun newInstance(): ShowRecipesFragment {
            return ShowRecipesFragment()
        }
    }

    var fireDataBase:FireBaseDB?=null
    var listView:ListView?=null
    private var listOfRecipes:ArrayList<RecipeModel>?= ArrayList()

     override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_show_recipes, container, false)
    }

    override fun onStart() {
        super.onStart()
        listView=view?.findViewById(R.id.show_recipes_list_view)
        fireDataBase?.setListener(this)
        setAdapter()
        show_recipes_list_view.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            val intent = Intent(activity, ShowRecipeDetails::class.java)
            intent.putExtra("TAG",position)
            startActivity(intent)
        }

        show_recipes_list_view.onItemLongClickListener=AdapterView.OnItemLongClickListener { parent, view, position, id ->

            val key:String= listOfRecipes!![position].key
            fireDataBase?.removeRecipe(key)

            true
        }
    }

     fun setAdapter(){
//        Toast.makeText(context,"Setting adaper yours recipes",Toast.LENGTH_SHORT).show()
        listOfRecipes=FireBaseDB.recipesList
        val recipeListNames:ArrayList<String>?=ArrayList()
        for(i in listOfRecipes!!){
            recipeListNames?.add(i.name)
        }
        val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, recipeListNames)
        listView?.adapter=adapter
    }

    override fun refreshAdapter() {
        setAdapter()
    }
     fun setDb(db:FireBaseDB)
     {
         fireDataBase=db
     }
}
