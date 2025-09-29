package com.example.cupcakeapp.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

//Resumen

enum class CupcakeScreen {
    Start,
    Flavor,
    Pickup,
    Summary
}

class OrderViewModel : ViewModel() {
private val _currentScreen = MutableStateFlow(CupcakeScreen.Start)
    val currentScreen: StateFlow<CupcakeScreen> = _currentScreen

    private val _quantity = MutableStateFlow(0)
    val quantity: StateFlow<Int> = _quantity

    private val _flavor = MutableStateFlow("")
    val flavor: StateFlow<String> = _flavor

    private val _date = MutableStateFlow("")
    val date: StateFlow<String> = _date

    private val _price = MutableStateFlow(0.0)
    val price: StateFlow<Double> = _price

    val pickupOptions: List<String> = generarOpcionesDeRecogida()

    fun setQuantity(number: Int) {
        _quantity.value = number
        calculatePrice()
    }

    fun setFlavor(selected: String) {
        _flavor.value = selected
    }

    fun setDate(pickupDate: String) {
        _date.value = pickupDate
    }

    fun setScreen(screen: CupcakeScreen) {
        _currentScreen.value = screen
    }

    fun resetOrder() {
        _quantity.value = 0
        _flavor.value = ""
        _date.value = ""
        _price.value = 0.0
        _currentScreen.value = CupcakeScreen.Start
    }

    private fun calculatePrice() {
        _price.value = _quantity.value * 2.0
    }

    private fun generarOpcionesDeRecogida(): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("EEE MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()

        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }

    fun getSummary(): String {
        return "QUANTITY: ${_quantity.value} cupcakes\n" +
                "FLAVOR: ${_flavor.value}\n" +
                "PICKUP DATE: ${_date.value}\n" +
                "Subtotal: $${_price.value}"
    }
}
