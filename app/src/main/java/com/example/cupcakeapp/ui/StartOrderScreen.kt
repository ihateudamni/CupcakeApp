package com.example.cupcakeapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cupcakeapp.ui.theme.CupcakeAppTheme

@Composable
fun StartOrderScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Aquí irán los elementos de la pantalla
    }
}

@Preview
@Composable
fun StartOrderPreview() {
    CupcakeAppTheme {
        StartOrderScreen()
    }
}
