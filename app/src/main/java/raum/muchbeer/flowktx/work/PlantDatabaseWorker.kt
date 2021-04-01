package raum.muchbeer.flowktx.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.coroutineScope
import raum.muchbeer.flowktx.data.db.PlantDatabase
import raum.muchbeer.flowktx.model.Plant
import raum.muchbeer.flowktx.utilities.PLANT_DATA_FILENAME

class PlantDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result =  coroutineScope {
        try {
            applicationContext.assets.open(PLANT_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val plantType = object : TypeToken<List<Plant>>() {}.type
                    val plantList: List<Plant> = Gson().fromJson(jsonReader, plantType)

                    val database = PlantDatabase.getInstance(applicationContext)
                    database.plantDao().insertAll(plantList)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }
    companion object {
        private const val TAG = "PlantDatabaseWorker"
    }
}