package com.example.anyphoto.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.anyphoto.R
import com.example.anyphoto.activities.MainActivity
import com.example.anyphoto.fragments.home_inner_fragments.HomeFragmentAdapter
import com.example.anyphoto.fragments.home_inner_fragments.RVHomePageAdapter
import com.example.anyphoto.model.photos.Photo
import com.example.anyphoto.retrofit.RetrofitHttp
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePage : Fragment() {
    lateinit var tabHomePage: TabLayout
    lateinit var homeViewPager: ViewPager2
    var photos: ArrayList<Photo>? = null
    lateinit var bottomReachedListener: RVHomePageAdapter.BottomReachedListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_page, container, false)

        initViews(view)
        return view
    }


    private fun initViews(view: View) {
        photos = MainActivity.photos!!

        bottomReachedListener = object : RVHomePageAdapter.BottomReachedListener {
            override fun onBottomReachedListener(position: Int) {
                val page = ++MainActivity.PAGE
                doRequestForLoadMorePhotos(page)
            }
        }
        homeViewPager = view.findViewById(R.id.view_pager_home_page)
        if (photos!!.size == 0) {
            doRequestForPhotos()
        } else {
            operateViewPager()
        }
        tabHomePage = view.findViewById(R.id.tab_home_page)
        operateTabLayout()
    }

    private fun operateViewPager() {
        MainActivity.progressBar.visibility = View.INVISIBLE
        val fragmentManager: FragmentManager = parentFragmentManager
        val fragmentAdapter =
            HomeFragmentAdapter(fragmentManager, lifecycle, bottomReachedListener)
        homeViewPager.adapter = fragmentAdapter

    }

    private fun operateTabLayout() {
        tabHomePage.setTabTextColors(Color.parseColor("#FF000000"), Color.parseColor("#ffffff"));
        tabHomePage.addTab(tabHomePage.newTab().setText("All"))

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

    private fun doRequestForPhotos(): ArrayList<Photo>? {
        MainActivity.progressBar.visibility = View.VISIBLE
        RetrofitHttp.retrofitService.listPost("photos")!!.enqueue(object : Callback<ArrayList<Photo>> {
            override fun onResponse(
                call: Call<ArrayList<Photo>>,
                response: Response<ArrayList<Photo>>
            ) {
                val list: ArrayList<Photo> = (response.body()!!)
                photos!!.addAll(list)
                Log.d("@@@James", "${response.body()}")
                operateViewPager()
            }

            override fun onFailure(call: Call<ArrayList<Photo>>, t: Throwable) {
                Log.d("@@@James", "ERROR   $t")
            }

        })
        return photos
    }

    private fun doRequestForLoadMorePhotos(page: Int): ArrayList<Photo>? {
        MainActivity.progressBar.visibility = View.VISIBLE
        RetrofitHttp.retrofitService.listPost(page)!!.enqueue(object : Callback<ArrayList<Photo>> {
            override fun onResponse(
                call: Call<ArrayList<Photo>>,
                response: Response<ArrayList<Photo>>
            ) {
                val temp = photos!!.size
                val list: ArrayList<Photo> = (response.body()!!)
                photos!!.addAll(list)
                MainActivity.adapter!!.notifyItemRangeInserted(temp, photos!!.size - 1)
                MainActivity.progressBar.visibility = View.INVISIBLE
            }

            override fun onFailure(call: Call<ArrayList<Photo>>, t: Throwable) {
                Log.d("@@@James", "ERROR   $t")
            }

        })
        return photos
    }
}