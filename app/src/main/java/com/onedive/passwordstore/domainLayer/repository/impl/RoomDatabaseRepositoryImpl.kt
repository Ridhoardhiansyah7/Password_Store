package com.onedive.passwordstore.domainLayer.repository.impl

import com.onedive.passwordstore.domainLayer.repository.DatabaseRepository
import com.onedive.passwordstore.domainLayer.dataSource.room.dao.PasswordRoomDao
import com.onedive.passwordstore.domainLayer.dataSource.room.entity.PasswordRoomEntity
import kotlinx.coroutines.flow.Flow

/**
 * This class is an implementation class of the DatabaseRepository interface,
 * For complete documentation, please see the interface.
 *
 * Note : This implementation class only applies to room database.
 * @see DatabaseRepository
 * @author Ridh
 */
class RoomDatabaseRepositoryImpl(private val passwordRoomDao: PasswordRoomDao) : DatabaseRepository<PasswordRoomEntity> {

    override fun getAll(): Flow<List<PasswordRoomEntity>> {
        return passwordRoomDao.getAll()
    }

    override fun getDistinctTags(): Flow<List<String>> {
        return passwordRoomDao.getDistinctTags()
    }

    override fun getAllByTagName(tagName: String): Flow<List<PasswordRoomEntity>> {
        return passwordRoomDao.getAllByTagName(tagName)
    }

    override fun getDetailedPasswordItemById(id: Long): Flow<PasswordRoomEntity> {
        return passwordRoomDao.getDetailedPasswordItemById(id)
    }

    override suspend fun upsert(entity: PasswordRoomEntity) {
        return passwordRoomDao.upsert(entity)
    }

    override suspend fun deleteById(id:Long) {
        return passwordRoomDao.delete(id)
    }

}