package com.example.mikuc.smartrecipe.Dialogs

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.WindowManager

import android.widget.EditText
import android.widget.RadioButton
import com.example.mikuc.smartrecipe.DataModels.Ingredient
import com.example.mikuc.smartrecipe.Interfaces.transferDataFromIngreadientDialog
import com.example.mikuc.smartrecipe.R


class AddIngredientDialog: DialogFragment(){

    var name: String?=null
    var amount:String?=null
    var unit:String?=null
    var inter: transferDataFromIngreadientDialog?=null

    fun setListener(listener: transferDataFromIngreadientDialog) {
        inter = listener
    }

    fun prepareDialog(name: String,amount:String,unit:String)
    {
        this.name=name
        this.amount=amount
        this.unit=unit
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val ad = AlertDialog.Builder(context!!)
        val li = activity!!.layoutInflater
        val view = li.inflate(R.layout.add_ingreadient_dialog, null)
        view.requestFocus()

        ad.setView(view)
                .setPositiveButton("Dodaj",
                        { _, _ ->

                            name=view?.findViewById<EditText>(R.id.add_ingreadient_dialog_name_et)?.text.toString()
                            amount=view?.findViewById<EditText>(R.id.add_ingreadient_dialog_amount_et)?.text.toString()

                            val g=view?.findViewById<RadioButton>(R.id.add_ingreadient_dialog_gram_rb)
                            val kg=view?.findViewById<RadioButton>(R.id.add_ingreadient_dialog_kg_rb)
                            val ml=view?.findViewById<RadioButton>(R.id.add_ingreadient_dialog_ml_rb)
                            val l=view?.findViewById<RadioButton>(R.id.add_ingreadient_dialog_l_rb)
                            val sztuk=view?.findViewById<RadioButton>(R.id.add_ingreadient_dialog_sztuk_rb)
                            val brak=view?.findViewById<RadioButton>(R.id.add_ingreadient_dialog_brak_rb2)

                            if(g!!.isChecked) unit="g"
                            if(kg!!.isChecked) unit="kg"
                            if(ml!!.isChecked) unit="ml"
                            if(l!!.isChecked) unit="l"
                            if(sztuk!!.isChecked) unit="sztuk"
                            if(brak!!.isChecked) unit=""
                            inter?.sendData(Ingredient(name!!,amount!!,unit!!))

                        })
        ad.setNegativeButton("Anuluj", { _, _ ->

        })


        return ad.create()

    }
}
