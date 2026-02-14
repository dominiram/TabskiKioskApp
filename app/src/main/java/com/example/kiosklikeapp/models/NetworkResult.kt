package com.example.kiosklikeapp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class NetworkResult<out T>(
    val status: ApiStatus,
    val success: Boolean,
    @SerialName("result") val data: T?,
    val message: String? = null
) {

    data class Success<out T>(val t: T?) :
        NetworkResult<T>(status = ApiStatus.SUCCESS, data = t, success = true, message = null)

    data class Error<out T>(val exception: String, val t: T? = null) :
        NetworkResult<T>(status = ApiStatus.ERROR, data = t, success = false, message = exception)
}

enum class ApiStatus {
    SUCCESS,
    ERROR,
    LOADING
}
