package com.example.anyphoto.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
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
    lateinit var tvCancel: TextView
    private var query: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_searching_page, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        tvCancel = view.findViewById(R.id.tv_cancel)
        photos = ArrayList()
        page = 1
        rvSearch = view.findViewById(R.id.rv_search)
        noSearch = view.findViewById(R.id.no_search)
        etSearch = view.findViewById(R.id.et_search)

        tvCancel.setOnClickListener {
            hideKeyboard()
        }

        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rvSearch.setHasFixedSize(true)
        rvSearch.layoutManager = manager

        refreshAdapter()

        rvSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!rvSearch.canScrollVertically(RecyclerView.VERTICAL) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (query != "" && photos.size != 0 && adapter.items.size != 0) {
                        doRequestForLoadMorePhotos(query)
                    }
                }
            }
        })



        etSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(
                p0: TextView?,
                actionId: Int,
                keyEvent: KeyEvent?
            ): Boolean {
                if ((keyEvent != null && (keyEvent.keyCode == KeyEvent.KEYCODE_ENTER)))
                    return false
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    query = etSearch.text.toString()
                    hideKeyboard()
                    doRequestForPhotos(query)
                    return false
                }
                return false
            }

        })

    }

    private fun refreshAdapter() {
        adapter = RVHomePageAdapter(photos)
        rvSearch.adapter = adapter
    }

    private fun doRequestForPhotos(query: String) {
        MainActivity.progressBar.visibility = View.VISIBLE
        val previousSize = photos.size
        page = 2
        photos.removeAll(photos)
        adapter.notifyItemRangeRemoved(0, previousSize)

        RetrofitHttp.retrofitService.listSearchPhotos(1, query)!!
            .enqueue(object : Callback<SearchPhoto> {
                override fun onResponse(
                    call: Call<SearchPhoto>,
                    response: Response<SearchPhoto>
                ) {
                    val list = response.body()!!.results!!
                    photos.addAll(list)
                    adapter.notifyItemRangeInserted(0, list.size)
                    noSearch.visibility = View.INVISIBLE
                    MainActivity.progressBar.visibility = View.INVISIBLE
                    if (photos.size == 0) {
                        noSearch.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<SearchPhoto>, t: Throwable) {
                    Log.d("@@@James", "ERROR   $t")
                    noSearch.visibility = View.INVISIBLE
                }

            })
    }

    private fun doRequestForLoadMorePhotos(query: String) {
        MainActivity.progressBar.visibility = View.VISIBLE
        val previousSize = photos.size
        Toast.makeText(activity, "$page qidirilmoqda", Toast.LENGTH_SHORT).show()
        val tPage = page
        page++
        RetrofitHttp.retrofitService.listSearchPhotos(tPage, query)!!
            .enqueue(object : Callback<SearchPhoto> {
                override fun onResponse(
                    call: Call<SearchPhoto>,
                    response: Response<SearchPhoto>
                ) {
                    photos.addAll(response.body()!!.results!!)
                    adapter.notifyItemRangeInserted(previousSize, response.body()!!.results!!.size)
                    MainActivity.progressBar.visibility = View.INVISIBLE
                    if (photos.size == 0) {
                        noSearch.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<SearchPhoto>, t: Throwable) {
                    Log.d("@@@James", "ERROR   $t")
                    noSearch.visibility = View.INVISIBLE
                }

            })
    }

    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}