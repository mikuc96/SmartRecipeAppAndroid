package com.example.mikuc.smartrecipe


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {




    interface profileFragmentInterface{
        fun logOut()
    }

     var profileInterface: profileFragmentInterface?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onStart() {
        super.onStart()


        logout_btn.setOnClickListener {


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

}
