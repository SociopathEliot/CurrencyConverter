package com.example.currencyconverter.di

import com.example.currencyconverter.data.dataSource.remote.RatesService
import com.example.currencyconverter.data.dataSource.remote.RemoteRatesServiceImpl
import com.example.currencyconverter.data.dataSource.room.ConverterDatabase
import com.example.currencyconverter.data.repository.ConverterRepository
import com.example.currencyconverter.data.repository.ConverterRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRatesService(): RatesService = RemoteRatesServiceImpl()

    @Provides
    @Singleton
    fun provideConverterRepository(
        db: ConverterDatabase,
        ratesService: RatesService
    ): ConverterRepository = ConverterRepositoryImpl(db, ratesService)
}
