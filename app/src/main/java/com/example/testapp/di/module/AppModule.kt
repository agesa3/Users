package com.example.testapp.di.module

import android.content.Context
import android.os.Build
import com.example.testapp.BuildConfig
import com.example.testapp.data.api.ApiHelper
import com.example.testapp.data.api.ApiHelperImpl
import com.example.testapp.data.api.ApiService
import com.example.testapp.utils.Constants
import com.example.testapp.utils.NetworkHelper
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val appModule = module {
    single { provideApiService(get()) }
    single { provideNetworkHelper(androidContext()) }
    single { provideRetrofit(get(), BASE_URL = Constants.BASE_URL) }
    single { provideOkHttpClient() }
    single<ApiHelper> {
        return@single ApiHelperImpl(get())
    }
}


private fun provideNetworkHelper(context: Context) = NetworkHelper(context)

private fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

} else OkHttpClient.Builder().build()


private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    BASE_URL: String
): Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()

private fun provideApiService(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)

private fun provideApiHelper(apiHelper: ApiHelper): ApiHelper = apiHelper
