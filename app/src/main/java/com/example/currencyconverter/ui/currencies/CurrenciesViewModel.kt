package com.example.currencyconverter.ui.currencies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.repository.ConverterRepository
import com.example.currencyconverter.domain.entity.Account
import com.example.currencyconverter.domain.entity.Currency
import com.example.currencyconverter.domain.entity.Rate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrenciesViewModel @Inject constructor(
    private val repository: ConverterRepository
) : ViewModel() {

    private val _rates = MutableStateFlow<List<Rate>>(emptyList())
    val rates: StateFlow<List<Rate>> = _rates.asStateFlow()

    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    val accounts: StateFlow<List<Account>> = _accounts.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observeAccounts().collect { _accounts.value = it }
        }
    }

    fun loadRates(base: Currency, amount: Double) {
        viewModelScope.launch {
            _rates.value = repository.getRates(base, amount)
        }
    }
}
