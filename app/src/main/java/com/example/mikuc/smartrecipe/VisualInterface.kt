package com.example.mikuc.smartrecipe

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.mikuc.smartrecipe.Dialogs.LoginByEmailAndPasswordDialogs
import com.example.mikuc.smartrecipe.R.id.drawer_layout
import com.example.mikuc.smartrecipe.R.id.nav_view
import kotlinx.android.synthetic.main.activity_main.*


class VisualInterface(view:View, activity:Activity,actionbar:ActionBar,nav_view:NavigationView)  {

    init {
        val headerLayout =nav_view.getHeaderView(0)
        val emailLoginBtn=headerLayout.findViewById<Button>(R.id.email_login_button)
        val facebookLoginBtn=headerLayout.findViewById<Button>(R.id.fb_login_btn)
        val googleLoginBtn=headerLayout.findViewById<Button>(R.id.google_login_btn)
        val dial= LoginByEmailAndPasswordDialogs(activity)
//        setToolbar(actionbar,nav_view)
    }


//    companion object {
//
//        private fun setToolbar(actionbar:ActionBar,nav_view:NavigationView)
//        {
//
//            actionbar.setDisplayHomeAsUpEnabled(true)
//            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu)
//
//            nav_view.setNavigationItemSelectedListener { menuItem ->
//
//                menuItem.isChecked = true
//                actionbar.title = menuItem.title
//                setFragment(menuItem.title.toString())
//                drawer_layout.closeDrawers()
//                true
//            }
//
//            visibleNavHeader()
//            nav_view.menu.getItem(0).isChecked=true
//            actionbar?.title="Twoje Przepisy"
//            setFragment("Twoje Przepisy")
//
//
//        }
//
//    }

}
