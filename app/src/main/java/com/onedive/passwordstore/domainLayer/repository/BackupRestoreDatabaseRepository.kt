package com.onedive.passwordstore.domainLayer.repository

/**
 * This interface is used to implement backup and restore data in the database
 * @author Ridh
 */
interface BackupRestoreDatabaseRepository {
    suspend fun backupDatabase(ifOperationIsSuccessfully: () -> Unit, messageIfOperationIsNotSuccessfully: (String) -> Unit)
    suspend fun restoreDatabase(ifOperationIsSuccessfully: () -> Unit, messageIfOperationIsNotSuccessfully: (String) -> Unit)
}