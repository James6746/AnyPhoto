package com.example.anyphoto.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.anyphoto.R
import com.example.anyphoto.fragments.HomePage
import com.example.anyphoto.fragments.MessagingPage
import com.example.anyphoto.fragments.ProfilePage
import com.example.anyphoto.fragments.SearchingPage
import com.example.anyphoto.fragments.home_inner_fragments.RVHomePageAdapter
import com.example.anyphoto.model.photos.Photo
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : BaseActivity() {
    lateinit var navBar: BottomNavigationView
    lateinit var mainFrameLayout: FrameLayout

    companion object{
        lateinit  var progressBar: ProgressBar
        var PAGE = 1
        var adapter: RVHomePageAdapter? = null
        var photos: ArrayList<Photo>? = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

    }

    private fun initViews() {
        progressBar = findViewById(R.id.progressBar)
        navBar = findViewById(R.id.nav_bar);
        mainFrameLayout = findViewById(R.id.main_frame_layout)
        progressBar.visibility = View.INVISIBLE

        var fragment: Fragment = HomePage()
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame_layout, fragment!!).commit()


        var badge = navBar.getOrCreateBadge(R.id.meassage)
        badge.isVisible = true
        badge.number = 7

        navBar.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.home -> fragment = HomePage()
                    R.id.search -> fragment = SearchingPage()
                    R.id.meassage -> fragment = MessagingPage()
                    R.id.profile -> fragment = ProfilePage()
                }

                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frame_layout, fragment!!).commit()

                return true
            }

        })
    }
}