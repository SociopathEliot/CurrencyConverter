package com.example.currencyconverter.ui.currencies

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyconverter.domain.entity.Currency
import com.example.currencyconverter.domain.entity.displayName
import com.example.currencyconverter.domain.entity.flagEmoji
import kotlinx.coroutines.delay

@Composable
fun CurrenciesScreen(
    onSelect: (from: Currency, to: Currency, fromAmount: Double, toAmount: Double) -> Unit,
    onHistory: () -> Unit,
    viewModel: CurrenciesViewModel = hiltViewModel()
) {
    var selected by remember { mutableStateOf(Currency.RUB) }
    var amount by remember { mutableStateOf(1.0) }
    var inputMode by remember { mutableStateOf(false) }

    LaunchedEffect(selected, amount, inputMode) {
        while (true) {
            val baseAmount = if (inputMode) amount else 1.0
            viewModel.loadRates(selected, baseAmount)

            delay(1000)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Currencies") },
                actions = {
                    IconButton(onClick = onHistory) {
                        Icon(Icons.Default.AddCircle, contentDescription = "History")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            if (inputMode) {
                OutlinedTextField(
                    value = amount.toString(),
                    onValueChange = { amount = it.toDoubleOrNull() ?: 1.0 },
                    trailingIcon = {
                        IconButton(onClick = { amount = 1.0; inputMode = false }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear")
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            val accounts = viewModel.accounts.value
            val rates = viewModel.rates.value
            val list = if (inputMode) {
                rates.filter { r ->
                    val acc = accounts.firstOrNull { it.currency == r.currency }
                    acc != null && acc.amount >= r.value
                }
            } else rates
            LazyColumn {
                items(list) { rate ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                if (inputMode) {
                                    onSelect(rate.currency, selected, rate.value, amount)
                                } else if (rate.currency == selected) {
                                    inputMode = true
                                } else {
                                    selected = rate.currency
                                    amount = 1.0
                                }
                            },

                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = rate.currency.flagEmoji(), style = MaterialTheme.typography.headlineMedium)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = rate.currency.displayName(), style = MaterialTheme.typography.titleMedium)
                                Text(text = rate.currency.name, style = MaterialTheme.typography.bodySmall)
                                val bal = accounts.firstOrNull { it.currency == rate.currency }?.amount
                                if (bal != null) {
                                    Text(text = "Balance: %.2f".format(bal), style = MaterialTheme.typography.bodySmall)
                                }

                            }
                            Text(text = String.format("%.2f", rate.value))
                        }
                    }
                }
            }
        }
    }
}
