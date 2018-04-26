package com.example.mikuc.smartrecipe

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.mikuc.smartrecipe.Authorization.LogRegConnection
import com.example.mikuc.smartrecipe.Authorization.LogRegConnectionInterface
import com.example.mikuc.smartrecipe.DataBaseControl.FireBaseDB
import com.example.mikuc.smartrecipe.DataModels.RecipeModel
import com.example.mikuc.smartrecipe.Dialogs.LoginByEmailAndPasswordDialogs
import com.example.mikuc.smartrecipe.Interfaces.AddRecipeInterface
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity(), LogRegConnectionInterface,
        ProfileFragment.profileFragmentInterface, AddRecipeInterface {


    override fun addRecipeToFireBaseDb(recipe:RecipeModel) {
        database?.addRecipe(recipe)
    }
    override fun createFireBaseDb() {
        database= FireBaseDB()
        showRecipeFragment?.setDb(database!!)
        setFragment("Twoje Przepisy")

//        showRecipeFragment?.setAdapter()

    }
    override fun startShowRecipeFragment() {
        toast("MSGGGGGGGGGGGGGGGGGGGG")

    }


    private val manager = supportFragmentManager
    private var facebookLoginBtn:Button? = null
    private var googleLoginBtn:Button? = null
    private var emailLoginBtn:Button? = null
    private var logRegClass: LogRegConnection?=null
    private var dial: LoginByEmailAndPasswordDialogs?=null
    private var addRecipeFragment:AddRecipeFragment?=null
    private var showRecipeFragment:ShowRecipesFragment?=null
    private var database:FireBaseDB?=null

    override fun onStart() {
        super.onStart()
        logRegClass?.ifLogIn()
        progressBar2.visibility=View.GONE
    }

    private fun setToolbar()
    {
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
        visibleNavHeader()
        nav_view.menu.getItem(0).isChecked=true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addRecipeFragment=AddRecipeFragment()
        addRecipeFragment?.setListener(this)
        showRecipeFragment= ShowRecipesFragment()

        setContentView(R.layout.activity_main)
        logRegClass= LogRegConnection(this@MainActivity)
        setToolbar()
    }

    private fun setFragment(id: String) {
        when (id) {

            "Dodaj Przepis" -> {
                manager.beginTransaction()
                        .replace(R.id.content_frame, addRecipeFragment)
                        .addToBackStack("AddRecipeFragment").commit()
            }
            "Twoje Przepisy" -> {
                manager.beginTransaction()
                        .replace(R.id.content_frame, showRecipeFragment)
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

    override fun visibleNavHeader()
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
    override fun inVisibleNavHeader()
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
        setFragment("Twoje Przepisy")
    }

    override fun logOut() {

        Toast.makeText(applicationContext, "Logout succesfull", Toast.LENGTH_LONG).show()
        logRegClass?.logOut()
        visibleNavHeader()
        database=null
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



