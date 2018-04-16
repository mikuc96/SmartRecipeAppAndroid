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
import com.example.mikuc.smartrecipe.DataBaseControl.FireBaseDbInterfaceRefreshAdapter
import com.example.mikuc.smartrecipe.DataModels.RecipeModel
import kotlinx.android.synthetic.main.fragment_show_recipes.*

class ShowRecipesFragment : Fragment(), FireBaseDbInterfaceRefreshAdapter {

    companion object {
        fun newInstance(): ShowRecipesFragment {
            return ShowRecipesFragment()
        }
    }

    var ListView:ListView?=null

    private var listOfRecipes:ArrayList<RecipeModel>?= ArrayList()

     override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_show_recipes, container, false)
    }

    override fun onStart() {
        super.onStart()
        ListView=view?.findViewById(R.id.show_recipes_list_view)
        MainActivity.database?.setListener(this)
        setAdapter()
        show_recipes_list_view.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            val intent = Intent(activity, ShowRecipeDetails::class.java)
            intent.putExtra("TAG",position)
            startActivity(intent)
        }
    }

    private fun setAdapter(){
        listOfRecipes=MainActivity.database?.recipesList
        val recipeListNames:ArrayList<String>?=ArrayList()
        for(i in listOfRecipes!!){
            recipeListNames?.add(i.name)
        }
        val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, recipeListNames)
        ListView?.adapter=adapter
    }

    override fun RefreshAdapter() {
        setAdapter()
    }

}
