package com.example.practiceproject.activities

import android.app.ActivityOptions
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.practiceproject.Order
import com.example.practiceproject.R
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.Response
import org.json.JSONException
import java.io.IOException
import java.util.*


class PriceChooseActivity : AppCompatActivity() {

    private var cLayout: ConstraintLayout? = null
    private var maxPriceButton: Button? = null
    private var minPriceButton: Button? = null
    private var allVariantsButton: Button? = null
    private var order: Order? = null
    private lateinit var geocoder: Geocoder
    private var arrivalAddress: MutableList<Address>? = null
    private var departureAddress: MutableList<Address>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_price_choose)
        maxPriceButton = findViewById(R.id.maxPriceButton)
        minPriceButton = findViewById(R.id.minPriceButton)
        allVariantsButton = findViewById(R.id.allVariantsButton)
        cLayout = findViewById(R.id.priceChooseLayout)
        order = (intent.getSerializableExtra("order") as Order?)
        geocoder = Geocoder(this, Locale.getDefault())
        arrivalAddress = geocoder.getFromLocationName(order?.toWhere, 2)
        departureAddress = geocoder.getFromLocationName(order?.fromWhere, 2)
    }

    override fun onStart() {
        super.onStart()
        if ((departureAddress as MutableList<Address>?)?.isEmpty() == true) {
            toMainActivity("Departure address is not correct")
        } else if ((arrivalAddress as MutableList<Address>?)?.isEmpty() == true) {
            toPreviousActivity("Arrival address is not correct")
        } else {
            fetchJson(
                arrivalAddress?.get(0)?.latitude!!.toString(),
                arrivalAddress?.get(0)?.longitude!!.toString(),
                departureAddress?.get(0)?.latitude!!.toString(),
                departureAddress?.get(0)?.longitude!!.toString()
            )
        }
    }

    fun fetchJson(vararg params: String?): Boolean {

        if (params.size != 4) {
            toPreviousActivity("Invalid count of params")
            return false
        }
        if ((params.get(0)?.toDouble()!! < 0 || params.get(0)?.toDouble()!! > 90) ||
            (params.get(1)?.toDouble()!! < -180 || params.get(1)?.toDouble()!! > 180) ||
            (params.get(2)?.toDouble()!! < 0 || params.get(2)?.toDouble()!! > 90) ||
            (params.get(3)?.toDouble()!! < -180 || params.get(3)?.toDouble()!! > 180)
        ) {
            toPreviousActivity("Wrong coordinates")
            return false
        }


        var time: Double = -1.0

        try {
            val request = Request.Builder()
                .url(
                    "http://51.250.101.124:8080/api/ride/price?startlat=${params.get(0)}&startlon=${
                        params.get(1)
                    }&endlat=${params.get(2)}&endlon=${params.get(3)}"
                )
                .get()
                .build()

            val requestTime = Request.Builder()
                .url(
                    "http://51.250.101.124:8080/api/ride/time?startlat=${params.get(0)}&startlon=${
                        params.get(1)
                    }&endlat=${params.get(2)}&endlon=${params.get(3)}"
                )
                .get()
                .build()

            val client = OkHttpClient()

            client.newCall(requestTime).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val body = response.body()?.string()
                    val gson = GsonBuilder().create()

                    time = gson.fromJson(body, Double::class.java)
                }

                override fun onFailure(call: Call, e: IOException) {
                    toPreviousActivity("Fail to get time")
                }
            } )


            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {

                    val body = response.body()?.string()
                    val gson = GsonBuilder().create()

                    val info = gson.fromJson(body, Array<taxiClass>::class.java)

                    info.map {
                        order?.setUpTaxi(
                            price = it.price.toDouble(),
                            taxiClass = it.rideClass,
                            driver = "Unknown",
                            time = time
                        )
                    }

                }


                override fun onFailure(call: Call, e: IOException) {
                    toPreviousActivity("Fail to connect")
                }
            })
        } catch (e: IOException) {
            toPreviousActivity("Something goes wrong")
        } catch (e: JSONException) {
            toPreviousActivity("Error while parsing JSON")
        }
        return true
    }

    fun toPreviousActivity(message: String) {

        val activityOptions =
            ActivityOptions.makeSceneTransitionAnimation(this, Pair(cLayout, "mainSlide"))
        val int = Intent(this, ToWhereActivity::class.java).apply {
            putExtra("order", order)
            if (message != "") {
                putExtra("connectionError", message)
            }
        }
        startActivity(int, activityOptions.toBundle())
    }

    fun toMainActivity(message: String) {

        val activityOptions =
            ActivityOptions.makeSceneTransitionAnimation(this, Pair(cLayout, "mainSlide"))
        val int = Intent(this, MainActivity::class.java).apply {
            putExtra("order", order)
            if (message != "") {
                putExtra("connectionError", message)
            }
        }
        startActivity(int, activityOptions.toBundle())
    }

    fun maxPriceAction(view: View) {

        val activityOptions =
            ActivityOptions.makeSceneTransitionAnimation(this, Pair(cLayout, "mainSlide"))
        val intent = Intent(this, MaxPriceActivity::class.java).apply {
            putExtra("order", order)
        }
        startActivity(intent, activityOptions.toBundle())
    }


    fun minPriceAction(view: View) {
        val activityOptions =
            ActivityOptions.makeSceneTransitionAnimation(this, Pair(cLayout, "mainSlide"))
        val intent = Intent(this, MinPriceActivity::class.java).apply {
            putExtra("order", order)
        }
        startActivity(intent, activityOptions.toBundle())
    }

    fun onClickBackActionChoose(view: View) {
        toPreviousActivity("")
    }

    fun allVariantsAction(view: View) {

        val activityOptions =
            ActivityOptions.makeSceneTransitionAnimation(this, Pair(cLayout, "mainSlide"))
        val intent = Intent(this, AllVariantsActivity::class.java).apply {
            putExtra("order", order)
        }
        startActivity(intent, activityOptions.toBundle())
    }
}

class taxiClass(val price: Int, val rideClass: String) {

}
