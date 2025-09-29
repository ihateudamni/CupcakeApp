package com.example.cupcakeapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cupcakeapp.data.DataSource
import com.example.cupcakeapp.ui.SelectOptionScreen
import com.example.cupcakeapp.ui.StartOrderScreen
import com.example.cupcakeapp.ui.SummaryScreen
import com.example.cupcakeapp.ui.OrderViewModel
import com.example.cupcakeapp.ui.PickupScreen   // 👈 Asegúrate de tener este import
import com.example.cupcakeapp.ui.theme.CupcakeAppTheme

//Pantalla Inicial

enum class CupcakeScreen(val title: String) {
    Start(title = "Cupcake App"),
    Flavor(title = "Choose Flavor"),
    Pickup(title = "Choose Pickup Date"),
    Summary(title = "Order Summary")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CupcakeAppBar(
    currentScreen: CupcakeScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = currentScreen.title) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}

@Composable
fun CupcakeApp() {
    val navController: NavHostController = rememberNavController()
    val viewModel: OrderViewModel = viewModel()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CupcakeScreen.valueOf(
        backStackEntry?.destination?.route ?: CupcakeScreen.Start.name
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CupcakeAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = CupcakeScreen.Start.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Pantalla de inicio
            composable(route = CupcakeScreen.Start.name) {
                StartOrderScreen(
                    quantityOptions = DataSource.quantityOptions,
                    onNextButtonClicked = {
                        viewModel.setQuantity(it)
                        navController.navigate(CupcakeScreen.Flavor.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }

            // Pantalla de sabores
            composable(route = CupcakeScreen.Flavor.name) {
                val context = LocalContext.current
                SelectOptionScreen(
                    options = DataSource.flavors.map { id -> context.resources.getString(id) },
                    onSelectionChanged = {
                        viewModel.setFlavor(it)
                        navController.navigate(CupcakeScreen.Pickup.name)
                    }
                )
            }

            // Pantalla de recogida
            composable(route = CupcakeScreen.Pickup.name) {
                PickupScreen(
                    options = viewModel.pickupOptions,
                    onSelectionChanged = {
                        viewModel.setDate(it)
                        navController.navigate(CupcakeScreen.Summary.name)
                    }
                )
            }

            // Pantalla resumen
            composable(route = CupcakeScreen.Summary.name) {
                SummaryScreen(
                    orderSummary = viewModel.getSummary(),
                    onCancelButtonClicked = {
                        viewModel.resetOrder()
                        navController.popBackStack(CupcakeScreen.Start.name, inclusive = false)
                    },
                    viewModel = viewModel
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun CupcakePreview() {
    CupcakeAppTheme {
        CupcakeApp()
    }
}
