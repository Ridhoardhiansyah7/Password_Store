package com.onedive.passwordstore.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onedive.passwordstore.domainLayer.repository.BackupRestoreDatabaseRepository
import com.onedive.passwordstore.viewmodel.BackupRestoreDataViewModel

class BackupRestoreDataViewModelFactory(
    private val repository: BackupRestoreDatabaseRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.cast(BackupRestoreDataViewModel(repository)) !!
    }
}