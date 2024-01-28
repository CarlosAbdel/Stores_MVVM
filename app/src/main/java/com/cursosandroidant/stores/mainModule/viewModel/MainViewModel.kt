package com.cursosandroidant.stores.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cursosandroidant.stores.common.entities.StoreEntity
import com.cursosandroidant.stores.mainModule.model.MainInteractor

class MainViewModel: ViewModel() {
    private var storeList: MutableList<StoreEntity> = mutableListOf()
    private var interactor: MainInteractor = MainInteractor()

    private val stores: MutableLiveData<List<StoreEntity>> by lazy {
        MutableLiveData<List<StoreEntity>>()/*.also {
            loadStores()
        }*/
    }

    fun getStores(): LiveData<List<StoreEntity>>{
        return stores.also {
            loadStores()
        }
    }

    private fun loadStores() {
          interactor.getStores {
            stores.value = it
              storeList = it
        }
    }

    fun deleteStore(storeEntity: StoreEntity){
        interactor.deleteStore(storeEntity) {
            val index = storeList.indexOf(storeEntity)
            if (index != -1){
                storeList.removeAt(index)
                stores.value = storeList
            }
        }
    }

    fun updateStore(storeEntity: StoreEntity){
        storeEntity.isFavorite = !storeEntity.isFavorite
        interactor.updateStore(storeEntity) {
            val index = storeList.indexOf(storeEntity)
            if (index != -1) {
                storeList.removeAt(index)
                stores.value = storeList
            }
        }
        loadStores()
    }
}