package com.example.currencyconverter.ui.exchange

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyconverter.domain.entity.Currency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExchangeScreen(
    from: Currency,
    to: Currency,
    fromAmount: Double,
    toAmount: Double,

    onDone: () -> Unit,
    viewModel: ExchangeViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Exchange") }) }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    val rate = fromAmount / toAmount
                    Text(text = "Buy $toAmount $to", style = MaterialTheme.typography.titleLarge)
                    Text(text = "for %.2f %s".format(fromAmount, from), style = MaterialTheme.typography.bodyLarge)
                    Text(text = "Rate: %.4f".format(rate), style = MaterialTheme.typography.bodyMedium)

                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    viewModel.buy(from, to, fromAmount, toAmount)

                    onDone()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Buy")
            }
        }
    }
}
