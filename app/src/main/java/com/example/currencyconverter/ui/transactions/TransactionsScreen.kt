package com.example.currencyconverter.ui.transactions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import com.example.currencyconverter.domain.entity.flagEmoji
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsScreen(onBack: () -> Unit, viewModel: TransactionsViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transactions") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        val formatter = remember { DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT) }
        LazyColumn(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            items(viewModel.transactions.value) { tx ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    ListItem(
                        leadingContent = {
                            Text(
                                text = tx.from.flagEmoji(),
                                style = MaterialTheme.typography.headlineMedium
                            )
                        },
                        headlineText = {
                            Text(text = "${tx.fromAmount} ${tx.from} → ${tx.toAmount} ${tx.to}")
                        },
                        supportingText = {
                            Text(text = tx.dateTime.format(formatter))
                        },
                        trailingContent = {
                            Text(
                                text = tx.to.flagEmoji(),
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                    )
                }
            }
        }
    }
}
