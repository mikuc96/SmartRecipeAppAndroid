package com.example.mikuc.smartrecipe


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mikuc.smartrecipe.DataBaseControl.FireBaseDB
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {




    private var profileInterface: profileFragmentInterface?=null
    private var firstName:String?=null
    private var lastName:String?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {



        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()

        firstName=FireBaseDB.firstName
        lastName=FireBaseDB.lastName
        fragment_profile_name.text= firstName + " "+ lastName

        val amountOfRecipes=FireBaseDB.recipesList?.size
        fragment_profile_amount_of_recipes.text="Ilość przepisów: "+ amountOfRecipes.toString()
        fragment_profile_logout_btn.setOnClickListener {


            profileInterface?.logOut()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is profileFragmentInterface )
        {
            profileInterface=context
        }
    }

    interface profileFragmentInterface{
        fun logOut()
    }

}
