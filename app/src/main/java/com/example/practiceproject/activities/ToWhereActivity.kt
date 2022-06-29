package com.example.practiceproject.activities

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.practiceproject.Order
import com.example.practiceproject.R

class ToWhereActivity : AppCompatActivity() {

    private var toWhereButton: Button? = null
    private var cLayout: ConstraintLayout? = null
    private var toWhere: TextView? = null
    private var order : Order? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_where)
        cLayout = findViewById(R.id.toWhereLayout)
        toWhereButton = findViewById(R.id.button3)
        toWhere = findViewById(R.id.toWhereInput)
        order = intent.getSerializableExtra("order") as Order?
        val potentialError: String? = intent.getStringExtra("connectionError")
        if (potentialError != null) {
            Toast.makeText(this, potentialError, Toast.LENGTH_SHORT).show()
        }
    }

    fun sendToWhereInfo(view: View) {
        if (toWhere?.text.toString() == "") {
            Toast.makeText(this, "Empty field!", Toast.LENGTH_SHORT).show()
        } else {
            val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this, Pair(cLayout, "mainSlide"))
            (intent.getSerializableExtra("order") as Order?)?.toWhere = toWhere?.text.toString()
            val intent = Intent(this, PriceChooseActivity::class.java).apply {
                putExtra("order", order)

            }
            startActivity(intent, activityOptions.toBundle())
        }
    }
}