package com.example.practiceproject

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable
import java.lang.Exception
import java.sql.Time

class Order : Serializable {
    var fromWhere: String = "Empty place"
        set(value) {
            if (value == "") {
                throw Exception("Empty place")
            } else {
                field = value
            }
        }
    var toWhere: String = "Empty place"
        set(value) {
            if (value == "") {
                throw Exception("Empty place")
            } else {
                field = value
            }
        }
    private var taxiList: ArrayList<TaxiInfo> = ArrayList()

    fun setUpTaxi(driver: String, price: Double, taxiClass: String, time: Double) : Boolean {
        return taxiList.add(TaxiInfo(driver, price, taxiClass, "$time мин"))
    }

    fun getMaxPriceTaxi() : TaxiInfo? {
        var max = taxiList[0].price
        var result: TaxiInfo? = taxiList[0]
        for (item in taxiList) {
            if (item.price > max) {
                max = item.price
                result = item
            }
        }
        return result
    }
    fun getMinPriceTaxi() : TaxiInfo? {
        var min = taxiList[0].price
        var result: TaxiInfo? = taxiList[0]
        for (item in taxiList) {
            if (item.price < min) {
                min = item.price
                result = item
            }
        }
        return result
    }

    fun getAllTaxi() : ArrayList<TaxiInfo> {
        return taxiList
    }

    fun clearTaxi() : Boolean {
        taxiList.clear()
        return taxiList.isEmpty()
    }
}