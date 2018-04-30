package com.example.mikuc.smartrecipe


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.FirebaseDatabase

class AssistantFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_assistant, container, false)
    }


    override fun onStart() {
        super.onStart()
        val data=FirebaseDatabase.getInstance().getReference("Recipes")?.child("name")?.equalTo("Grd")

        Log.d("Tag", data.toString())
    }

}// Required empty public constructor
