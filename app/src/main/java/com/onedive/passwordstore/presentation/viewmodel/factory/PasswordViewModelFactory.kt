package com.onedive.passwordstore.presentation.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onedive.passwordstore.domain.model.DatabaseModelDTO
import com.onedive.passwordstore.domain.repository.DatabaseRepository
import com.onedive.passwordstore.presentation.viewmodel.PasswordViewModel

/**
 * This is a factory class from PasswordViewModel used to create viewModel instances with parameters in the constructor
 * @see ViewModelProvider.Factory
 * @see PasswordViewModel
 */

class PasswordViewModelFactory(private val repository: DatabaseRepository<DatabaseModelDTO>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.cast(PasswordViewModel(repository))!!
    }

}