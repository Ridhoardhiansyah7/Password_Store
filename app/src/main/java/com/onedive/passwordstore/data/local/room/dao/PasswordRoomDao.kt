package com.onedive.passwordstore.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.onedive.passwordstore.data.local.room.entity.PasswordRoomDatabaseEntity
import kotlinx.coroutines.flow.Flow

@Dao
/**
 * this is database interaction interface
 * Data Access Objects are the main classes where you define your database interactions. They can include a variety of query methods.
 *
 * Note : For More Complete documentation please see Android Developer website
 *
 * @see Dao
 *
 */
interface PasswordRoomDao {

    @Query("SELECT * FROM PasswordRoomDatabaseEntity")
    fun getAll() : Flow<List<PasswordRoomDatabaseEntity>>

    @Query("SELECT DISTINCT tags FROM PasswordRoomDatabaseEntity ")
    fun getDistinctTags() : Flow<List<String>>

    @Query("SELECT * FROM PasswordRoomDatabaseEntity WHERE tags = :tagName")
    fun getAllByTagName(tagName:String) : Flow<List<PasswordRoomDatabaseEntity>>

    @Query("SELECT * FROM PasswordRoomDatabaseEntity WHERE id = :key")
    fun getDetailedPasswordItemById(key:Long) : Flow<PasswordRoomDatabaseEntity>

    @Upsert
    suspend fun upsert(entity: PasswordRoomDatabaseEntity)

    @Query("DELETE FROM PasswordRoomDatabaseEntity WHERE id = :key")
    suspend fun delete(key:Long)

}