package com.example.mikuc.smartrecipe.RecycleView

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mikuc.smartrecipe.DataModels.Ingredient
import com.example.mikuc.smartrecipe.R
import kotlinx.android.synthetic.main.ingredient_list_recycle_view_element.view.*


class IngredientsRecycleViewAdapter(val list: ArrayList<Ingredient>):
        RecyclerView.Adapter<IngredientsRecycleViewAdapter.ViewHolder>() {


    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsRecycleViewAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.ingredient_list_recycle_view_element, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: IngredientsRecycleViewAdapter.ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(item: Ingredient) {
            itemView.ingredient_list_recycle_view_element_name_tv.text=item.name
            itemView.ingredient_list_recycle_view_element_amount_tv.text=item.amount
            itemView.ingredient_list_recycle_view_element_unit_tv.text=item.unit

        }
    }

}