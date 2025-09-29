package com.example.cupcakeapp.ui

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.cupcakeapp.R

//Pantalla Final

@Composable
fun SummaryScreen(
    viewModel: OrderViewModel,
    onCancelButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    orderSummary: String
) {
    val quantity by viewModel.quantity.collectAsState()
    val flavor by viewModel.flavor.collectAsState()
    val date by viewModel.date.collectAsState()
    val context = LocalContext.current

    val orderSummary = """
        Cantidad: $quantity
        Sabor: $flavor
        Fecha: $date
    """.trimIndent()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Información del pedido
        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Resumen del pedido", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
            Text(orderSummary, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
            Text("Subtotal $24.00", fontSize = 18.sp, fontWeight = FontWeight.Bold) // aquí luego puedes calcular el precio real
        }

        // Botones
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, orderSummary)
                    }
                    context.startActivity(
                        Intent.createChooser(intent, "Enviar pedido vía")
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enviar pedido a otra app")
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            OutlinedButton(
                onClick = onCancelButtonClicked,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancelar")
            }
        }
    }
}
