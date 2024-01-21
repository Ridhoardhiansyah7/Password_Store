package com.onedive.passwordstore.data.local.room.app

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.onedive.passwordstore.data.local.room.dao.PasswordRoomDao
import com.onedive.passwordstore.data.local.room.entity.PasswordRoomDatabaseEntity
import com.onedive.passwordstore.utils.Const
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(entities = [PasswordRoomDatabaseEntity::class], version = 1, exportSchema = true)
abstract class PasswordDatabase : RoomDatabase() {

    abstract fun dao() : PasswordRoomDao

    companion object{

        @Volatile private var INSTANCE: PasswordDatabase? = null
        private const val DATABASE_NAME = "pass.db"

        /**
         * @return : Singleton instance of room database
         * @param context : ApplicationContext
         */
        @JvmStatic
        fun getInstance(context: Context) : PasswordDatabase {

            val passphrase = SQLiteDatabase.getBytes(Const.getNativeEncryptKey().toCharArray())
            val supportFactory = SupportFactory(passphrase)

            return INSTANCE ?: synchronized(this){

                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = PasswordDatabase::class.java,
                    name = DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .openHelperFactory(supportFactory)
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}