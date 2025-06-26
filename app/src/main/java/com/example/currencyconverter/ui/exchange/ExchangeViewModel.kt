package com.example.currencyconverter.ui.exchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.repository.ConverterRepository
import com.example.currencyconverter.domain.entity.Currency
import com.example.currencyconverter.domain.entity.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val repository: ConverterRepository
) : ViewModel() {

    fun buy(from: Currency, to: Currency, fromAmount: Double, toAmount: Double) {
        viewModelScope.launch {
            repository.insertTransaction(
                Transaction(
                    from = from,
                    to = to,
                    fromAmount = fromAmount,
                    toAmount = toAmount,
                    dateTime = LocalDateTime.now()
                )
            )
            val accounts = repository.getAccounts().toMutableList()
            val updated = accounts.map {
                when (it.currency) {
                    from -> it.copy(amount = it.amount - fromAmount)
                    to -> it.copy(amount = it.amount + toAmount)
                    else -> it
                }
            }.toMutableList()
            if (updated.none { it.currency == to }) {
                updated.add(com.example.currencyconverter.domain.entity.Account(to, toAmount))
            }
            repository.updateAccounts(updated)
        }
    }
}
