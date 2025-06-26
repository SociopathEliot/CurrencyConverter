package com.example.currencyconverter.ui.exchange

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyconverter.domain.entity.Currency

@Composable
fun ExchangeScreen(
    from: Currency,
    to: Currency,
    amount: Double,
    rate: Double,
    onDone: () -> Unit,
    viewModel: ExchangeViewModel = hiltViewModel()
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Buy $amount $to for %.2f".format(amount * rate))
        Text(text = "Rate: $rate")
        Button(onClick = {
            viewModel.buy(from, to, amount * rate, amount)
            onDone()
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Buy")
        }
    }
}
