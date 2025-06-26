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
                onSelect = { currency, amount, rate ->
                    navController.navigate("exchange/${currency.name}/$amount/$rate")
                },
                onHistory = { navController.navigate("transactions") }
            )
        }
        composable(
            "exchange/{currency}/{amount}/{rate}",
            arguments = listOf(
                navArgument("currency") { type = NavType.StringType },
                navArgument("amount") { type = NavType.StringType },
                navArgument("rate") { type = NavType.StringType }
            )
        ) { backStack ->
            val currency = Currency.valueOf(backStack.arguments?.getString("currency")!!)
            val amount = backStack.arguments?.getString("amount")!!.toDouble()
            val rate = backStack.arguments?.getString("rate")!!.toDouble()
            ExchangeScreen(
                from = Currency.USD,
                to = currency,
                amount = amount,
                rate = rate,
                onDone = { navController.popBackStack() }
            )
        }
        composable("transactions") {
            TransactionsScreen(onBack = { navController.popBackStack() })
        }
    }
}
