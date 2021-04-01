package raum.muchbeer.flowktx.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import raum.muchbeer.flowktx.fragment.GardenFragment
import raum.muchbeer.flowktx.fragment.PlantListFragment
import java.lang.IndexOutOfBoundsException

const val MY_GARDEN_PAGE_INDEX = 0
const val PLANT_LIST_PAGE_INDEX = 1

class SunFlowerPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    /**
     * Mapping of the ViewPager page indexes to their respective Fragments
     */
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        MY_GARDEN_PAGE_INDEX to { GardenFragment() },
        PLANT_LIST_PAGE_INDEX to { PlantListFragment() }
    )

    override fun getItemCount(): Int = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
       return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}