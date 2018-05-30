package com.example.mikuc.smartrecipe.Dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.support.v7.app.AlertDialog
import android.widget.EditText
import android.widget.RadioButton
import com.example.mikuc.smartrecipe.R


interface TransferFromEditIngreadientDialog
{
    fun transferInormation(name: String, amount:String, unit:String )
}

class EditIngreadientDialog(context:Context, activity:Activity, name: String, amount:String, unit:String){


    var context:Context?=null
    var activity:Activity?=null
    var name: String?=null
    var amount:String?=null
    var unit:String?=null
    var inter: TransferFromEditIngreadientDialog?=null
    var nameEt:EditText?=null
    var amountEt:EditText?=null
    var g:RadioButton?=null
    var kg:RadioButton?=null
    var ml:RadioButton?=null
    var l:RadioButton?=null
    var sztuk:RadioButton?=null
    fun setListener(listener: TransferFromEditIngreadientDialog) {
        inter = listener
    }

    init {
        this.context=context
        this.activity=activity
        this.name=name
        this.amount=amount
        this.unit=unit
    }



     fun onCreateDialog(): Dialog {

        val ad = AlertDialog.Builder(context!!)
        val li = activity!!.layoutInflater
        val view = li.inflate(R.layout.add_ingreadient_dialog, null)

        nameEt=view?.findViewById(R.id.add_ingreadient_dialog_name_et)
        amountEt=view?.findViewById(R.id.add_ingreadient_dialog_amount_et)
        g=view?.findViewById(R.id.add_ingreadient_dialog_gram_rb)
        kg=view?.findViewById(R.id.add_ingreadient_dialog_kg_rb)
        ml=view?.findViewById(R.id.add_ingreadient_dialog_ml_rb)
        l=view?.findViewById(R.id.add_ingreadient_dialog_l_rb)
        sztuk=view?.findViewById(R.id.add_ingreadient_dialog_sztuk_rb)
        name=view?.findViewById<EditText>(R.id.add_ingreadient_dialog_name_et)?.text.toString()
        amount=view?.findViewById<EditText>(R.id.add_ingreadient_dialog_amount_et)?.text.toString()


         if(unit=="g") g?.isChecked=true
         if(unit=="kg") kg?.isChecked=true
         if(unit=="ml") ml?.isChecked=true
         if(unit=="l") l?.isChecked=true
         if(unit=="sztuk") sztuk?.isChecked=true

         amountEt?.setText(name)
         nameEt?.setText(amount)

        view.requestFocus()

        ad.setView(view)
                .setPositiveButton("Dodaj",
                        { _, _ ->



                            if(g!!.isChecked) unit="g"
                            if(kg!!.isChecked) unit="kg"
                            if(ml!!.isChecked) unit="ml"
                            if(l!!.isChecked) unit="l"
                            if(sztuk!!.isChecked) unit="sztuk"

                            inter?.transferInormation(name!!,amount!!,unit!!)

                        })
        ad.setNegativeButton("Anuluj", { _, _ ->

        })


        return ad.create()

    }
}
