package com.example.currencyconverter.ui.currencies

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
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
    onSelect: (Currency, Double, Double) -> Unit,
    onHistory: () -> Unit,
    viewModel: CurrenciesViewModel = hiltViewModel()
) {
    var selected by remember { mutableStateOf(Currency.USD) }
    var amount by remember { mutableStateOf(1.0) }

    LaunchedEffect(selected, amount) {
        while (true) {
            viewModel.loadRates(selected, amount)
            delay(1000)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Currencies") },
                actions = {
                    IconButton(onClick = onHistory) {
                        Icon(Icons.Default.History, contentDescription = "History")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            OutlinedTextField(
                value = amount.toString(),
                onValueChange = { amount = it.toDoubleOrNull() ?: 1.0 },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            val rates = viewModel.rates.value
            LazyColumn {
                items(rates) { rate ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { selected = rate.currency; onSelect(rate.currency, amount, rate.value) },
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
                            }
                            Text(text = String.format("%.2f", rate.value))
                        }
                    }
                }

            }
        }
    }
}
