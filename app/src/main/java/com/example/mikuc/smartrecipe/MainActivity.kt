package com.example.mikuc.smartrecipe

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MyCallback, LogRegInterface {
    override fun chaneUIinvisible() {
        InVisibleNavHeader()
    }

    override fun chaneUIvisible() {
        VisibleNavHeader()
    }


    override fun createDataBaseUserInfo(UserId: String, fistname: String, lastname: String) {
        val currentUserDb = mDatabaseReference!!.child(UserId)
        currentUserDb.child("firstName").setValue(fistname)
        currentUserDb.child("lastName").setValue(lastname)
    }

    override fun stopProgeCircle() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun Logout() {
        logRegClass?.LogOut()
    }


    val manager = supportFragmentManager
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var facebookLoginBtn: Button? = null
    private var googleLoginBtn: Button? = null
    private var emailLoginBtn: Button? = null
    private var mAuth: FirebaseAuth? = null
    private var logRegClass: LogReg? = null


    override fun onStart() {
        super.onStart()
        logRegClass?.IfLogedin()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logRegClass = LogReg(this@MainActivity)
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase?.reference?.child("Users")

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setHomeAsUpIndicator(R.drawable.ic_menu)

        nav_view.setNavigationItemSelectedListener { menuItem ->

            menuItem.isChecked = true
            drawer_layout.closeDrawers()
            actionbar?.title = menuItem.title
            setFragment(menuItem.title.toString())
            true
        }

        val headerLayout = nav_view.getHeaderView(0)
        emailLoginBtn = headerLayout.findViewById<Button>(R.id.email_login_button)
        facebookLoginBtn = headerLayout.findViewById<Button>(R.id.fb_login_btn)
        googleLoginBtn = headerLayout.findViewById<Button>(R.id.google_login_btn)

        emailLoginBtn?.setOnClickListener {

            val dial = LogRegDialogs(this@MainActivity)
            dial.CreateLogInDialogByEmailandPassword()

//            val builder=AlertDialog.Builder(this@MainActivity)
//            val view=layoutInflater.inflate(R.layout.email_login_dialog,null)
//            title = "Logowanie"
//            val regButton=view.findViewById<Button>(R.id.reg_button)
//            builder.setView(view)
//                    .setPositiveButton("Zaloguj",
//                            { _, _ ->
//
//                                val email=view.findViewById<EditText>(R.id.email_emailLoginDialog).text.toString()
//                                val password=view.findViewById<EditText>(R.id.password_emailLoginDialog).text.toString()
//                                logRegClass?.loginEmailandPassword(email,password)
//
//                            })
//            builder.setNegativeButton("Anuluj", { _, _ ->
//
//            })
//            val dialog=builder.create()
//            dialog.show()

//            regButton.setOnClickListener {
//
//                val view2=layoutInflater.inflate(R.layout.email_reg_dialog,null)
//
//                builder.setView(view2)
//                        .setPositiveButton("Zarejestruj",
//                                { _, _ ->
//
//                                    val progres=indeterminateProgressDialog("Logowanie")
//                                    progres.show()
//                                    val email2=view.findViewById<EditText>(R.id.email_reg_LoginDialog).text.toString()
//                                    val password2=view.findViewById<EditText>(R.id.password_reg_LoginDialog).text.toString()
//                                    val firstName2=view.findViewById<EditText>(R.id.name_reg_LoginDialog).text.toString()
//                                    val lastName2=view.findViewById<EditText>(R.id.surname_reg_LoginDialog).text.toString()
//
//                                    logRegClass?.createUserbyEmailandPassword(email2,password2,firstName2,lastName2)
////                                    mAuth!!
////                                            .createUserWithEmailAndPassword(email2, password2)
////                                            .addOnCompleteListener(this) { task ->
////
////                                                if (task.isSuccessful) {
////                                                    progres.hide()
////                                                    val userId = mAuth!!.currentUser!!.uid
////                                                    val currentUserDb = mDatabaseReference!!.child(userId)
////                                                    currentUserDb.child("firstName").setValue(firstName2)
////                                                    currentUserDb.child("lastName").setValue(lastName2)
////
////                                                    InVisibleNavHeader()
////
////                                                } else {
////                                                    progres.hide()
////
////                                                    alert("Błąd podczas rejestracji") {
////                                                        yesButton() {}
////
////                                                    }.show()
////
////                                                }
////                                            }
//                                })
//                builder.setNegativeButton("Anuluj", { _, _ ->
//                }).create().show()
//            }

        }
        facebookLoginBtn?.setOnClickListener { }
        googleLoginBtn?.setOnClickListener { }
    }


    fun setFragment(id: String) {
        when (id) {

            "Dodaj Przepis" -> {
                manager.beginTransaction()
                        .replace(R.id.content_frame, AddRecipeFragment())
                        .addToBackStack("AddRecipeFragment").commit()
            }
            "Twoje Przepisy" -> {
                manager.beginTransaction()
                        .replace(R.id.content_frame, AddRecipeFragment())
                        .addToBackStack("AddRecipeFragment").commit()
            }
            "Inteligentny Pomocnik" -> {
                manager.beginTransaction()
                        .replace(R.id.content_frame, AddRecipeFragment())
                        .addToBackStack("AddRecipeFragment").commit()
            }
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser;
        mUser!!.sendEmailVerification()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@MainActivity,
                                "Verification email sent to " + mUser.getEmail(),
                                Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e("Tag1111", "sendEmailVerification", task.exception)
                        Toast.makeText(this@MainActivity,
                                "Failed to send verification email.",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }


    fun VisibleNavHeader() {
        facebookLoginBtn?.visibility = View.VISIBLE
        googleLoginBtn?.visibility = View.VISIBLE
        emailLoginBtn?.text = "Logowanie/Rejestracja przez email"
    }

    fun InVisibleNavHeader() {
        val user = logRegClass?.getUser()
        val nameUser = user?.email.toString()
        facebookLoginBtn?.visibility = View.GONE
        googleLoginBtn?.visibility = View.GONE
        emailLoginBtn?.text = "Witaj " + nameUser

    }


}


