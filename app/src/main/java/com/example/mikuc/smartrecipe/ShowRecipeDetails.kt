package com.example.mikuc.smartrecipe
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.LinearLayout
import com.example.mikuc.smartrecipe.DataBaseControl.FireBaseDB
import com.example.mikuc.smartrecipe.RecycleView.IngredientsRecycleViewAdapter
import kotlinx.android.synthetic.main.activity_show_recipe_details.*

class ShowRecipeDetails : AppCompatActivity() {

    private var itemNumber:Int?=null


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_recipe_details)


        val toolbar = findViewById<Toolbar>(R.id.toolbar2)
        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setDisplayShowHomeEnabled(true)

        itemNumber=intent.getIntExtra("TAG",0)
        val recipe= FireBaseDB.recipesList?.get(itemNumber!!)
        actionbar?.title=recipe?.name

        activity_show_recipe_details_description_tv.text=recipe?.description
        activity_show_recipe_details_hardness_tv.text=recipe?.hardness
        activity_show_recipe_details_time_tv.text=recipe?.time
        activity_show_recipe_details_people_tv.text=recipe?.people

        activity_show_recipe_details_recycle_view.layoutManager =
                LinearLayoutManager(applicationContext, LinearLayout.VERTICAL, false)

        val ingredientList=recipe?.ingredients
        val adapter = IngredientsRecycleViewAdapter(ingredientList!!)
        activity_show_recipe_details_recycle_view.adapter=adapter
    }
}
