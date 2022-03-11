package com.example.anyphoto.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.anyphoto.R
import com.example.anyphoto.fragments.home_inner_fragments.HomeFragmentAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class HomePage : Fragment() {
    lateinit var tabHomePage: TabLayout
    lateinit var homeViewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_page, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        homeViewPager = view.findViewById(R.id.view_pager_home_page)
        operateViewPager()
        tabHomePage = view.findViewById(R.id.tab_home_page)
        operateTabLayout()
    }

    private fun operateViewPager() {
        val fragmentManager: FragmentManager = parentFragmentManager
        val fragmentAdapter =
            HomeFragmentAdapter(fragmentManager, lifecycle)
        homeViewPager.adapter = fragmentAdapter

    }

    private fun operateTabLayout() {
        tabHomePage.setTabTextColors(Color.parseColor("#FF000000"), Color.parseColor("#ffffff"));
        tabHomePage.addTab(tabHomePage.newTab().setText("All"))
        tabHomePage.addTab(tabHomePage.newTab().setText("Nature"))

        tabHomePage.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                homeViewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        homeViewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabHomePage.selectTab(tabHomePage.getTabAt(position))
            }
        })
    }
}