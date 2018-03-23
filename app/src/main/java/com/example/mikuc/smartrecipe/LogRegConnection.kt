package com.example.mikuc.smartrecipe

import android.app.Activity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import org.jetbrains.anko.*
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserInfo


interface LogRegInterface{
    fun stopProgeCircle()
    fun createDataBaseUserInfo(UserId: String, fistname: String, lastname: String)
    fun chaneUIinvisible()
    fun chaneUIvisible()
}
class LogReg(ac: Activity) : LogRegDialogInterface {

    override fun LogInbyEmailandPassword(email: String, password: String) {


        activity?.let {
            mAuth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {

                        Log.d("LOGGGGGGGGGGIN", "LOggggggggggggggggggggggggggggggggggggggggggggginnnn")

                        LogRegInterface?.chaneUIinvisible()
//                        LogRegInterface?.stopProgeCircle()


                    } else {
                        LogRegInterface?.chaneUIvisible()
//                        LogRegInterface?.stopProgeCircle()

                    }


                }
        }
    }

    private var LogRegInterface: LogRegInterface?=null
    private var mAuth: FirebaseAuth? = FirebaseAuth.getInstance()
    private var activity:Activity?= ac


    fun createUserbyEmailandPassword(email:String, password:String, fistname:String, lastname:String){

        this.activity?.let {
            mAuth!!
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(it) { task ->

                    if (task.isSuccessful) {

                        val userId = mAuth!!.currentUser!!.uid
                        LogRegInterface?.createDataBaseUserInfo(userId, fistname, lastname)
//                        LogRegInterface?.stopProgeCircle()



                    } else {
//                        LogRegInterface?.stopProgeCircle()

//                        alert("Błąd podczas rejestracji") {
//                            yesButton() {}
//
//                        }.show()

                    }
                }
        }
    }
    fun getUser(): FirebaseUser? {
        return mAuth?.currentUser
    }
    fun loginEmailandPassword(email: String, password: String)
    {
        mAuth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(activity!!) { task ->
                    if (task.isSuccessful) {

                        Log.d("LOGGGGGGGGGGIN", "LOggggggggggggggggggggggggggggggggggggggggggggginnnn")

                        LogRegInterface?.chaneUIinvisible()
//                        LogRegInterface?.stopProgeCircle()


                    } else {
                        LogRegInterface?.chaneUIvisible()
//                        LogRegInterface?.stopProgeCircle()

                    }


                }
    }

    fun IfLogedin(){
        val currentUser = mAuth?.currentUser
        if(currentUser!=null){
            LogRegInterface?.chaneUIinvisible()

        }else{
            LogRegInterface?.chaneUIvisible()
        }

    }

    fun LogOut()
    {
        mAuth?.signOut()
        LogRegInterface?.chaneUIvisible()
    }

    init {
        if(ac is LogRegInterface) {
            LogRegInterface = ac
        }
    }


}