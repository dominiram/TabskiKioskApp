package com.example.kiosklikeapp.repos

import com.apollographql.apollo.ApolloClient
import com.example.kiosklikeapp.GetMerchantBrandingQuery
import com.example.kiosklikeapp.models.MerchantBrandingModel
import com.example.kiosklikeapp.models.NetworkResult
import com.example.kiosklikeapp.utils.toMerchantDataModel

interface MerchantRepository {
    suspend fun fetchBranding(merchantId: String): NetworkResult<MerchantBrandingModel>
}

class MerchantRepositoryImpl(private val apolloClient: ApolloClient) : MerchantRepository {

    override suspend fun fetchBranding(merchantId: String): NetworkResult<MerchantBrandingModel> {
        return try {
            val response = apolloClient
                .query(GetMerchantBrandingQuery(id = merchantId))
                .execute()

            response.data?.merchant?.let { NetworkResult.Success(it.toMerchantDataModel()) }
                ?: NetworkResult.Error("Merchant not found")
        } catch (e: Exception) {
            NetworkResult.Error(exception = e.message ?: "")
        }
    }
}
