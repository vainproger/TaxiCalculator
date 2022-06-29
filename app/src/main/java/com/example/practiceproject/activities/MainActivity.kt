package com.example.practiceproject.activities

import android.app.ActivityOptions
import android.content.Intent
//import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.Pair
import com.example.practiceproject.Order
import com.example.practiceproject.R


class MainActivity : AppCompatActivity() {

    private var fromWhere: TextView? = null
    private var cLayout: ConstraintLayout? = null
    private var fromWhereButton: Button? = null
    private val order = Order()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cLayout = findViewById(R.id.cLayout)
        fromWhere = findViewById(R.id.fromWhereInput)
        fromWhereButton = findViewById(R.id.backButton)
        val potentialError: String? = intent.getStringExtra("connectionError")
        if (potentialError != null) {
            Toast.makeText(this, potentialError, Toast.LENGTH_SHORT).show()
        }
    }


    fun onClickAction(view: View) {

        if (fromWhere?.text.toString() == "") {
            Toast.makeText(this, "Empty field!", Toast.LENGTH_SHORT).show()
        } else {
            order.fromWhere = fromWhere?.text.toString()

            val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this, Pair(cLayout, "mainSlide"))
            val intent = Intent(this, ToWhereActivity::class.java).apply {
                putExtra("order", order)
            }
            startActivity(intent, activityOptions.toBundle())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
