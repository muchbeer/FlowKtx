package raum.muchbeer.flowktx.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import raum.muchbeer.flowktx.adapter.PhotoAdapter
import raum.muchbeer.flowktx.databinding.FragmentGalleryBinding
import raum.muchbeer.flowktx.viewmodel.PhotoVM

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private val adapter = PhotoAdapter()
    private val args: GalleryFragmentArgs by navArgs()
    private var searchJob: Job? = null
    private val viewModel: PhotoVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentGalleryBinding.inflate(inflater, container, false)

        binding.photoList.adapter = adapter
        search(args.plantName)

        binding.toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }
        return binding.root
    }

    private fun search(query: String) {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchPictures(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }
}