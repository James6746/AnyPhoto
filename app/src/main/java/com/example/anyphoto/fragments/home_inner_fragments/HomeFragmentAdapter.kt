package com.example.anyphoto.fragments.home_inner_fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.anyphoto.model.photos.Photo


class HomeFragmentAdapter(
    fm: FragmentManager,
    lifecycle: Lifecycle,
    var bottomReachedListener: RVHomePageAdapter.BottomReachedListener
) :
    FragmentStateAdapter(fm, lifecycle) {



    override fun getItemCount(): Int {
        return 1
    }

    override fun createFragment(position: Int): Fragment {
        var innerHomeFragment: Fragment? = null

        when (position) {
            0 -> innerHomeFragment = InnerHomeFragment(bottomReachedListener)
            1 -> innerHomeFragment = InnerHomeFragment(bottomReachedListener)
//            2 -> innerHomeFragment = InnerHomeFragment(null)
//            3 -> innerHomeFragment = InnerHomeFragment(null)
//            4 -> innerHomeFragment = InnerHomeFragment(null)
//            5 -> innerHomeFragment = InnerHomeFragment(null)
//            6 -> innerHomeFragment = InnerHomeFragment(null)
//            7 -> innerHomeFragment = InnerHomeFragment(null)
//            8 -> innerHomeFragment = InnerHomeFragment(null)
//            9 -> innerHomeFragment = InnerHomeFragment(null)
        }
//        Log.d("@@@James", "HomeFragmentAdapter ${doRequestForPhotos()?.size}" )


        return innerHomeFragment!!
    }

}