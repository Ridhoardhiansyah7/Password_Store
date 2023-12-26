package com.onedive.passwordstore.domainLayer.dataSource.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This class will have a mapping SQLite table in the database.
 *
 * For more documentation please see Android Developer website.
 * @author Ridh
 * @see Entity
 */
@Entity
data class PasswordRoomEntity(
    val title:String,
    val username:String,
    val password:String,
    val desc:String,
    val roundedColor: Int,
    val tags: String,
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP") val date: String,
    @PrimaryKey(autoGenerate = true) val id: Long? = 0
)