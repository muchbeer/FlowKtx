package raum.muchbeer.flowktx.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import raum.muchbeer.flowktx.model.GardenPlanting
import raum.muchbeer.flowktx.model.PlantAndGardenPlantings

@Dao
interface GardenPlantDao {
    @Query("SELECT * FROM garden_plantings")
    fun getGardenPlantings(): Flow<List<GardenPlanting>>

    @Query("SELECT EXISTS(SELECT 1 FROM garden_plantings WHERE plant_id = :plantId LIMIT 1)")
    fun isPlanted(plantId: String): Flow<Boolean>

    /**
     * This query will tell Room to query both the [Plant] and [GardenPlanting] tables and handle
     * the object mapping.
     */
    @Transaction
    @Query("SELECT * FROM plants WHERE id IN (SELECT DISTINCT(plant_id) FROM garden_plantings)")
    fun getPlantedGardens(): Flow<List<PlantAndGardenPlantings>>

    @Insert
    suspend fun insertGardenPlanting(gardenPlanting: GardenPlanting): Long

    @Delete
    suspend fun deleteGardenPlanting(gardenPlanting: GardenPlanting)
}