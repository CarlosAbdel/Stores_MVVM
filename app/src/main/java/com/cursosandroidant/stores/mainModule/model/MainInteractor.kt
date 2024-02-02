package com.cursosandroidant.stores.mainModule.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.cursosandroidant.stores.StoreApplication
import com.cursosandroidant.stores.common.entities.StoreEntity
import com.cursosandroidant.stores.common.utils.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.concurrent.LinkedBlockingQueue

class MainInteractor {


    fun getStores(callback: (MutableList<StoreEntity>) -> Unit){
        val URL = Constants.STORES_URL + Constants.GET_ALL_PATH
        var storeList = mutableListOf<StoreEntity>()
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, URL , null, {response ->
            val status= response.optInt(Constants.STATUS_PROPERTY, Constants.ERROR)
            if (status == Constants.SUCCESS){
                val jsonList = response.getJSONArray(Constants.STORE_PROPERTY)?.toString()
                if (jsonList != null) {
                    val mutableListType = object : TypeToken<MutableList<StoreEntity>>() {}.type
                    storeList =
                        Gson().fromJson(jsonList, mutableListType)

                    callback(storeList)
                    return@JsonObjectRequest
                }
            }
            callback(storeList)
        },{
            it.printStackTrace()
            callback(storeList)
        })
        StoreApplication.storeAPI.addToRequestQueue(jsonObjectRequest)
    }
    fun getStoresRoom(callback: (MutableList<StoreEntity>) -> Unit){
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