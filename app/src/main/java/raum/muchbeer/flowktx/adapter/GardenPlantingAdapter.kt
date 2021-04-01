package raum.muchbeer.flowktx.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import raum.muchbeer.flowktx.R
import raum.muchbeer.flowktx.databinding.ListItemGardenPlantingBinding
import raum.muchbeer.flowktx.fragment.HomeViewPagerFragmentDirections
import raum.muchbeer.flowktx.model.PlantAndGardenPlantings
import raum.muchbeer.flowktx.viewmodel.PlantAndGardenPlantingVM

class GardenPlantingAdapter : ListAdapter<PlantAndGardenPlantings,
                            GardenPlantingAdapter.GardenPlantingVH>(diffCallbackUtil) {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GardenPlantingVH {
       val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemGardenPlantingBinding.inflate(inflater)
        return GardenPlantingVH(binding)
    }

    override fun onBindViewHolder(holder: GardenPlantingVH, position: Int) {
        holder.bind(getItem(position))
    }

    private object diffCallbackUtil : DiffUtil.ItemCallback<PlantAndGardenPlantings>() {
        override fun areItemsTheSame(
            oldItem: PlantAndGardenPlantings,
            newItem: PlantAndGardenPlantings
        ): Boolean {
            return oldItem.plant.plantId == newItem.plant.plantId
        }

        override fun areContentsTheSame(
            oldItem: PlantAndGardenPlantings,
            newItem: PlantAndGardenPlantings
        ): Boolean {
            return oldItem.plant == newItem.plant
        }

    }

    class GardenPlantingVH(val binding : ListItemGardenPlantingBinding) :
                        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener { view ->
                binding.viewModel?.plantId?.let { plantId ->
                    navigateToPlant(plantId, view)
                }
            }
        }

        private fun navigateToPlant(plantId: String, view: View) {
            val direction = HomeViewPagerFragmentDirections
                .actionHomeViewPagerFragmentToPlantDetailFragment(plantId)
            view.findNavController().navigate(direction)
        }

        fun bind(plantings: PlantAndGardenPlantings) {
            with(binding) {
                viewModel = PlantAndGardenPlantingVM(plantings)
                executePendingBindings()
            }
        }

    }
}