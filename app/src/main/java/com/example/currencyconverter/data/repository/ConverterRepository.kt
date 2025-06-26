package com.example.currencyconverter.data.repository

import com.example.currencyconverter.domain.entity.Account
import com.example.currencyconverter.domain.entity.Currency
import com.example.currencyconverter.domain.entity.Rate
import com.example.currencyconverter.domain.entity.Transaction
import kotlinx.coroutines.flow.Flow

interface ConverterRepository {
    suspend fun getRates(base: Currency, amount: Double): List<Rate>
    fun observeAccounts(): Flow<List<Account>>
    suspend fun getAccounts(): List<Account>
    suspend fun updateAccounts(accounts: List<Account>)
    suspend fun insertTransaction(tx: Transaction)
    suspend fun getTransactions(): List<Transaction>
}
