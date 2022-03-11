package com.example.anyphoto.fragments

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.anyphoto.R
import com.example.anyphoto.activities.MainActivity
import com.example.anyphoto.fragments.home_inner_fragments.RVHomePageAdapter
import com.example.anyphoto.model.photos.Photo
import com.example.anyphoto.model.search.SearchPhoto
import com.example.anyphoto.retrofit.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchingPage : Fragment() {
    lateinit var rvSearch: RecyclerView
    lateinit var noSearch: LinearLayout
    lateinit var etSearch: EditText
    lateinit var photos: ArrayList<Photo>
    lateinit var adapter: RVHomePageAdapter
    private var page: Int = 1
    lateinit var query: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_searching_page, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        photos = ArrayList()
        page = 1
        rvSearch = view.findViewById(R.id.rv_search)
        noSearch = view.findViewById(R.id.no_search)
        etSearch = view.findViewById(R.id.et_search)

        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rvSearch.setHasFixedSize(true)
        rvSearch.layoutManager = manager

        refreshAdapter()

        rvSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!rvSearch.canScrollVertically(RecyclerView.VERTICAL) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    doRequestForLoadMorePhotos(query, ++page)
                }
            }
        })

        etSearch.setOnEditorActionListener { _, actionId, keyEvent ->
            if ((keyEvent != null && (keyEvent.keyCode == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_SEARCH)) {
                query = etSearch.text.toString()
                doRequestForPhotos(query)
            }
            false
        }

    }

    private fun refreshAdapter() {
        adapter = RVHomePageAdapter(photos)
        rvSearch.adapter = adapter
    }

    private fun doRequestForPhotos(query: String) {
        MainActivity.progressBar.visibility = View.VISIBLE
        val previousSize = photos.size
        photos.removeAll(photos)
        page = 1
        adapter.notifyItemRangeRemoved(0, previousSize)

        RetrofitHttp.retrofitService.listSearchPhotos(1, query)!!
            .enqueue(object : Callback<SearchPhoto> {
                override fun onResponse(
                    call: Call<SearchPhoto>,
                    response: Response<SearchPhoto>
                ) {
                    photos.addAll(response.body()!!.results!!)
                    adapter.notifyItemRangeInserted(0, response.body()!!.results!!.size)
                    noSearch.visibility = View.INVISIBLE
                    MainActivity.progressBar.visibility = View.INVISIBLE
                }

                override fun onFailure(call: Call<SearchPhoto>, t: Throwable) {
                    Log.d("@@@James", "ERROR   $t")
                    noSearch.visibility = View.INVISIBLE
                }

            })
    }

    private fun doRequestForLoadMorePhotos(query: String, page: Int) {
        MainActivity.progressBar.visibility = View.VISIBLE
        val previousSize = photos.size

        RetrofitHttp.retrofitService.listSearchPhotos(page, query)!!
            .enqueue(object : Callback<SearchPhoto> {
                override fun onResponse(
                    call: Call<SearchPhoto>,
                    response: Response<SearchPhoto>
                ) {
                    photos.addAll(response.body()!!.results!!)
                    adapter.notifyItemRangeInserted(previousSize, response.body()!!.results!!.size)
                    MainActivity.progressBar.visibility = View.INVISIBLE
                }

                override fun onFailure(call: Call<SearchPhoto>, t: Throwable) {
                    Log.d("@@@James", "ERROR   $t")
                    noSearch.visibility = View.INVISIBLE
                }

            })
    }

}