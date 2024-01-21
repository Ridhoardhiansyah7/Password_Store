package com.onedive.passwordstore.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.onedive.passwordstore.domain.model.DatabaseModelDTO
import com.onedive.passwordstore.domain.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * This is a viewModel class that is used to manipulate data in any database
 *
 * @see DatabaseRepository
 */
class PasswordViewModel(private val repository: DatabaseRepository<DatabaseModelDTO>) : ViewModel() {
    fun getAll() = repository.getAll().asLiveData()

    fun getDistinctTags()  = repository.getDistinctTags().asLiveData()

    fun getAllByTagName(tagName:String) = repository.getAllByTagName(tagName).asLiveData()

    fun getDetailedItemById(id:Long) = repository.getDetailedPasswordItemById(id).asLiveData()


    fun upsert(entity:DatabaseModelDTO) = viewModelScope.launch(Dispatchers.IO) {
        repository.upsert(entity)
    }

    fun deleteById(id : Long) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteById(id)
    }

}