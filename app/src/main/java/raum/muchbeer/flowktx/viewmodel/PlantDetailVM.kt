package raum.muchbeer.flowktx.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import raum.muchbeer.flowktx.BuildConfig
import raum.muchbeer.flowktx.data.repository.PlantGardeningRepository
import raum.muchbeer.flowktx.data.repository.PlantRepository

class PlantDetailVM
@AssistedInject constructor(
    plantRepository: PlantRepository,
    private val gardenPlantingRepository: PlantGardeningRepository,
    @Assisted private val plantId: String
) : ViewModel(){

    val isPlanted = gardenPlantingRepository.isPlanted(plantId).asLiveData()
    val plant = plantRepository.getPlant(plantId).asLiveData()

    fun addPlantToGarden() {
        viewModelScope.launch {
            gardenPlantingRepository.createGardenPlanting(plantId)
        }
    }

    fun hasValidUnsplashKey() = (BuildConfig.UNSPLASH_ACCESS_KEY != "null")

    companion object {
        fun provideFactory(
            assistedFactory: PlantDetailViewModelFactory,
            plantId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(plantId) as T
            }
        }
    }


}
@AssistedFactory
interface PlantDetailViewModelFactory {
    fun create(plantId: String): PlantDetailVM
}