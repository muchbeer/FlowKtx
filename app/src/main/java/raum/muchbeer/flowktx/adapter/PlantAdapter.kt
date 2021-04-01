package raum.muchbeer.flowktx.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import raum.muchbeer.flowktx.databinding.ListItemPlantBinding
import raum.muchbeer.flowktx.fragment.HomeViewPagerFragmentDirections
import raum.muchbeer.flowktx.model.Plant

class PlantAdapter : ListAdapter<Plant, PlantAdapter.PlantVH>(diffCallUtil) {


    override fun onBindViewHolder(holder: PlantVH, position: Int) {
        val plant = getItem(position)
        (holder as PlantVH).bind(plant!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemPlantBinding.inflate(inflater)
        return PlantVH(binding)
    }


    class PlantVH(val binding: ListItemPlantBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                binding.plant?.let { plant ->
                    navigateToPlant(plant, it)
                }
            }
        }

        private fun navigateToPlant(
            plant: Plant,
            view: View
        ) {
            val direction =
                HomeViewPagerFragmentDirections.actionHomeViewPagerFragmentToPlantDetailFragment(
                    plant.plantId
                )
            view.findNavController().navigate(direction)
        }

        fun bind(item: Plant) {
            binding.apply {
                plant = item
                executePendingBindings()
            }
        }
    }
}
    private object diffCallUtil : DiffUtil.ItemCallback<Plant>() {

        override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem.plantId == newItem.plantId
        }

        override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem == newItem
        }
}