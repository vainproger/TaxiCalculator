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

class MaxPriceActivity : AppCompatActivity() {
    private var cLayout: ConstraintLayout? = null
    private var backButton: Button? = null
    private var orderButton: Button? = null
    private var driverInput: TextView? = null
    private var timeInput: TextView? = null
    private var priceInput: TextView? = null
    private var taxiClassInput: TextView? = null
    private var order: Order? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.max_price_activity)
        cLayout = findViewById(R.id.maxPriceLayout)
        backButton = findViewById(R.id.backButton)
        orderButton = findViewById(R.id.buttonOrder)
        driverInput = findViewById(R.id.driverInput)
        timeInput = findViewById(R.id.timeInput)
        priceInput = findViewById(R.id.priceInput)
        taxiClassInput = findViewById(R.id.taxiClassInput)
        order = (intent.getSerializableExtra("order") as Order?)
        showInfo()
    }

    fun showInfo() {
        driverInput?.text = order?.getMaxPriceTaxi()?.driver
        priceInput?.text = order?.getMaxPriceTaxi()?.price.toString()
        taxiClassInput?.text = order?.getMaxPriceTaxi()?.taxiClass
        timeInput?.text = order?.getMaxPriceTaxi()?.time
    }

    fun onClickBackAction(view: View) {
        order?.clearTaxi()
        val activityOptions =
            ActivityOptions.makeSceneTransitionAnimation(this, Pair(cLayout, "mainSlide"))
        val intent = Intent(this, PriceChooseActivity::class.java).apply {
            putExtra("order", order)
        }
        startActivity(intent, activityOptions.toBundle())
    }

    fun onClickOrderMax(view: View) {
        askForLinkDialog()
        askForFileSaveDialog()
    }

    fun askForLinkDialog() {
        val builder = AlertDialog.Builder(this, R.style.MyDialogTheme)
        builder.setTitle("Перейти по ссылке: ")
        builder.setMessage("*** your link ***")
        builder.setNegativeButton("Нет") { dialogInterface, i ->

        }
        builder.setPositiveButton("Перейти") { dialog, i ->

        }
        builder.show()
    }

    fun askForFileSaveDialog() {
        val builder = AlertDialog.Builder(this, R.style.MyDialogTheme)
        builder.setTitle("Сохранить инфо в файл?")
        builder.setMessage("*** В файл запишется имя водителя, цена и время поездки ***")
        builder.setNegativeButton("Нет") { dialogInterface, i ->

        }
        builder.setPositiveButton("Да") { dialog, i ->

            fillFile("savedTaxiInfo.txt")
            Toast.makeText(this, "Успешно сохранено", Toast.LENGTH_SHORT).show()
        }
        builder.show()
    }

    private fun fillFile(fileName: String) {
        val path = this.getExternalFilesDir(null)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        } else {
            val folder = File(path, "TaxiCalculator")
            if (!folder.exists()) {
                folder.mkdirs()
            }
            val file = File(folder, fileName)
            if (!file.exists()) {
                file.createNewFile()
            }
            file.appendText("\n".repeat(3))
            file.appendText(FileFiller.separator)
            file.appendText("\n".repeat(3))
            file.appendText("${FileFiller.taxiDriver}:  ${order?.getMaxPriceTaxi()?.driver} \n")
            file.appendText("${FileFiller.taxiPrice}:  ${order?.getMaxPriceTaxi()?.price} \n")
            file.appendText("${FileFiller.classTaxi}:  ${order?.getMaxPriceTaxi()?.taxiClass} \n")
            file.appendText("${FileFiller.tripTime}:  ${order?.getMaxPriceTaxi()?.time} \n")
        }
    }

}