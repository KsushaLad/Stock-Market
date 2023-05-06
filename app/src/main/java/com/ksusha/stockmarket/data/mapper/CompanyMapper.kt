package com.ksusha.stockmarket.data.mapper

import com.ksusha.stockmarket.data.local.CompanyListingEntity
import com.ksusha.stockmarket.domain.model.CompanyListing

fun CompanyListingEntity.toCompatyListing(): CompanyListing {
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompatyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}