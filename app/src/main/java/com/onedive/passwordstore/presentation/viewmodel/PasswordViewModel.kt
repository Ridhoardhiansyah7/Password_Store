package com.onedive.passwordstore.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.onedive.passwordstore.domain.repository.DatabaseRepository
import com.onedive.passwordstore.presentation.viewmodel.factory.PasswordViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * This is a viewModel class that is used to manipulate data in any database
 *
 * @param E  entity / data model that will be used by the database
 * @see DatabaseRepository
 * @see PasswordViewModelFactory
 *
 * @author Ridh
 */
class PasswordViewModel<E>(private val repository: DatabaseRepository<E>) : ViewModel() {
    fun getAll() = repository.getAll().asLiveData()

    fun getDistinctTags()  = repository.getDistinctTags().asLiveData()

    fun getAllByTagName(tagName:String) = repository.getAllByTagName(tagName).asLiveData()

    fun getDetailedItemById(id:Long) = repository.getDetailedPasswordItemById(id).asLiveData()


    fun upsert(entity:E) = viewModelScope.launch(Dispatchers.IO) {
        repository.upsert(entity)
    }

    fun deleteById(id : Long) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteById(id)
    }

}