package com.onedive.passwordstore.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onedive.passwordstore.domain.repository.BackupRestoreDatabaseRepository
import com.onedive.passwordstore.presentation.viewmodel.factory.BackupRestoreDataViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * This is a viewModel class that is used to backup and restore data in the database
 *
 * @see BackupRestoreDatabaseRepository
 * @see BackupRestoreDataViewModelFactory
 */
class BackupRestoreDataViewModel(
    private val repository: BackupRestoreDatabaseRepository
) : ViewModel() {


    private val _backupRestoreResult = MutableLiveData<String>()
    val backupRestoreResult:LiveData<String> get() = _backupRestoreResult

    fun backupData() = viewModelScope.launch(Dispatchers.IO) {
        repository.backupDatabase(
            ifOperationIsSuccessfully = { _backupRestoreResult.postValue("BackupData is Successfully") },
            messageIfOperationIsNotSuccessfully = { _backupRestoreResult.postValue(it) }
        )
    }

    fun restoreData() = viewModelScope.launch(Dispatchers.IO) {
        repository.restoreDatabase(
            ifOperationIsSuccessfully = { _backupRestoreResult.postValue("RestoreData is Successfully") },
            messageIfOperationIsNotSuccessfully = { _backupRestoreResult.postValue(it) }
        )
    }

}