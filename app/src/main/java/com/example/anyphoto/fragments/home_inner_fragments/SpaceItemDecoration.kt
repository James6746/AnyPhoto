package com.example.anyphoto.fragments.home_inner_fragments

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(private val mSpace: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {


        if(parent.getChildAdapterPosition(view) == 0 || parent.getChildAdapterPosition(view) == 1){

        }
    }
}