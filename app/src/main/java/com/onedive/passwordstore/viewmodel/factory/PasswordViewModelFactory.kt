package com.onedive.passwordstore.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onedive.passwordstore.domainLayer.repository.DatabaseRepository
import com.onedive.passwordstore.viewmodel.PasswordViewModel

/**
 * This is a factory class from PasswordViewModel used to create viewModel instances with parameters in the constructor
 * @see ViewModelProvider.Factory
 * @see PasswordViewModel
 */

class PasswordViewModelFactory<T>(private val repository: DatabaseRepository<T>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.cast(PasswordViewModel(repository))!!
    }

}