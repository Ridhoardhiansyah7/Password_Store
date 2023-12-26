package com.onedive.passwordstore.domainLayer.dataSource.room.app

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.onedive.passwordstore.domainLayer.dataSource.room.dao.PasswordRoomDao
import com.onedive.passwordstore.domainLayer.dataSource.room.entity.PasswordRoomEntity

@Database(entities = [PasswordRoomEntity::class], version = 1, exportSchema = true)
abstract class PasswordDatabase : RoomDatabase() {

    abstract fun dao() : PasswordRoomDao

    companion object{

        /**
         * Return : Instance of room database
         */
        @Volatile private var INSTANCE: PasswordDatabase? = null
        fun getInstance(context: Context) : PasswordDatabase {

            return INSTANCE ?: synchronized(this){

                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = PasswordDatabase::class.java,
                    name = "pass.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}