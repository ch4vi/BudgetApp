package com.ch4vi.data.di

import com.ch4vi.data.BuildConfig.API_BASE_URL
import com.ch4vi.data.api.ApiService
import com.ch4vi.data.database.AppDatabase
import com.ch4vi.data.datasource.BudgetDatabaseDataSource
import com.ch4vi.data.datasource.BudgetDatabaseDataSourceImp
import com.ch4vi.data.datasource.CategoryApiDataSource
import com.ch4vi.data.datasource.CategoryApiDataSourceImp
import com.ch4vi.data.datasource.LocationApiDataSource
import com.ch4vi.data.datasource.LocationApiDataSourceImp
import com.ch4vi.data.datasource.LocationDatabaseDataSource
import com.ch4vi.data.datasource.LocationDatabaseDataSourceImp
import com.ch4vi.data.repository.BudgetRepositoryImp
import com.ch4vi.data.repository.CategoryRepositoryImp
import com.ch4vi.data.repository.LocationRepositoryImp
import com.ch4vi.domain.repository.BudgetRepository
import com.ch4vi.domain.repository.CategoryRepository
import com.ch4vi.domain.repository.LocationRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {
    single {
        provideRetrofit(API_BASE_URL).create(ApiService::class.java)
    } bind ApiService::class
    single { AppDatabase.create(androidApplication()) } bind AppDatabase::class

    single { BudgetDatabaseDataSourceImp(db = get()) } bind BudgetDatabaseDataSource::class
    single { LocationDatabaseDataSourceImp(db = get()) } bind LocationDatabaseDataSource::class

    single { CategoryApiDataSourceImp(api = get()) } bind CategoryApiDataSource::class
    single { LocationApiDataSourceImp(api = get()) } bind LocationApiDataSource::class

    single { BudgetRepositoryImp(budgetDatabase = get()) } bind BudgetRepository::class
    single { CategoryRepositoryImp(categoryApi = get()) } bind CategoryRepository::class
    single {
        LocationRepositoryImp(
            locationApi = get(),
            locationDatabase = get()
        )
    } bind LocationRepository::class

}

fun provideRetrofit(baseUrl: String): Retrofit {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS

    val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    return Retrofit.Builder()
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()
}