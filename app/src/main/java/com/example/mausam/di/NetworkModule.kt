package com.example.mausam.di

import com.example.mausam.data.network.WeatherApi
import com.example.mausam.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/*The classes which have to make HTTP requests can use Network Module rather than creating retrofit instances for themselves.retrofit creates interfaces that deal with Restful
 Webservices(basically they expose data won which you can perform using operations like retrieve,update using HTTP requests).Jis bhi class ko Http request karni h wo Network module use
 karke FoodRecipesApi banayega jiske liye it woruld need the retrofit instance too
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun providesRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory).build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }/*the method creates and returns an instance of the FoodRecipesApi interface by calling the create() method on the Retrofit instance and passing the
    FoodRecipesApi interface as a parameter. The create() method creates an implementation of the FoodRecipesApi interface that is backed by a Retrofit instance, and it can be used to
    call the methods defined in the FoodRecipesApi interface to retrieve data from the REST API.*/
}