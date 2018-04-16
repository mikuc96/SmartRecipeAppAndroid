package com.example.mikuc.smartrecipe.Authorization
import android.app.Activity
import android.util.Log
import com.example.mikuc.smartrecipe.DataBaseControl.FireBaseDB
import com.example.mikuc.smartrecipe.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase




interface logRegConnectionInterface{
    fun InVisibleNavHeader()
    fun showProgress()
    fun hideProgress()
    fun VisibleNavHeader()
    fun sentMassage(info:String)
}
class LogRegConnection(ac: Activity) {

    private var mAuth: FirebaseAuth? = FirebaseAuth.getInstance()
    private var activity:Activity?= ac


    var inter : logRegConnectionInterface?=null

    fun LogInByEmailandPassword(email: String, password: String) {
        inter?.showProgress()
        activity?.let {
            mAuth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {

                        inter?.sentMassage("Logged in successfully")
                        inter?.InVisibleNavHeader()
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




    fun createUserByEmailandPassword(email:String, password:String, fistname:String, lastname:String){
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

     fun createDataBaseUserInfo(UserId: String, fistname: String, lastname: String) {
        val currentUserDb = FirebaseDatabase.getInstance().reference!!.child(UserId)
        currentUserDb.child("firstName").setValue(fistname)
        currentUserDb.child("lastName").setValue(lastname)
    }


    fun IfLogedin(){
        val currentUser = mAuth?.currentUser
        if(currentUser!=null) inter?.InVisibleNavHeader()
        else                  inter?.VisibleNavHeader()
    }

    fun LogOut() {
        mAuth?.signOut()
        MainActivity.database?.recipesList=ArrayList()
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
        if(ac is logRegConnectionInterface)
        {
            inter=ac
        }
    }




}