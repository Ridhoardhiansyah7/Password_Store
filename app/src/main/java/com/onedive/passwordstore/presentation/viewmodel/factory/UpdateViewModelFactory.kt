package com.onedive.passwordstore.presentation.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onedive.passwordstore.domain.model.UpdateModelDTO
import com.onedive.passwordstore.domain.repository.UpdateRepository
import com.onedive.passwordstore.presentation.viewmodel.UpdateViewModel

/**
 * This is a factory class from UpdateViewModel used to create viewModel instances with parameters in the constructor
 * @see ViewModelProvider.Factory
 * @see UpdateViewModel
 */
class UpdateViewModelFactory(private val updateRepository: UpdateRepository<UpdateModelDTO>): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.cast(UpdateViewModel(updateRepository)) !!
    }
}