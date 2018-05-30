package com.example.mikuc.smartrecipe

import android.content.Context
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
import com.example.mikuc.smartrecipe.Dialogs.ShowRecipeListMenu
import com.example.mikuc.smartrecipe.Dialogs.ShowRecipeListMenuInterafce
import com.example.mikuc.smartrecipe.Interfaces.AddRecipeFromShowRecipeFragment
import kotlinx.android.synthetic.main.fragment_show_recipes.*

class ShowRecipesFragment : Fragment(), FireBaseDbInterfaceRefreshAdapter, ShowRecipeListMenuInterafce {
    override fun removeRecipe() {

        fireDataBase?.removeRecipe(key!!)
        optionListDialog!!.dismiss()
        Toast.makeText(context,"Remove recipe "+key,Toast.LENGTH_SHORT).show()
    }

    override fun shareRecipe() {



    }

    override fun editRecipe() {

    }


    companion object {
        fun newInstance(): ShowRecipesFragment {
            return ShowRecipesFragment()
        }
    }
    private var key:String?=null

    private var inter: AddRecipeFromShowRecipeFragment?=null
    private var fireDataBase:FireBaseDB?=null
    private var listView:ListView?=null
    private var optionListDialog:ShowRecipeListMenu?=null
    private var listOfRecipes:ArrayList<RecipeModel>?= ArrayList()

     override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_show_recipes, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        optionListDialog= ShowRecipeListMenu()
        optionListDialog?.setListener(this)
    }

    override fun onStart() {
        super.onStart()
        listView=view?.findViewById(R.id.show_recipes_list_view)
        fireDataBase?.setListener(this)
        setAdapter()
        show_recipe_add_button.setOnClickListener {

            inter?.addRecipefromShowRecipeFragment()
        }
        show_recipes_list_view.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            val intent = Intent(activity, ShowRecipeDetails::class.java)
            intent.putExtra("TAG",position)
            startActivity(intent)
        }

        show_recipes_list_view.onItemLongClickListener=AdapterView.OnItemLongClickListener { parent, view, position, id ->

            key=listOfRecipes!![position].key
            optionListDialog?.show(fragmentManager, "optionListDialog")
            true
        }
    }

     fun setAdapter(){
        listOfRecipes=FireBaseDB.recipesList
         val recipeListNames:ArrayList<String>?=ArrayList()
         if(listOfRecipes!!.isEmpty())
         {

         }else
         {
             for(i in listOfRecipes!!){
                 recipeListNames?.add(i.name)
             }
         }


        val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, recipeListNames)
        listView?.adapter=adapter
    }

    fun listOfRecipesIsEmpty() : Boolean
    {
        return listOfRecipes!!.isEmpty()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is AddRecipeFromShowRecipeFragment)
        {
            inter=context
        }
    }

    override fun refreshAdapter() {
        setAdapter()
    }
     fun setDb(db:FireBaseDB)
     {
         fireDataBase=db
     }
}
