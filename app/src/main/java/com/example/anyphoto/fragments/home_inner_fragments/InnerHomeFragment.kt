package com.example.anyphoto.fragments.home_inner_fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.anyphoto.R
import com.example.anyphoto.activities.MainActivity
import com.example.anyphoto.model.photos.Photo
import com.example.anyphoto.retrofit.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InnerHomeFragment(
    var bottomReacheListener: RVHomePageAdapter.BottomReachedListener
) :
    Fragment() {
    private lateinit var rvHome: RecyclerView
    lateinit var photos: ArrayList<Photo>
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_inner_home, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        photos = MainActivity.photos!!
        rvHome = view.findViewById(R.id.rv_home)
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//        manager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        rvHome.setHasFixedSize(true)
        rvHome.layoutManager = manager

        if (photos != null) {
            refreshAdapter(photos)
        }

        swipeRefreshLayout.setOnRefreshListener{
           doRequestForPhotos()
            swipeRefreshLayout.isRefreshing = false

        }
    }

    private fun refreshAdapter(photos: ArrayList<Photo>) {
        MainActivity.adapter = RVHomePageAdapter(photos, bottomReacheListener)
        rvHome.adapter = MainActivity.adapter
    }

    private fun doRequestForPhotos(): ArrayList<Photo> {
        Log.d("@@@James", "SwipeRefresh Chaqirildi")
        MainActivity.photos!!.clear()
        MainActivity.PAGE = 1

        MainActivity.progressBar.visibility = View.VISIBLE
        RetrofitHttp.retrofitService.listPost("photos")!!.enqueue(object : Callback<ArrayList<Photo>> {
            override fun onResponse(
                call: Call<ArrayList<Photo>>,
                response: Response<ArrayList<Photo>>
            ) {
                val list: ArrayList<Photo> = (response.body()!!)
                MainActivity.photos!!.addAll(list)
                MainActivity.adapter!!.notifyItemRangeInserted(MainActivity.photos!!.size, list.size)
                MainActivity.progressBar.visibility = View.INVISIBLE
            }

            override fun onFailure(call: Call<ArrayList<Photo>>, t: Throwable) {
                Log.d("@@@James", "ERROR   $t")
            }

        })
        return photos
    }
}