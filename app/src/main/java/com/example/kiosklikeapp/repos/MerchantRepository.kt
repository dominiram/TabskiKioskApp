package com.example.kiosklikeapp.repos

import com.apollographql.apollo.ApolloClient
import com.example.kiosklikeapp.GetMerchantBrandingQuery
import com.example.kiosklikeapp.GetMerchantMenusQuery
import com.example.kiosklikeapp.models.MenuItemModel
import com.example.kiosklikeapp.models.MerchantBrandingModel
import com.example.kiosklikeapp.models.MerchantMenuModel
import com.example.kiosklikeapp.models.NetworkResult
import com.example.kiosklikeapp.models.PurchaseItemInfo
import com.example.kiosklikeapp.utils.addAllMenusItem
import com.example.kiosklikeapp.utils.toMenuDomainModel
import com.example.kiosklikeapp.utils.toMerchantDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

interface MerchantRepository {
    fun fetchBranding(): Flow<NetworkResult<MerchantBrandingModel>>

    fun fetchMenus(): Flow<NetworkResult<List<MerchantMenuModel>>>

    fun storeAddedItems(item: MenuItemModel, count: Int)

    fun getAddedItemsFlow(): Flow<Map<String, PurchaseItemInfo>>
}

class MerchantRepositoryImpl(private val apolloClient: ApolloClient) : MerchantRepository {

    private val addedItems = MutableStateFlow<Map<String, PurchaseItemInfo>>(mapOf())

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
                val menus = it.toMenuDomainModel()
                NetworkResult.Success(menus.addAllMenusItem())
            } ?: NetworkResult.Error(
                response.errors?.firstOrNull()?.message ?: "GraphQL Error"
            )
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Network Exception")
        }

        emit(result)
    }

    override fun storeAddedItems(item: MenuItemModel, count: Int) {
        val itemsMap = hashMapOf<String, PurchaseItemInfo>().apply {
            addedItems.value.let {
                for ((key, value) in it) {
                    put(key, value)
                }

                if (!it.contains(item.id)) put(
                    item.id,
                    PurchaseItemInfo(
                        id = item.id,
                        name = item.name,
                        imageUrl = item.imageUrl,
                        price = item.price,
                        count = count
                    )
                ) else {
                    get(item.id)?.let { purchaseInfoItem ->
                        replace(
                            item.id,
                            purchaseInfoItem.copy(count = purchaseInfoItem.count + count)
                        )
                    }
                }
            }
        }

        addedItems.value = itemsMap
    }

    override fun getAddedItemsFlow() = addedItems

    companion object {
        private const val MERCHANT_ID = "cmaxrxqhs0icsob9oe0sccxuw"
    }
}
