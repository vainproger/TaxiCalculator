package com.example.practiceproject

import java.io.Serializable
import java.sql.Time

class TaxiInfo(var driver: String = "Unknown driver",
               var price: Double = 0.0,
               var taxiClass: String = "Unknown class",
               var time: String = "0 мин") : Serializable {

}
