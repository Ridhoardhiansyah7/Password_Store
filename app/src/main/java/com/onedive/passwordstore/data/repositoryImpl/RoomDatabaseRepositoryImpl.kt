package com.onedive.passwordstore.data.repositoryImpl

import com.onedive.passwordstore.data.local.room.dao.PasswordRoomDao
import com.onedive.passwordstore.data.mapper.toDatabaseModel
import com.onedive.passwordstore.data.mapper.toDatabaseModelList
import com.onedive.passwordstore.data.mapper.toPasswordRoomDatabaseEntity
import com.onedive.passwordstore.domain.model.DatabaseModelDTO
import com.onedive.passwordstore.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * This class is an implementation class of the DatabaseRepository interface,
 * For complete documentation, please see the interface.
 *
 * Note : This implementation class only applies to room database.
 * @see DatabaseRepository
 */
class RoomDatabaseRepositoryImpl(private val passwordRoomDao: PasswordRoomDao) : DatabaseRepository<DatabaseModelDTO> {

    override fun getAll(): Flow<List<DatabaseModelDTO>> {
        return passwordRoomDao.getAll().map { it.toDatabaseModelList() }
    }

    override fun getDistinctTags(): Flow<List<String>> {
        return passwordRoomDao.getDistinctTags()
    }

    override fun getAllByTagName(tagName: String): Flow<List<DatabaseModelDTO>> {
        return passwordRoomDao.getAllByTagName(tagName).map { it.toDatabaseModelList() }
    }

    override fun getDetailedPasswordItemById(id: Long): Flow<DatabaseModelDTO> {
        return passwordRoomDao.getDetailedPasswordItemById(id).map { it.toDatabaseModel() }
    }

    override suspend fun upsert(entity: DatabaseModelDTO) {
        return passwordRoomDao.upsert(entity.toPasswordRoomDatabaseEntity())
    }

    override suspend fun deleteById(id:Long) {
        passwordRoomDao.delete(id)
    }

    override suspend fun deleteAllData() {
        passwordRoomDao.deleteAllData()
    }

}