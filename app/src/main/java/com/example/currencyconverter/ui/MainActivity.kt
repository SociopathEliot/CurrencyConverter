package com.example.currencyconverter.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.currencyconverter.domain.entity.Currency
import com.example.currencyconverter.ui.currencies.CurrenciesScreen
import com.example.currencyconverter.ui.exchange.ExchangeScreen
import com.example.currencyconverter.ui.transactions.TransactionsScreen
import com.example.currencyconverter.ui.theme.CurrencyConverterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNav()
                }
            }
        }
    }
}

@Composable
fun AppNav() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "currencies") {
        composable("currencies") {
            CurrenciesScreen(
                onSelect = { from, to, fromAmount, toAmount ->
                    navController.navigate("exchange/${from.name}/${to.name}/$fromAmount/$toAmount")
                },
                onHistory = { navController.navigate("transactions") }
            )
        }
        composable(
            "exchange/{from}/{to}/{fromAmount}/{toAmount}",
            arguments = listOf(
                navArgument("from") { type = NavType.StringType },
                navArgument("to") { type = NavType.StringType },
                navArgument("fromAmount") { type = NavType.StringType },
                navArgument("toAmount") { type = NavType.StringType }
            )
        ) { backStack ->
            val from = Currency.valueOf(backStack.arguments?.getString("from")!!)
            val to = Currency.valueOf(backStack.arguments?.getString("to")!!)
            val fromAmount = backStack.arguments?.getString("fromAmount")!!.toDouble()
            val toAmount = backStack.arguments?.getString("toAmount")!!.toDouble()
            ExchangeScreen(
                from = from,
                to = to,
                fromAmount = fromAmount,
                toAmount = toAmount,
                onDone = { navController.popBackStack() }
            )
        }
        composable("transactions") {
            TransactionsScreen(onBack = { navController.popBackStack() })
        }
    }
}
