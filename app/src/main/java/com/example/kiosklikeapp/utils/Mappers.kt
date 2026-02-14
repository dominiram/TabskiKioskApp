package com.example.kiosklikeapp.utils

import com.example.kiosklikeapp.GetMerchantBrandingQuery
import com.example.kiosklikeapp.models.MerchantBrandingModel

fun GetMerchantBrandingQuery.Merchant.toMerchantDataModel() = MerchantBrandingModel(
    id = id,
    logoUrl = branding?.logoUrl,
    coverUrl = branding?.coverUrl,
    backgroundColor = branding?.buttonBgColor,
    textColor = branding?.buttonTextColor,
    brightness = branding?.coverBrightness
)
