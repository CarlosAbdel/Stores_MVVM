package com.cursosandroidant.stores.mainModule.adapter

import com.cursosandroidant.stores.common.entities.StoreEntity

interface OnClickListener {
    fun onClick(storeId: Long)
    fun onFavoriteStore(storeEntity: StoreEntity)
    fun onDeleteStore(storeEntity: StoreEntity)
}