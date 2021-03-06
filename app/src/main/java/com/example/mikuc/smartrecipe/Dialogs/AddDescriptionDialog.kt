package com.example.mikuc.smartrecipe.Dialogs

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.widget.EditText
import com.example.mikuc.smartrecipe.R

interface SendDescriptionToAddRecipeFragment{

    fun transferData(msg:String)
}
class AddDescriptionDialog: DialogFragment(){

    private var descritption:String?=null
    var inter:SendDescriptionToAddRecipeFragment?=null


    fun setListener(listener:SendDescriptionToAddRecipeFragment)
    {
        inter=listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder= AlertDialog.Builder(context!!)
        val li = activity!!.layoutInflater
        val view = li.inflate(R.layout.add_description_dialog, null)
        builder.setView(view)
                .setPositiveButton("Ok",{_,_->

                    val et=view.findViewById<EditText>(R.id.add_description_dialog_et)
                    descritption=et.text.toString()
                    if(descritption!=null)
                    {
                        inter?.transferData(descritption!!)
                    }

                })
                .setNegativeButton("Cancel",{_,_->})

        return builder.create()
    }
}