package raum.muchbeer.flowktx.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ShareCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import raum.muchbeer.flowktx.R
import raum.muchbeer.flowktx.databinding.FragmentPlantDetailBinding
import raum.muchbeer.flowktx.model.Plant
import raum.muchbeer.flowktx.viewmodel.PlantDetailVM
import raum.muchbeer.flowktx.viewmodel.PlantDetailViewModelFactory
import javax.inject.Inject


@AndroidEntryPoint
class PlantDetailFragment : Fragment() {

    private val args: PlantDetailFragmentArgs by navArgs()

    @Inject
    lateinit var plantDetailViewModelFactory: PlantDetailViewModelFactory

    private val plantDetailViewModel: PlantDetailVM by viewModels {
        PlantDetailVM.provideFactory(plantDetailViewModelFactory, args.plantId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentPlantDetailBinding.inflate(inflater, container, false).apply {
            viewModel = plantDetailViewModel
            lifecycleOwner = viewLifecycleOwner

            callback = Callback { plant ->
                plant?.let {
                    hideAppBarFab(fab)
                    plantDetailViewModel.addPlantToGarden()
                    Snackbar.make(root, R.string.added_plant_to_garden, Snackbar.LENGTH_LONG)
                        .show()
                }
            }

            galleryNav.setOnClickListener { navigateToGallery() }

            var isToolbarShown = false

            // scroll change listener begins at Y = 0 when image is fully collapsed
            plantDetailScrollview.setOnScrollChangeListener(
                NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->

                    // User scrolled past image to height of toolbar and the title text is
                    // underneath the toolbar, so the toolbar should be shown.
                    val shouldShowToolbar = scrollY > toolbar.height

                    // The new state of the toolbar differs from the previous state; update
                    // appbar and toolbar attributes.
                    if (isToolbarShown != shouldShowToolbar) {
                        isToolbarShown = shouldShowToolbar

                        // Use shadow animator to add elevation if toolbar is shown
                        appbar.isActivated = shouldShowToolbar

                        // Show the plant name if toolbar is shown
                        toolbarLayout.isTitleEnabled = shouldShowToolbar
                    }
                }
            )

            toolbar.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }

            toolbar.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_share -> {
                        createShareIntent()
                        true
                    }
                    else -> false
                }
            }
        }
        setHasOptionsMenu(true)

        return binding.root
    }


    fun interface Callback {
        fun add(plant: Plant?)
    }

    private fun navigateToGallery() {
        plantDetailViewModel.plant.value?.let { plant ->
            val direction =
                PlantDetailFragmentDirections.actionPlantDetailFragmentToGalleryFragment(plant.name)
            findNavController().navigate(direction)
        }
    }

    // Helper function for calling a share functionality.
    // Should be used when user presses a share button/menu item.
    @Suppress("DEPRECATION")
    private fun createShareIntent() {
        val shareText = plantDetailViewModel.plant.value.let { plant ->
            if (plant == null) {
                ""
            } else {
                getString(R.string.share_text_plant, plant.name)
            }
        }
        val shareIntent = ShareCompat.IntentBuilder.from(requireActivity())
            .setText(shareText)
            .setType("text/plain")
            .createChooserIntent()
            .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        startActivity(shareIntent)
    }

    // FloatingActionButtons anchored to AppBarLayouts have their visibility controlled by the scroll position.
    // We want to turn this behavior off to hide the FAB when it is clicked.
    //
    // This is adapted from Chris Banes' Stack Overflow answer: https://stackoverflow.com/a/41442923
    private fun hideAppBarFab(fab: FloatingActionButton) {
        val params = fab.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior as FloatingActionButton.Behavior
        behavior.isAutoHideEnabled = false
        fab.hide()
    }

}