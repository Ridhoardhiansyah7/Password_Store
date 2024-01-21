package com.onedive.passwordstore.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onedive.passwordstore.domain.model.UpdateModelDTO
import com.onedive.passwordstore.domain.repository.UpdateRepository
import kotlinx.coroutines.launch

/**
 * This class is used to retrieve result data from checks carried out by the UpdateRepository implementation
 * and expose it via livedata
 *
 * @see UpdateRepository
 */
class UpdateViewModel(private val repository: UpdateRepository<UpdateModelDTO>) : ViewModel() {

    private val _update = MutableLiveData<UpdateModelDTO>()
    val updateLiveData : LiveData<UpdateModelDTO> get() = _update
    fun checkAvailableUpdate() = viewModelScope.launch {
        _update.postValue(repository.checkAvailableUpdate()) // post update data
    }



}