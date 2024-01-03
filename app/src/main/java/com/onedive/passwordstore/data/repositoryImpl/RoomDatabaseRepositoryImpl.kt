package com.onedive.passwordstore.data.repositoryImpl

import com.onedive.passwordstore.data.dataSource.local.room.dao.PasswordRoomDao
import com.onedive.passwordstore.data.dataSource.local.room.entity.PasswordRoomDatabaseEntity
import com.onedive.passwordstore.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow

/**
 * This class is an implementation class of the DatabaseRepository interface,
 * For complete documentation, please see the interface.
 *
 * Note : This implementation class only applies to room database.
 * @see DatabaseRepository
 * @author Ridh
 */
class RoomDatabaseRepositoryImpl(private val passwordRoomDao: PasswordRoomDao) : DatabaseRepository<PasswordRoomDatabaseEntity> {

    override fun getAll(): Flow<List<PasswordRoomDatabaseEntity>> {
        return passwordRoomDao.getAll()
    }

    override fun getDistinctTags(): Flow<List<String>> {
        return passwordRoomDao.getDistinctTags()
    }

    override fun getAllByTagName(tagName: String): Flow<List<PasswordRoomDatabaseEntity>> {
        return passwordRoomDao.getAllByTagName(tagName)
    }

    override fun getDetailedPasswordItemById(id: Long): Flow<PasswordRoomDatabaseEntity> {
        return passwordRoomDao.getDetailedPasswordItemById(id)
    }

    override suspend fun upsert(entity: PasswordRoomDatabaseEntity) {
        return passwordRoomDao.upsert(entity)
    }

    override suspend fun deleteById(id:Long) {
        return passwordRoomDao.delete(id)
    }

}