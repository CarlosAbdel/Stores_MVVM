package com.cursosandroidant.stores.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cursosandroidant.stores.common.entities.StoreEntity
import com.cursosandroidant.stores.common.utils.Constants
import com.cursosandroidant.stores.mainModule.model.MainInteractor

class MainViewModel: ViewModel() {
    private var storeList: MutableList<StoreEntity> = mutableListOf()
    private var interactor: MainInteractor = MainInteractor()

    private val stores: MutableLiveData<MutableList<StoreEntity>> by lazy {
        MutableLiveData<MutableList<StoreEntity>>()/*.also {
            loadStores()
        }*/
    }

    private val showProgress : MutableLiveData<Boolean> = MutableLiveData()

    fun getStores(): LiveData<MutableList<StoreEntity>>{
        return stores.also {
            loadStores()
        }
    }

    fun isShowProgress() : LiveData<Boolean>{
        return showProgress
    }

    private fun loadStores() {
        showProgress.value= Constants.SHOW
          interactor.getStores {
              showProgress.value= Constants.HIDE
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