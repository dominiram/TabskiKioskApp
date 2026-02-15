package com.example.kiosklikeapp.repos

import com.apollographql.apollo.ApolloClient
import com.example.kiosklikeapp.GetMerchantBrandingQuery
import com.example.kiosklikeapp.GetMerchantMenusQuery
import com.example.kiosklikeapp.models.MerchantBrandingModel
import com.example.kiosklikeapp.models.MerchantMenuModel
import com.example.kiosklikeapp.models.NetworkResult
import com.example.kiosklikeapp.utils.toMenuDomainModel
import com.example.kiosklikeapp.utils.toMerchantDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface MerchantRepository {
    fun fetchBranding(): Flow<NetworkResult<MerchantBrandingModel>>

    fun fetchMenus(): Flow<NetworkResult<List<MerchantMenuModel>>>
}

class MerchantRepositoryImpl(private val apolloClient: ApolloClient) : MerchantRepository {

    override fun fetchBranding() = flow {
        val merchantId = MERCHANT_ID

        val result: NetworkResult<MerchantBrandingModel> = try {
            val response = apolloClient
                .query(GetMerchantBrandingQuery(id = merchantId))
                .execute()

            response.data?.merchant?.takeIf { !response.hasErrors() }?.let {
                NetworkResult.Success(it.toMerchantDataModel())
            } ?: NetworkResult.Error(
                response.errors?.firstOrNull()?.message ?: "GraphQL Error"
            )
        } catch (e: Exception) {
            NetworkResult.Error(errorMessage = e.message ?: "")
        }

        emit(result)
    }

    override fun fetchMenus(): Flow<NetworkResult<List<MerchantMenuModel>>> = flow {
        val merchantId = MERCHANT_ID

        val result = try {
            val response = apolloClient
                .query(GetMerchantMenusQuery(merchantId = merchantId))
                .execute()

            response.data?.menus?.takeIf { !response.hasErrors() }?.let {
                NetworkResult.Success(it.toMenuDomainModel())
            } ?: NetworkResult.Error(
                response.errors?.firstOrNull()?.message ?: "GraphQL Error"
            )
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Network Exception")
        }

        emit(result)
    }

    companion object {
        private const val MERCHANT_ID = "cmaxrxqhs0icsob9oe0sccxuw"
    }
}
