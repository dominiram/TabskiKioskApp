package com.example.kiosklikeapp.repos

import com.apollographql.apollo.ApolloClient
import com.example.kiosklikeapp.GetMerchantBrandingQuery
import com.example.kiosklikeapp.GetMerchantMenusQuery
import com.example.kiosklikeapp.models.MerchantBrandingModel
import com.example.kiosklikeapp.models.MerchantMenuModel
import com.example.kiosklikeapp.models.NetworkResult
import com.example.kiosklikeapp.utils.toMenuDomainModel
import com.example.kiosklikeapp.utils.toMerchantDataModel

interface MerchantRepository {
    suspend fun fetchBranding(merchantId: String): NetworkResult<MerchantBrandingModel>

    suspend fun fetchMenus(merchantId: String): NetworkResult<List<MerchantMenuModel>>
}

class MerchantRepositoryImpl(private val apolloClient: ApolloClient) : MerchantRepository {
    override suspend fun fetchBranding(merchantId: String): NetworkResult<MerchantBrandingModel> {
        return try {
            val response = apolloClient
                .query(GetMerchantBrandingQuery(id = merchantId))
                .execute()

            if (response.hasErrors()) return NetworkResult.Error(
                response.errors?.firstOrNull()?.message ?: "GraphQL Error"
            )

            response.data?.merchant?.let { NetworkResult.Success(it.toMerchantDataModel()) }
                ?: NetworkResult.Error("Merchant not found")
        } catch (e: Exception) {
            NetworkResult.Error(exception = e.message ?: "")
        }
    }

    override suspend fun fetchMenus(merchantId: String): NetworkResult<List<MerchantMenuModel>> {
        return try {
            val response = apolloClient
                .query(GetMerchantMenusQuery(merchantId = merchantId))
                .execute()

            if (response.hasErrors()) {
                return NetworkResult.Error(
                    response.errors?.firstOrNull()?.message ?: "Error fetching menus"
                )
            }

            response.data?.menus?.toMenuDomainModel()?.let { NetworkResult.Success(it) }
                ?: NetworkResult.Error("Menus not found for this merchant")
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Network Exception")
        }
    }
}
