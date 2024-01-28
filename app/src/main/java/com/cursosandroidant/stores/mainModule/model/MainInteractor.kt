package com.cursosandroidant.stores.mainModule.model

import com.cursosandroidant.stores.StoreApplication
import com.cursosandroidant.stores.common.entities.StoreEntity
import java.util.concurrent.LinkedBlockingQueue

class MainInteractor {

       fun getStores(callback: (MutableList<StoreEntity>) -> Unit){
        val queue = LinkedBlockingQueue<MutableList<StoreEntity>>()
        Thread{
            val storesList = StoreApplication.database.storeDao().getAllStores()
            queue.add(storesList)

        }.start()
        callback(queue.take())
    }

    fun deleteStore(storeEntity: StoreEntity, callback: (StoreEntity) -> Unit){
        val queue = LinkedBlockingQueue<StoreEntity>()
        Thread{
            StoreApplication.database.storeDao().deleteStore(storeEntity)
            queue.add(storeEntity)
        }.start()
        callback(queue.take())
    }

    fun updateStore(storeEntity: StoreEntity, callback: (StoreEntity) -> Unit){
        val queue= LinkedBlockingQueue<StoreEntity>()
        Thread{
            StoreApplication.database.storeDao().updateStore(storeEntity)
            queue.add(storeEntity)
        }.start()
        callback(queue.take())
    }
}