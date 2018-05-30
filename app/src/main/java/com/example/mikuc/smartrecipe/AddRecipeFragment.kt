package com.example.mikuc.smartrecipe
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import com.example.mikuc.smartrecipe.DataModels.Ingredient
import com.example.mikuc.smartrecipe.DataModels.RecipeModel
import com.example.mikuc.smartrecipe.Dialogs.*
import com.example.mikuc.smartrecipe.Interfaces.AddRecipeInterface
import com.example.mikuc.smartrecipe.Interfaces.transferDataFromIngreadientDialog

import com.example.mikuc.smartrecipe.RecycleView.IngredientsRecycleViewAdapter
import com.example.mikuc.smartrecipe.RecycleView.RecyclerItemClickListener

import kotlinx.android.synthetic.main.fragment_add_recipe.*

class AddRecipeFragment : Fragment(),
        transferDataFromIngreadientDialog,
        SendDescriptionToAddRecipeFragment,
        TranferFromEditIngreadientDialogOptions,
        TransferFromEditIngreadientDialog
{
    override fun transferInormation(name: String, amount: String, unit: String) {
        ingredientsList?.get(ingredientPossition!!)?.name=name
        ingredientsList?.get(ingredientPossition!!)?.amount=amount
        ingredientsList?.get(ingredientPossition!!)?.unit=unit
        setAdapter()
    }


    override fun editIngreadient() {
        nameIngreadient=ingredientsList?.get(ingredientPossition!!)?.name
        amountIngreadient=ingredientsList?.get(ingredientPossition!!)?.amount
        unitIngreadient=ingredientsList?.get(ingredientPossition!!)?.unit
        editIngreadientDialog= EditIngreadientDialog(context!!,activity!!,
                nameIngreadient!!,amountIngreadient!!, unitIngreadient!!)
        editIngreadientDialog!!.setListener(this)
        editIngreadientDialog!!.onCreateDialog().show()



    }

    override fun deleteIngreadient() {
        Toast.makeText(context,"Delete",Toast.LENGTH_SHORT).show()
        ingredientsList?.removeAt(ingredientPossition!!)
        setAdapter()
    }

    private var nameIngreadient:String?=null
    private var amountIngreadient:String?=null
    private var unitIngreadient:String?=null
    private var addIngredientDialog: AddIngredientDialog? = null
    private var addDescriptionDialog: AddDescriptionDialog? = null
    private var editIngreadientDialog: EditIngreadientDialog? = null
    private var editIngredientDialogOptions: EditIngredientDialogOptions? = null
    private var adapter: IngredientsRecycleViewAdapter? = null
    private var ingredientsList: ArrayList<Ingredient>? = ArrayList()
    private var ingredientPossition:Int?=null
    private var name:String?=null
    private var hardness:String?=null
    private var people:String?=null
    private var time:String?=null
    private var descriptionString:String?=null
    private var recycleView:RecyclerView?=null


    private var myInterface:AddRecipeInterface?=null

    fun setListener(listener:AddRecipeInterface){
        myInterface=listener

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

    recycleView=view?.findViewById(R.id.recycle_view)
        return inflater.inflate(R.layout.fragment_add_recipe, container, false)
    }



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        addIngredientDialog = AddIngredientDialog()
        addDescriptionDialog = AddDescriptionDialog()
        editIngredientDialogOptions=EditIngredientDialogOptions()


        editIngreadientDialog?.setListener(this)
        editIngredientDialogOptions?.setListener(this)
        addIngredientDialog?.setListener(this)
        addDescriptionDialog?.setListener(this)

    }



    override fun onStart() {
        super.onStart()
        fragmentManager?.addOnBackStackChangedListener { activity?.actionBar?.title="Twoje Przepisy" }
        recycle_view.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        setAdapter()

        recycle_view.addOnItemTouchListener(
                RecyclerItemClickListener(context!!, recycle_view, object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onLongItemClick(view: View?, position: Int) {
                        Toast.makeText(context!!,"longgg click",Toast.LENGTH_SHORT).show()
                    }

                    override fun onItemClick(view: View, position: Int) {
                        ingredientPossition=position
                        editIngredientDialogOptions?.show(fragmentManager, "Edit Ingredient Options")
                    }

                })
        )


        clearData()
        add_ingredient_btn.setOnClickListener {
            addIngredientDialog?.show(fragmentManager, "Add ingreadient")
        }
        add_description_btn.setOnClickListener {
            addDescriptionDialog?.show(fragmentManager, "Add description")
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


            if(!name!!.isEmpty() && !ingredientsList!!.isEmpty() && !descriptionString.isNullOrEmpty())
            {
                val recipe=RecipeModel(name!!,hardness!!,ingredientsList!!,descriptionString!!,time!!,people!!,hashCode().toString())
                myInterface?.addRecipeToFireBaseDb(recipe)
                fragmentManager?.popBackStack()

                Toast.makeText(context, "Dodano "+name +" pomyślnie",Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(context,"Wprowadź brakujące dane",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setAdapter() {
        adapter = IngredientsRecycleViewAdapter(ingredientsList!!)
        recycle_view.adapter = adapter
    }

    override fun transferData(msg: String) {
        fragment_add_recipe_description_tv.text = msg
        descriptionString=msg
    }

    override fun sendData(item: Ingredient) {
        ingredientsList?.add(item)
        setAdapter()
    }

    private fun clearData()
    {
        add_recipe_name_et.text.clear()
        ingredientsList?.clear()
        fragment_add_recipe_description_tv.text=""
        add_recipe_rb_easy.isChecked=true
        add_recipe_rb_1.isChecked=true
        add_recipe_rb_10min.isChecked=true
    }
}

