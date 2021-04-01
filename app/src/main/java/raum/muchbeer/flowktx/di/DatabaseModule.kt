package raum.muchbeer.flowktx.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import raum.muchbeer.flowktx.data.db.GardenPlantDao
import raum.muchbeer.flowktx.data.db.PlantDao
import raum.muchbeer.flowktx.data.db.PlantDatabase
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providePlantDatabase(@ApplicationContext context: Context) : PlantDatabase {
        return PlantDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun providePlantDao(plantDatabase: PlantDatabase) : PlantDao {
        return plantDatabase.plantDao()
    }

    @Singleton
    @Provides
    fun provideGardenPlantDao(plantDatabase: PlantDatabase) : GardenPlantDao {
        return plantDatabase.gardenPlantingDao()
    }
}