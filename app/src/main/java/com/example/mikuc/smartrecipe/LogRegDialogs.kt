package com.example.mikuc.smartrecipe

import android.app.Activity
import android.app.Dialog
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Button
import android.widget.EditText

/**
 * Created by mikuc on 3/18/18.
 */
interface LogRegDialogInterface{
    fun LogInbyEmailandPassword(email:String, password:String)
}
class LogRegDialogs(ac: Activity)
{

    private var inter: LogRegDialogInterface?=null


    private val activity: Activity?=ac
    private val LoginDialogbyEmailandPassword: Dialog?=null
    private val RegDialogbyEmailandPassword: Dialog?=null
    private var AlertBuilder= AlertDialog.Builder(ac)
    private var logRegClass: LogReg?=LogReg(ac)


    fun CreateLogInDialogByEmailandPassword()
    {
        val view=activity?.layoutInflater?.inflate(R.layout.email_login_dialog,null)
        val regButton=view?.findViewById<Button>(R.id.reg_button)
        AlertBuilder.setView(view)
                .setPositiveButton("Zaloguj",
                        { _, _ ->

                            val email=view?.findViewById<EditText>(R.id.email_emailLoginDialog)?.text.toString()
                            val password=view?.findViewById<EditText>(R.id.password_emailLoginDialog)?.text.toString()

                            inter?.LogInbyEmailandPassword(email,password)
                            Log.d("msg", "omsdifmisjdoifjsoidjfsoidjfsoidf")

                        })
        AlertBuilder.setNegativeButton("Anuluj", { _, _ ->

        })
        val dialog=AlertBuilder.create()
        dialog.show()
    }


    init {
        if(ac is LogRegDialogInterface)
        {
            inter=ac
        }
    }
}
