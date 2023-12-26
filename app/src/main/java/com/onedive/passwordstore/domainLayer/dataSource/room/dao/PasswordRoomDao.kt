package com.onedive.passwordstore.domainLayer.dataSource.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.onedive.passwordstore.domainLayer.dataSource.room.entity.PasswordRoomEntity
import kotlinx.coroutines.flow.Flow

@Dao
/**
 * this is database interaction interface
 * Data Access Objects are the main classes where you define your database interactions. They can include a variety of query methods.
 *
 * Note : For More Complete documentation please see Android Developer website
 *
 * @see Dao
 * @author Ridh
 *
 */
interface PasswordRoomDao {

    @Query("SELECT * FROM PasswordRoomEntity")
    fun getAll() : Flow<List<PasswordRoomEntity>>

    @Query("SELECT DISTINCT tags FROM PasswordRoomEntity ")
    fun getDistinctTags() : Flow<List<String>>

    @Query("SELECT * FROM PasswordRoomEntity WHERE tags = :tagName")
    fun getAllByTagName(tagName:String) : Flow<List<PasswordRoomEntity>>

    @Query("SELECT * FROM PasswordRoomEntity WHERE id = :key")
    fun getDetailedPasswordItemById(key:Long) : Flow<PasswordRoomEntity>

    @Upsert
    suspend fun upsert(entity: PasswordRoomEntity)

    @Query("DELETE FROM PasswordRoomEntity WHERE id = :key")
    suspend fun delete(key:Long)

}