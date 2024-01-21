package com.onedive.passwordstore.utils

import androidx.annotation.Keep
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Keep
object RetrofitInstance {

    private var retrofit : Retrofit? = null


    fun<T> getInstanceService(service: Class<T>) : T {
        if (retrofit == null){

            val builder = OkHttpClient.Builder()
            builder.addInterceptor { chain ->
                val request =
                    chain.request().newBuilder()
                        .addHeader("secret-key", Const.getApiSecretKey()).build()
                chain.proceed(request)
            }

            retrofit = Retrofit.Builder()
                .baseUrl(Const.UPDATE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build()

        }
        return retrofit!!.create(service)
    }

}