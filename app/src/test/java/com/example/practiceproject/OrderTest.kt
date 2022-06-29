package com.example.practiceproject

import org.junit.Assert.*

import org.junit.Test

class OrderTest {

    @Test
    fun setUpTaxi() {
        val k = Order()
        val result = k.setUpTaxi("weewq", 34.5, "Sss", 45.9)
        assertEquals(true, result)
    }
}