package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.knowledge.DeviceKnowledgeBase
import com.example.data.model.DeviceEntity
import com.example.data.model.FeatureRequestEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [DeviceEntity::class, FeatureRequestEntity::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun deviceDao(): DeviceDao
    abstract fun featureRequestDao(): FeatureRequestDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "allconnect_v3_db"
                )
                .fallbackToDestructiveMigration(dropAllTables = true)
                .fallbackToDestructiveMigrationOnDowngrade(dropAllTables = true)
                .addCallback(DatabaseCallback(scope))
                .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateInitialDatabase(database.deviceDao(), database.featureRequestDao())
                    }
                }
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        if (database.deviceDao().getDeviceCount() == 0) {
                            populateInitialDatabase(database.deviceDao(), database.featureRequestDao())
                        }
                    }
                }
            }

            suspend fun populateInitialDatabase(deviceDao: DeviceDao, featureDao: FeatureRequestDao) {
                val initialDevices = DeviceKnowledgeBase.getInitialDevices()
                deviceDao.insertAll(initialDevices)
                val initialRequests = DeviceKnowledgeBase.getInitialFeatureRequests()
                featureDao.insertAll(initialRequests)
            }
        }
    }
}

