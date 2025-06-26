package com.example.currencyconverter.ui.transactions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TransactionsScreen(onBack: () -> Unit, viewModel: TransactionsViewModel = hiltViewModel()) {
    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = onBack) { Text("Back") }
        viewModel.transactions.value.forEach { tx ->
            Text(
                text = "${tx.from.name}:${tx.fromAmount} -> ${tx.to.name}:${tx.toAmount} at ${tx.dateTime}",
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            )
        }
    }
}
