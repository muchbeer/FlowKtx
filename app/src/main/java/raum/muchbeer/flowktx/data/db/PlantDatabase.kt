package raum.muchbeer.flowktx.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import raum.muchbeer.flowktx.model.GardenPlanting
import raum.muchbeer.flowktx.model.Plant
import raum.muchbeer.flowktx.utilities.DATABASE_NAME
import raum.muchbeer.flowktx.work.PlantDatabaseWorker

@Database(entities = [Plant::class, GardenPlanting::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PlantDatabase : RoomDatabase() {

    abstract fun gardenPlantingDao(): GardenPlantDao
    abstract fun plantDao(): PlantDao

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: PlantDatabase? = null

        fun getInstance(context: Context): PlantDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): PlantDatabase {
            return Room.databaseBuilder(context, PlantDatabase::class.java, DATABASE_NAME)
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<PlantDatabaseWorker>().build()
                            WorkManager.getInstance().enqueue(request)
                        }
                    }
                )
                .build()
        }
    }


}