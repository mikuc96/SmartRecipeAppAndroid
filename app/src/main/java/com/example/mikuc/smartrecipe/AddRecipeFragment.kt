package com.example.mikuc.smartrecipe
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import com.example.mikuc.smartrecipe.DataModels.Ingredient
import com.example.mikuc.smartrecipe.DataModels.RecipeModel
import com.example.mikuc.smartrecipe.Dialogs.AddDescritptionDialog
import com.example.mikuc.smartrecipe.Dialogs.AddIngredientDialog
import com.example.mikuc.smartrecipe.Dialogs.TransferInformationFromAddDescritptionDialog
import com.example.mikuc.smartrecipe.Interfaces.transferDataFromIngreadientDialog

import com.example.mikuc.smartrecipe.RecycleView.IngredientsRecycleViewAdapter
import kotlinx.android.synthetic.main.fragment_add_recipe.*


class AddRecipeFragment : Fragment(), transferDataFromIngreadientDialog, TransferInformationFromAddDescritptionDialog {


    var AddIngreadientDialog: AddIngredientDialog? = null
    var AddDescritptionDialog: AddDescritptionDialog? = null
    var adapter: IngredientsRecycleViewAdapter? = null

    var IngreadientsList: ArrayList<Ingredient>? = ArrayList()
    var name:String?=null
    var hardness:String?=null
    var people:String?=null
    var time:String?=null
    var description_string:String?=null
    var description: TextureView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_add_recipe, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AddIngreadientDialog = AddIngredientDialog()
        AddDescritptionDialog = AddDescritptionDialog()

        AddIngreadientDialog?.setListener(this)
        AddDescritptionDialog?.setListener(this)
    }

    override fun onStart() {
        super.onStart()

        recycle_view.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        setAdapter()



        add_ingredient_btn.setOnClickListener {
            AddIngreadientDialog?.show(fragmentManager, "Add ingreadient")
        }
        add_description_btn.setOnClickListener {
            AddDescritptionDialog?.show(fragmentManager, "Add description")
        }
        add_recipe_btn.setOnClickListener {

            name= add_recipe_name_et.text.toString()

            if(add_recipe_rb_easy.isChecked) hardness="łatwy"
            if(add_recipe_rb_medium.isChecked) hardness="średni"
            if(add_recipe_hard.isChecked) hardness="trudny"

            if(add_recipe_rb_1.isChecked) people="1"
            if(add_recipe_rb_2.isChecked) people="2"
            if(add_recipe_rb_3.isChecked) people="3"
            if(add_recipe_rb_4.isChecked) people="4"
            if(add_recipe_rb_5.isChecked) people="5"

            if(add_recipe_rb_10min.isChecked) time="10min"
            if(add_recipe_rb_20min.isChecked) time="20min"
            if(add_recipe_rb_40min.isChecked) time="40min"
            if(add_recipe_rb_1h.isChecked) time="1h"
            if(add_recipe_rb_2h.isChecked) time="2h"

            val recipe=RecipeModel(name!!,hardness!!,IngreadientsList!!,description_string!!,time!!,people!!)

            Toast.makeText(context,"cos",Toast.LENGTH_SHORT).show()

            MainActivity.database?.addRecipe(recipe)
        }
    }

    fun setAdapter() {
        adapter = IngredientsRecycleViewAdapter(IngreadientsList!!)
        recycle_view.adapter = adapter
    }

    override fun tranferData(msg: String) {

        fragment_add_recipe_description_tv.text = msg
        description_string=msg
    }

    override fun sendData(item: Ingredient) {

        IngreadientsList?.add(item)
        setAdapter()
    }

}

