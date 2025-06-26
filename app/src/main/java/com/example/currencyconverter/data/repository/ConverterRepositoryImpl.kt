package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.dataSource.remote.RatesService
import com.example.currencyconverter.data.dataSource.remote.dto.RateDto
import com.example.currencyconverter.data.dataSource.room.ConverterDatabase
import com.example.currencyconverter.data.dataSource.room.account.dbo.AccountDbo
import com.example.currencyconverter.data.dataSource.room.transaction.dbo.TransactionDbo
import com.example.currencyconverter.domain.entity.Account
import com.example.currencyconverter.domain.entity.Currency
import com.example.currencyconverter.domain.entity.Rate
import com.example.currencyconverter.domain.entity.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ConverterRepositoryImpl(
    private val db: ConverterDatabase,
    private val remote: RatesService
) : ConverterRepository {

    override suspend fun getRates(base: Currency, amount: Double): List<Rate> {
        return remote.getRates(base.name, amount).map { it.toDomain() }
    }

    override fun observeAccounts(): Flow<List<Account>> =
        db.accountDao().getAllAsFlow().map { list -> list.map { it.toDomain() } }

    override suspend fun getAccounts(): List<Account> =
        db.accountDao().getAll().map { it.toDomain() }

    override suspend fun updateAccounts(accounts: List<Account>) {
        db.accountDao().insertAll(*accounts.map { it.toDbo() }.toTypedArray())
    }

    override suspend fun insertTransaction(tx: Transaction) {
        db.transactionDao().insertAll(tx.toDbo())
    }

    override suspend fun getTransactions(): List<Transaction> =
        db.transactionDao().getAll().map { it.toDomain() }

    private fun RateDto.toDomain() = Rate(Currency.valueOf(currency), value)
    private fun AccountDbo.toDomain() = Account(Currency.valueOf(code), amount)
    private fun Account.toDbo() = AccountDbo(currency.name, amount)
    private fun TransactionDbo.toDomain() = Transaction(
        id,
        Currency.valueOf(from),
        Currency.valueOf(to),
        fromAmount,
        toAmount,
        dateTime
    )
    private fun Transaction.toDbo() = TransactionDbo(
        id,
        from.name,
        to.name,
        fromAmount,
        toAmount,
        dateTime
    )
}
