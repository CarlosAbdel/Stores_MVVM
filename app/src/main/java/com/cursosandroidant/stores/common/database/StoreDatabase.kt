package com.cursosandroidant.stores.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cursosandroidant.stores.common.entities.StoreEntity

@Database(entities = arrayOf(StoreEntity::class), version = 2, exportSchema = false)
abstract class StoreDatabase : RoomDatabase() {
    abstract fun storeDao(): StoreDao
}