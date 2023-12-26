package com.onedive.passwordstore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.onedive.passwordstore.domainLayer.repository.DatabaseRepository
import com.onedive.passwordstore.viewmodel.factory.PasswordViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * This is a viewModel class that is used to manipulate data in any database
 *
 * @param T  entity / data model that will be used by the database
 * @see DatabaseRepository
 * @see PasswordViewModelFactory
 *
 * @author Ridh
 */
class PasswordViewModel<T>(private val repository: DatabaseRepository<T>) : ViewModel() {

    val getAll = repository.getAll().asLiveData()

    val getDistrictTag  = repository.getDistinctTags().asLiveData()

    fun getAllByTagName(tagName:String) = repository.getAllByTagName(tagName).asLiveData()

    fun getDetailedItemById(id:Long) = repository.getDetailedPasswordItemById(id).asLiveData()


    fun upsert(entity:T) = viewModelScope.launch(Dispatchers.IO) {
        repository.upsert(entity)
    }

    fun deleteById(id : Long) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteById(id)
    }

}