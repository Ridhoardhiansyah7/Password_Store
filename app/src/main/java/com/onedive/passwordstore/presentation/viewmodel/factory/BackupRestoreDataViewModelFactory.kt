package com.onedive.passwordstore.presentation.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onedive.passwordstore.domain.repository.BackupRestoreDatabaseRepository
import com.onedive.passwordstore.presentation.viewmodel.BackupRestoreDataViewModel

/**
 * This is a factory class from BackupRestoreDataViewModel used to create viewModel instances with parameters in the constructor
 * @see ViewModelProvider.Factory
 * @see BackupRestoreDataViewModel
 */
class BackupRestoreDataViewModelFactory(
    private val repository: BackupRestoreDatabaseRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.cast(BackupRestoreDataViewModel(repository)) !!
    }
}