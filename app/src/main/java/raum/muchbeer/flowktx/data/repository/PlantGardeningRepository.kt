package raum.muchbeer.flowktx.data.repository

import raum.muchbeer.flowktx.data.db.GardenPlantDao
import raum.muchbeer.flowktx.model.GardenPlanting
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlantGardeningRepository
@Inject constructor(
    private val gardenPlantingDao: GardenPlantDao
){

    suspend fun createGardenPlanting(plantId: String) {
        val gardenPlanting = GardenPlanting(plantId)
        gardenPlantingDao.insertGardenPlanting(gardenPlanting)
    }

    suspend fun removeGardenPlanting(gardenPlanting: GardenPlanting) {
        gardenPlantingDao.deleteGardenPlanting(gardenPlanting)
    }

    fun isPlanted(plantId: String) =
        gardenPlantingDao.isPlanted(plantId)

    fun getPlantedGardens() = gardenPlantingDao.getPlantedGardens()
}