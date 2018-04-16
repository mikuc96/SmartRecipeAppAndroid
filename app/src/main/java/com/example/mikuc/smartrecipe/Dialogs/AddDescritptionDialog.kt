package com.example.mikuc.smartrecipe.Dialogs

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.widget.EditText
import com.example.mikuc.smartrecipe.R

interface TransferInformationFromAddDescritptionDialog{

    fun tranferData(msg:String)
}
class AddDescritptionDialog: DialogFragment(){

    private var descritption:String?=null
    var inter:TransferInformationFromAddDescritptionDialog?=null


    fun setListener(listener:TransferInformationFromAddDescritptionDialog)
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
                        inter?.tranferData(descritption!!)
                    }

                })
                .setNegativeButton("Cancel",{_,_->})

        return builder.create()
    }
}