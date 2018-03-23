package com.example.mikuc.smartrecipe
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_add_recipe.*

interface MyCallback {
    fun Logout()

}
class AddRecipeFragment : Fragment() {

    private var callback: MyCallback?=null

    companion object {
        fun newInstance(): AddRecipeFragment {
            return AddRecipeFragment()
        }
    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_add_recipe, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is MyCallback) {
            callback = context
        }
     }

    override fun onStart() {
        super.onStart()

        button1.setOnClickListener {
            callback?.Logout()
        }
    }

}

