package com.example.kiosklikeapp.di

import com.apollographql.apollo.ApolloClient
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
            .serverUrl("https://your-api-endpoint.com/graphql")
            .build()
    }

    @Provides
    @Singleton
    fun provideMerchantRepository(
        apolloClient: ApolloClient
    ): MerchantRepository {
        return MerchantRepositoryImpl(apolloClient)
    }
}
