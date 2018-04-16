package com.example.mikuc.smartrecipe.Dialogs

import android.app.Activity
import android.support.v7.app.AlertDialog
import android.widget.EditText
import com.example.mikuc.smartrecipe.Authorization.LogRegConnection
import com.example.mikuc.smartrecipe.R

class RegByLoginAndPasswordDialog (ac: Activity)
{

    private val activity: Activity?=ac
    private var AlertBuilder= AlertDialog.Builder(ac)
    private var logRegClass: LogRegConnection?= LogRegConnection(ac)


    fun createRegbyLoginAndPasswordDialog()
    {
        val view=activity?.layoutInflater?.inflate(R.layout.email_reg_dialog,null)
        AlertBuilder.setView(view)
                .setPositiveButton("Zarejestruj",
                        { _, _ ->

                            val name=view?.findViewById<EditText>(R.id.name_reg_LoginDialog)?.text.toString()
                            val surname=view?.findViewById<EditText>(R.id.surname_reg_LoginDialog)?.text.toString()
                            val email=view?.findViewById<EditText>(R.id.email_reg_LoginDialog)?.text.toString()
                            val password=view?.findViewById<EditText>(R.id.password_reg_LoginDialog)?.text.toString()

                            logRegClass?.createUserbyEmailandPassword(email,password,name, surname)
                            logRegClass?.LogInbyEmailandPassword(email,password)

                        })
        AlertBuilder.setNegativeButton("Anuluj", { _, _ ->

        })
        val dialog=AlertBuilder.create()
        dialog.show()
    }
}