package raum.muchbeer.flowktx.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import raum.muchbeer.flowktx.model.PlantAndGardenPlantings
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class PlantAndGardenPlantingVM
 constructor(
     plantings: PlantAndGardenPlantings
){

    private val plant = checkNotNull(plantings.plant)
    private val gardenPlanting = plantings.gardenPlantings[0]

    val waterDateString: String = dateFormat.format(gardenPlanting.lastWateringDate.time)
    val wateringInterval
        get() = plant.wateringInterval
    val imageUrl
        get() = plant.imageUrl
    val plantName
        get() = plant.name
    val plantDateString: String = dateFormat.format(gardenPlanting.plantDate.time)
    val plantId
        get() = plant.plantId

    companion object {
        private val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.US)
    }
}