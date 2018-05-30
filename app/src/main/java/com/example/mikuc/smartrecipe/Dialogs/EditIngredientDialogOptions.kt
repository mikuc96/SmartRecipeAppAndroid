package com.example.mikuc.smartrecipe.Dialogs
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog


interface TranferFromEditIngreadientDialogOptions
{
    fun deleteIngreadient()
    fun editIngreadient()
}
class EditIngredientDialogOptions: DialogFragment(){


    private var inter: TranferFromEditIngreadientDialogOptions?=null


    fun setListener(listener:TranferFromEditIngreadientDialogOptions)
    {
        inter=listener
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder= AlertDialog.Builder(context!!)
        builder.setView(view)


                .setNeutralButton("Delete",{_,_->
                    inter?.deleteIngreadient()
                })

                .setPositiveButton("Edit",{_,_->

                    inter?.editIngreadient()
                })

                .setNegativeButton("Cancel",{_,_->

                })

        return builder.create()
    }
}