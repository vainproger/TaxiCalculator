package com.example.practiceproject.activities

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Pair
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceproject.Order
import com.example.practiceproject.R
import com.example.practiceproject.adapters.TaxiWindowAdapter

class AllVariantsActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var order : Order? = null
    private var cLayout: ConstraintLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_variants)
        cLayout = findViewById(R.id.allVariantsPriceLayout)
        recyclerView = findViewById(R.id.allVariantsList)
        order = (intent.getSerializableExtra("order") as Order?)
        createList()
    }

    fun onClickBackActionArray(view: View) {
        order?.clearTaxi()
        val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this, Pair(cLayout, "mainSlide"))
        val intent = Intent(this, PriceChooseActivity::class.java).apply {
            putExtra("order", order)
        }
        startActivity(intent, activityOptions.toBundle())
    }

    private fun createList() {
        val linearManager = LinearLayoutManager(this)
        linearManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView?.layoutManager = linearManager
        recyclerView?.adapter = TaxiWindowAdapter(order?.getAllTaxi())
    }

}