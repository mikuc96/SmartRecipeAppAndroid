package com.example.mikuc.smartrecipe
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.support.v4.view.GravityCompat
import android.support.v7.widget.Toolbar
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.mikuc.smartrecipe.Authorization.LogRegConnection
import com.example.mikuc.smartrecipe.Authorization.logRegConnectionInterface
import com.example.mikuc.smartrecipe.DataBaseControl.FireBaseDB
import com.example.mikuc.smartrecipe.Dialogs.LoginByEmailAndPasswordDialogs

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity(), logRegConnectionInterface, ProfileFragment.profileFragmentInterface {



    private val manager = supportFragmentManager
    private var facebookLoginBtn:Button? = null
    private var googleLoginBtn:Button? = null
    private var emailLoginBtn:Button? = null
    private var logRegClass: LogRegConnection?=null
    private var dial: LoginByEmailAndPasswordDialogs?=null

    companion object {
        var database:FireBaseDB?=FireBaseDB()
    }

    override fun onStart() {
        super.onStart()
        logRegClass?.IfLogedin()
        progressBar2.visibility=View.GONE
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        logRegClass= LogRegConnection(this@MainActivity)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setHomeAsUpIndicator(R.drawable.ic_menu)

        nav_view.setNavigationItemSelectedListener { menuItem ->

            menuItem.isChecked = true
            actionbar?.title = menuItem.title
            setFragment(menuItem.title.toString())
            drawer_layout.closeDrawers()
            true
        }

        val headerLayout =nav_view.getHeaderView(0)
        emailLoginBtn=headerLayout.findViewById(R.id.email_login_button)
        facebookLoginBtn=headerLayout.findViewById(R.id.fb_login_btn)
        googleLoginBtn=headerLayout.findViewById(R.id.google_login_btn)
        dial= LoginByEmailAndPasswordDialogs(this@MainActivity)
        VisibleNavHeader()
        nav_view.menu.getItem(0).isChecked = true
        actionbar?.title="Twoje Przepisy"
        setFragment("Twoje Przepisy")


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
                        .replace(R.id.content_frame, ShowRecipesFragment())
                        .addToBackStack("ShowRecipesFragment").commit()
            }
            "Inteligentny Pomocnik" -> {
                manager.beginTransaction()
                        .replace(R.id.content_frame, AssistantFragment())
                        .addToBackStack("AssistantFragment").commit()
            }
            "Twój Profil" -> {
                manager.beginTransaction()
                        .replace(R.id.content_frame, ProfileFragment())
                        .addToBackStack("ProfileFragment").commit()
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

    override fun VisibleNavHeader()
    {

        facebookLoginBtn?.visibility=View.VISIBLE
        googleLoginBtn?.visibility= View.VISIBLE
        emailLoginBtn?.text="Logowanie/Rejestracja przez email"
        emailLoginBtn?.setOnClickListener {
            dial?.showLogInByEmailandPasswordDialog()
            drawer_layout.closeDrawers()
        }
        facebookLoginBtn?.setOnClickListener {  }
        googleLoginBtn?.setOnClickListener {  }

    }
    override fun InVisibleNavHeader()
    {
        val user=logRegClass?.getUser()
        val nameUser=user?.email.toString()
        facebookLoginBtn?.visibility=View.GONE
        googleLoginBtn?.visibility= View.GONE
        emailLoginBtn?.text="Witaj "+nameUser
        emailLoginBtn?.setOnClickListener {
            setFragment("Twój Profil")
            drawer_layout.closeDrawers()
        }
    }

    override fun logOut() {

        Toast.makeText(applicationContext, "Logout succesfull", Toast.LENGTH_LONG).show()
        logRegClass?.LogOut()
        VisibleNavHeader()
    }

    override fun sentMassage(info: String) {
        Toast.makeText(applicationContext, info, Toast.LENGTH_LONG).show()
    }

    override fun hideProgress() {
        progressBar2.visibility=View.GONE
    }

    override fun showProgress() {
        progressBar2.visibility=View.VISIBLE
    }




}



