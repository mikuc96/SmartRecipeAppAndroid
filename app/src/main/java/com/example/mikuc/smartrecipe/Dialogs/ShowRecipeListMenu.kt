package com.example.mikuc.smartrecipe.Dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

interface ShowRecipeListMenuInterafce
{
    fun removeRecipe()
    fun shareRecipe()
    fun editRecipe()
}

class ShowRecipeListMenu: DialogFragment()
{

    private var inter:ShowRecipeListMenuInterafce?=null
    private var listView:ListView?=null

     fun setListener(a:ShowRecipeListMenuInterafce)
    {
        inter=a
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = AlertDialog.Builder(context!!)
        val names = arrayOf("Udostępnij", "Edytuj", "Usuń")
        listView=ListView(context)
        val adapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, names)
        listView?.adapter = adapter
        listView?.setOnItemClickListener { parent, view, position, id ->
            if(position==0)
            {
                inter?.shareRecipe()
            }
            if(position==1)
            {
                inter?.editRecipe()
            }
            if(position==2)
            {
                inter?.removeRecipe()

            }
        }

        dialog.setView(listView)
        return dialog.create()
    }

}