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

class InnerHomeFragment(var photoType: String) : Fragment() {
    private var collectionId: String? = null
    private var isTopic: Boolean = false
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var rvHome: RecyclerView
    private var photos: ArrayList<Photo>? = null
    private var page: Int = 1
    private lateinit var adapter: RVHomePageAdapter

    constructor(collectionId: String, isTopic: Boolean) : this("") {
        this.collectionId = collectionId
        this.isTopic = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("@@@James", "OncreateInner")
        val view = inflater.inflate(R.layout.fragment_inner_home, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {

        MainActivity.PAGE = 1
        MainActivity.photos!!.clear()
        photos = MainActivity.photos!!
//        photos = ArrayList()
//        photos!!.clear()

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        rvHome = view.findViewById(R.id.rv_home)
        rvHome.setHasFixedSize(true)
        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//        manager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        rvHome.layoutManager = manager
        rvHome.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!rvHome.canScrollVertically(RecyclerView.VERTICAL) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    var page = ++MainActivity.PAGE
                    if (isTopic) {
                        doRequestForLoadMoreTopicPhotos(collectionId!!, ++page)
                    } else {
                        doRequestForLoadMorePhotos(photoType, ++page)
                    }
                }
            }

        })

        refreshAdapter(photos!!)
        swipeRefreshLayout.setOnRefreshListener {
            if (isTopic) {
                doRequestForTopicPhotos(collectionId!!)
            } else {
                doRequestForPhotos(photoType)
            }
            swipeRefreshLayout.isRefreshing = false
        }

        if (isTopic) {
            doRequestForTopicPhotos(collectionId!!)
        } else {
            doRequestForPhotos(photoType)
        }
    }

    private fun refreshAdapter(photos: ArrayList<Photo>) {
        MainActivity.adapter = RVHomePageAdapter(photos)
//        adapter = RVHomePageAdapter(photos, bottomReachedListener)
        rvHome.adapter = MainActivity.adapter
//        rvHome.adapter = adapter
    }

    private fun doRequestForPhotos(photoType: String) {
        MainActivity.progressBar.visibility = View.VISIBLE
        MainActivity.photos!!.clear()
        MainActivity.PAGE = 1
//        photos!!.clear()
//        page = 1

        RetrofitHttp.retrofitService.listPost(photoType, MainActivity.PAGE)!!
            .enqueue(object : Callback<ArrayList<Photo>> {
                override fun onResponse(
                    call: Call<ArrayList<Photo>>,
                    response: Response<ArrayList<Photo>>
                ) {
                    MainActivity.photos!!.addAll(response.body()!!)
//                    photos!!.addAll(response.body()!!)
                    MainActivity.adapter!!.notifyItemRangeInserted(
                        MainActivity.photos!!.size,
                        response.body()!!.size
                    )
//                    adapter.notifyItemRangeInserted(0, response.body()!!.size - 1)
//                    refreshAdapter(MainActivity.photos!!)
//                    refreshAdapter(photos!!)
                    MainActivity.progressBar.visibility = View.INVISIBLE
                }

                override fun onFailure(call: Call<ArrayList<Photo>>, t: Throwable) {
                    Log.d("@@@James", "ERROR   $t")
                }
            })
    }

    private fun doRequestForTopicPhotos(collectionId: String) {
        MainActivity.progressBar.visibility = View.VISIBLE
        MainActivity.photos!!.clear()
        MainActivity.PAGE = 1
//        photos!!.clear()
//        page = 1

        RetrofitHttp.retrofitService.listTopicPost(collectionId, MainActivity.PAGE)!!
            .enqueue(object : Callback<ArrayList<Photo>> {
                override fun onResponse(
                    call: Call<ArrayList<Photo>>,
                    response: Response<ArrayList<Photo>>
                ) {
                    MainActivity.photos!!.addAll(response.body()!!)
                    Log.d("@@@James", response.body().toString())
//                    photos!!.addAll(response.body()!!)
                    MainActivity.adapter!!.notifyItemRangeInserted(
                        MainActivity.photos!!.size,
                        response.body()!!.size
                    )
//                    adapter.notifyItemRangeInserted(0, response.body()!!.size)
                    refreshAdapter(MainActivity.photos!!)
//                    refreshAdapter(photos!!)
                    MainActivity.progressBar.visibility = View.INVISIBLE
                }

                override fun onFailure(call: Call<ArrayList<Photo>>, t: Throwable) {
                    Log.d("@@@James", "ERROR   $t")
                }
            })
    }

    private fun doRequestForLoadMorePhotos(photoType: String, page: Int) {
        MainActivity.progressBar.visibility = View.VISIBLE

        RetrofitHttp.retrofitService.listPost(photoType, page)!!
            .enqueue(object : Callback<ArrayList<Photo>> {
                override fun onResponse(
                    call: Call<ArrayList<Photo>>,
                    response: Response<ArrayList<Photo>>
                ) {

                    MainActivity.photos!!.addAll(response.body()!!)
                    MainActivity.adapter!!.notifyItemRangeInserted(
                        MainActivity.photos!!.size,
                        response.body()!!.size
                    )
//                    photos!!.addAll(response.body()!!)
//                    adapter.notifyItemRangeInserted(position + 1, response.body()!!.size)
                    MainActivity.progressBar.visibility = View.INVISIBLE
                }

                override fun onFailure(call: Call<ArrayList<Photo>>, t: Throwable) {
                    Log.d("@@@James", "ERROR   $t")
                }

            })
    }

    private fun doRequestForLoadMoreTopicPhotos(collectionId: String, page: Int) {
        MainActivity.progressBar.visibility = View.VISIBLE

        RetrofitHttp.retrofitService.listTopicPost(collectionId, page)!!
            .enqueue(object : Callback<ArrayList<Photo>> {
                override fun onResponse(
                    call: Call<ArrayList<Photo>>,
                    response: Response<ArrayList<Photo>>
                ) {
                    MainActivity.photos!!.addAll(response.body()!!)
                    MainActivity.adapter!!.notifyItemRangeInserted(
                        MainActivity.photos!!.size,
                        response.body()!!.size
                    )
//                    photos!!.addAll(response.body()!!)
//                    adapter.notifyItemRangeInserted(position + 1, response.body()!!.size)
                    MainActivity.progressBar.visibility = View.INVISIBLE
                }

                override fun onFailure(call: Call<ArrayList<Photo>>, t: Throwable) {
                    Log.d("@@@James", "ERROR   $t")
                }

            })
    }
}