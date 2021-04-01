package raum.muchbeer.flowktx.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import raum.muchbeer.flowktx.data.repository.PlantGardeningRepository
import raum.muchbeer.flowktx.model.PlantAndGardenPlantings
import javax.inject.Inject

@HiltViewModel
class GardenPlantListVM
@Inject internal constructor(
    gardenPlantingRepository: PlantGardeningRepository
) : ViewModel(){

    val plantAndGardenPlantings: LiveData<List<PlantAndGardenPlantings>> =
        gardenPlantingRepository.getPlantedGardens().asLiveData()
}