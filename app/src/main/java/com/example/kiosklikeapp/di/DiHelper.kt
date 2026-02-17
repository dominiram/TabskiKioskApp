package com.example.kiosklikeapp.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.http.HttpRequest
import com.apollographql.apollo.api.http.HttpResponse
import com.apollographql.apollo.network.http.HttpInterceptor
import com.apollographql.apollo.network.http.HttpInterceptorChain
import com.example.kiosklikeapp.repos.MerchantRepository
import com.example.kiosklikeapp.repos.MerchantRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://api-qa.tabski.com/graphql")
            .addHttpHeader("x-app", "ONLINE")
            .addHttpInterceptor(object : HttpInterceptor {
                override suspend fun intercept(request: HttpRequest, chain: HttpInterceptorChain): HttpResponse {
                    android.util.Log.d("AuthInterceptor", "Requesting: ${request.url}")
                    val response = chain.proceed(request)

                    android.util.Log.d("AuthInterceptor", "HTTP Status: ${response.statusCode}")

                    val bodyString = response.body?.peek()?.readUtf8()
                    android.util.Log.d("AuthInterceptor", "Raw Body: $bodyString")

                    return response
                }
            })
            .build()
    }

    @Provides
    fun provideMerchantRepository(
        apolloClient: ApolloClient
    ): MerchantRepository {
        return MerchantRepositoryImpl(apolloClient)
    }
}
