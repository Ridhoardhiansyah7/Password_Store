package com.onedive.passwordstore.data.repositoryImpl

import android.content.Context
import com.onedive.passwordstore.R
import com.onedive.passwordstore.data.dataSource.local.room.app.PasswordDatabase
import com.onedive.passwordstore.domain.repository.BackupRestoreDatabaseRepository
import de.raphaelebner.roomdatabasebackup.core.RoomBackup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * This class is an implementation class of the DatabaseRepository interface,
 * For complete documentation, please see the interface.
 *
 * Note : This implementation class only applies to database room.
 * @see BackupRestoreDatabaseRepository
 * @see RoomBackup
 */
class BackupRestoreRoomDatabaseImpl(
    private val roomBackup: RoomBackup,
    private val applicationContext:Context
) : BackupRestoreDatabaseRepository {

    override suspend fun backupDatabase(ifOperationIsSuccessfully: () -> Unit, messageIfOperationIsNotSuccessfully: (String) -> Unit) {
        withContext(Dispatchers.IO){
            backupRestoreHelper(applicationContext,
                BackupRestoreOperation.BACKUP,ifOperationIsSuccessfully,messageIfOperationIsNotSuccessfully)
        }
    }

    override suspend fun restoreDatabase(ifOperationIsSuccessfully: () -> Unit, messageIfOperationIsNotSuccessfully: (String) -> Unit) {
        withContext(Dispatchers.IO){
            backupRestoreHelper(applicationContext,
                BackupRestoreOperation.RESTORE,ifOperationIsSuccessfully,messageIfOperationIsNotSuccessfully)
        }
    }

    /**
     * For more complete documentation please see https://github.com/rafi0101/Android-Room-Database-Backup
     */
    private fun backupRestoreHelper(
        context:Context,
        operation: BackupRestoreOperation,
        operationIsSuccessfully : () -> Unit,
        messageIfOperationIsNotSuccess : (String) -> Unit
    ) {

        roomBackup
            .database(PasswordDatabase.getInstance(context))
            .enableLogDebug(true)
            .backupLocation(RoomBackup.BACKUP_FILE_LOCATION_CUSTOM_DIALOG)
            .maxFileCount(1)
            .customBackupFileName("PasswordStore.db.sqlite3")
            .apply {
                if (operation == BackupRestoreOperation.BACKUP) backup() else restore()

                onCompleteListener { success, message, exitCode ->

                    if (success && exitCode == 0) { // no errors found
                        operationIsSuccessfully()

                    }else if (exitCode == 2){ //no files selected or back button is pressed when restoring data
                        messageIfOperationIsNotSuccess(context.getString(R.string.err_no_file_selected))

                    }else if (exitCode == 3){//data has not been saved to internal storage or the back button was pressed when backing up data
                        messageIfOperationIsNotSuccess(context.getString(R.string.err_back_button_pressed))
                    }else{ // unknown err
                        throw RuntimeException("err : caused $message with err code $exitCode") // close jvm
                    }

                }
            }

    }

    private enum class BackupRestoreOperation{
        BACKUP,
        RESTORE
    }

}