package com.example.mikuc.smartrecipe.LogRegDialog

import android.app.Activity
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.mikuc.smartrecipe.Authorization.LogRegConnection
import com.example.mikuc.smartrecipe.R


/**
 * Created by mikuc on 3/18/18.
 */

class LoginByEmailAndPasswordDialogs(activity:Activity)
{
    val ac:Activity=activity

    private var AlertBuilder= AlertDialog.Builder(activity)
    private var logRegClass: LogRegConnection?= LogRegConnection(activity)

    fun showLogInByEmailandPasswordDialog()
    {
        val view= ac.layoutInflater?.inflate(R.layout.email_login_dialog,null)
        val regButton=view?.findViewById<Button>(R.id.reg_button)
        AlertBuilder.setView(view)
                .setPositiveButton("Zaloguj",
                        { _, _ ->

                            val email=view?.findViewById<EditText>(R.id.email_emailLoginDialog)?.text.toString()
                            val password=view?.findViewById<EditText>(R.id.password_emailLoginDialog)?.text.toString()

                            logRegClass?.LogInbyEmailandPassword(email,password)
                            Log.d("msg", email+" "+password)

                        })
        AlertBuilder.setNegativeButton("Anuluj", { _, _ ->

        })
        val dialog=AlertBuilder.create()
        dialog.show()
        regButton?.setOnClickListener {
            dialog.hide()
            val regDialog=RegByLoginAndPasswordDialog(ac)
            regDialog.createRegbyLoginAndPasswordDialog()
        }

    }
}
