package com.example.mikuc.smartrecipe.Authorization
import android.app.Activity
import android.util.Log
import com.example.mikuc.smartrecipe.DataBaseControl.FireBaseDB
import com.example.mikuc.smartrecipe.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase




interface LogRegConnectionInterface{
    fun inVisibleNavHeader()
    fun showProgress()
    fun hideProgress()
    fun visibleNavHeader()
    fun sentMassage(info:String)
    fun createFireBaseDb()
}
class LogRegConnection(ac: Activity) {

    private var mAuth: FirebaseAuth? = FirebaseAuth.getInstance()
    private var activity:Activity?= ac


    var inter : LogRegConnectionInterface?=null

    fun logInByEmailAndPassword(email: String, password: String) {
        inter?.showProgress()
        activity?.let {
            mAuth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        inter?.createFireBaseDb()
                        inter?.sentMassage("Logged in successfully")
                        inter?.inVisibleNavHeader()
                        inter?.hideProgress()

                        Log.d("Log", "Login ok")
                    } else {
                        inter?.sentMassage("Unsuccessful, check input data")
                        inter?.hideProgress()
                        Log.d("Log", "Bad Login")
                    }
                }
        }
    }

    fun createUserByEmailAndPassword(email:String, password:String, fistname:String, lastname:String){
        Log.d("msg", email+" "+password+" "+fistname+" "+lastname)
        inter?.showProgress()
        this.activity?.let {
            mAuth!!
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(it) { task ->

                    if (task.isSuccessful) {
                        inter?.sentMassage("Registered successfully")
                        inter?.hideProgress()
                        val userId = mAuth!!.currentUser!!.uid
                        createDataBaseUserInfo(userId, fistname, lastname)
                        Log.d("tag" , "creating acount is succesfull")

                    } else {
                        inter?.sentMassage("Error, try again")
                        inter?.hideProgress()
                        Log.d("msg", email+" "+password+" "+fistname+" "+lastname)
                        Log.d("tag" , "problem with creating acount")

                    }
                }
        }
    }

    fun createUserByFb(){

    }

    fun createUserByGoogle(){

    }

    fun getUser(): FirebaseUser? {
        return mAuth?.currentUser
    }

     private fun createDataBaseUserInfo(UserId: String, fistname: String, lastname: String) {
        val currentUserDb = FirebaseDatabase.getInstance().reference!!.child(UserId)
        currentUserDb.child("firstName").setValue(fistname)
        currentUserDb.child("lastName").setValue(lastname)
    }


    fun ifLogIn(){


        val currentUser = mAuth?.currentUser
        if(currentUser!=null){
            inter?.createFireBaseDb()
            inter?.inVisibleNavHeader()
        }
        else                  inter?.visibleNavHeader()
    }

    fun logOut() {
        FireBaseDB.recipesList?.clear()
        mAuth?.signOut()
    }


//         fun verifyEmail() {
//            val mUser = mAuth!!.currentUser;
//            mUser!!.sendEmailVerification()
//                    .addOnCompleteListener(this) { task ->
//                        if (task.isSuccessful) {
//                            Toast.makeText(this@MainActivity,
//                                    "Verification email sent to " + mUser.getEmail(),
//                                    Toast.LENGTH_SHORT).show()
//                        } else {
//                            Log.e("Tag1111", "sendEmailVerification", task.exception)
//                            Toast.makeText(this@MainActivity,
//                                    "Failed to send verification email.",
//                                    Toast.LENGTH_SHORT).show()
//                        }
//                    }
//        }

    init {
        if(ac is LogRegConnectionInterface)
        {
            inter=ac
        }
    }




}