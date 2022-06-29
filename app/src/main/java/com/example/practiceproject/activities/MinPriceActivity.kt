package com.example.practiceproject.activities

import android.Manifest
import android.app.ActivityOptions
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.practiceproject.FileFiller
import com.example.practiceproject.Order
import com.example.practiceproject.R
import java.io.File

class MinPriceActivity : AppCompatActivity() {
    private var cLayout: ConstraintLayout? = null
    private var backButtonMin: Button? = null
    private var orderButtonMin: Button? = null
    private var driverInputMin: TextView? = null
    private var timeInputMin: TextView? = null
    private var priceInputMin: TextView? = null
    private var taxiClassInputMin: TextView? = null
    private var order : Order? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_min_price)
        cLayout = findViewById(R.id.minPriceLayout)
        backButtonMin = findViewById(R.id.backButtonMin)
        orderButtonMin = findViewById(R.id.buttonOrderMin)
        driverInputMin = findViewById(R.id.driverInputMin)
        timeInputMin = findViewById(R.id.timeInputMin)
        priceInputMin = findViewById(R.id.priceInputMin)
        taxiClassInputMin = findViewById(R.id.taxiClassInputMin)
        order = (intent.getSerializableExtra("order") as Order?)
        showInfo()
    }

    fun showInfo() {
        driverInputMin?.text = order?.getMinPriceTaxi()?.driver
        priceInputMin?.text = order?.getMinPriceTaxi()?.price.toString()
        taxiClassInputMin?.text = order?.getMinPriceTaxi()?.taxiClass
        timeInputMin?.text = order?.getMinPriceTaxi()?.time
    }

    fun onClickBackActionMin(view: View) {
        order?.clearTaxi()
        val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this, Pair(cLayout, "mainSlide"))
        val intent = Intent(this, PriceChooseActivity::class.java).apply {
            putExtra("order", order)
        }
        startActivity(intent, activityOptions.toBundle())
    }

    fun onClickOrderMin(view: View) {
        askForLinkDialog()
        askForFileSaveDialog()

    }

    fun askForLinkDialog() {
        val builder = AlertDialog.Builder(this, R.style.MyDialogTheme)
        builder.setTitle("Перейти по ссылке: ")
        builder.setMessage("*** your link ***")
        builder.setNegativeButton("Нет"){ dialogInterface, i ->

        }
        builder.setPositiveButton("Перейти") {dialog, i ->
            //тут переход по ссылке юзая класс NetClient
        }
        builder.show()
    }
    fun askForFileSaveDialog() {
        val builder = AlertDialog.Builder(this, R.style.MyDialogTheme)
        builder.setTitle("Сохранить инфо в файл?")
        builder.setMessage("*** В файл запишется имя водителя, цена и время поездки ***")
        builder.setNegativeButton("Нет"){ dialogInterface, i ->

        }
        builder.setPositiveButton("Да") {dialog, i ->
            //тут переход по ссылке юзая класс NetClient
            fillFile("savedTaxiInfo.txt")
            Toast.makeText(this, "Успешно сохранено", Toast.LENGTH_SHORT).show()
        }
        builder.show()
    }

    fun fillFile(fileName: String) {
        val path = this.getExternalFilesDir(null)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            val folder = File(path, "TaxiCalculator")
            if (!folder.exists()) {
                folder.mkdirs()
            }
            val file = File(folder, fileName)
            if (!file.exists()){
                file.createNewFile()
            }
            file.appendText("\n".repeat(3))
            file.appendText(FileFiller.separator)
            file.appendText("\n".repeat(3))
            file.appendText("${FileFiller.taxiDriver}:  ${order?.getMinPriceTaxi()?.driver} \n")
            file.appendText("${FileFiller.taxiPrice}:  ${order?.getMinPriceTaxi()?.price} \n")
            file.appendText("${FileFiller.classTaxi}:  ${order?.getMinPriceTaxi()?.taxiClass} \n")
            file.appendText("${FileFiller.tripTime}:  ${order?.getMinPriceTaxi()?.time} \n")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}