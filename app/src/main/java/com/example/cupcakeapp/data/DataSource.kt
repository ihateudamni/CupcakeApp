package com.example.cupcakeapp.data

import android.content.Context
import com.example.cupcakeapp.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DataSource {

    val flavors = listOf(
        R.string.vanilla,
        R.string.chocolate,
        R.string.red_velvet,
        R.string.salted_caramel,
        R.string.coffee
    )

    val quantityOptions = listOf(
        Pair(R.string.one_cupcake, 1),
        Pair(R.string.six_cupcakes, 6),
        Pair(R.string.twelve_cupcakes, 12)
    )

    // 👉 Nueva función para generar las fechas disponibles de recogida
    fun pickupOptions(context: Context): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()

        repeat(4) {   // genera hoy + los próximos 3 días
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }
}
