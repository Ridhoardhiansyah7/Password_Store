package com.onedive.passwordstore.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This class will have a mapping SQLite table in the database.
 *
 * For more documentation please see Android Developer website.
 * @see Entity
 */
@Entity
data class PasswordRoomDatabaseEntity(
    val title:String,
    val username:String,
    val password:String,
    val desc:String,
    val roundedColor: Int,
    val tags: String,
    val date: String,
    @PrimaryKey(autoGenerate = true) val id: Long? = 0
)