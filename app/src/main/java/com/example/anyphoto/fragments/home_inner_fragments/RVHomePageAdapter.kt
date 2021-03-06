package com.example.anyphoto.fragments.home_inner_fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.anyphoto.R
import com.example.anyphoto.activities.MyApplication
import com.example.anyphoto.model.photos.Photo
import com.google.android.material.imageview.ShapeableImageView
import java.util.*
import kotlin.collections.ArrayList

class RVHomePageAdapter(
    var items: ArrayList<Photo>,
    var bottomReachedListener: BottomReachedListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_page_image_item, parent, false)
        return RVHomePageViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(position == (items.size - 4)){
            bottomReachedListener.onBottomReachedListener(position)
        }

        if (holder is RVHomePageViewHolder) {
            val photo: Photo = items[position]
            Glide.with(MyApplication.context).load(photo.urls!!.small).into(holder.photo)

            Glide.with(MyApplication.context).load(photo.user!!.profileImage!!.medium).transition(
                DrawableTransitionOptions.withCrossFade()
            ).apply(
                RequestOptions()
                    .placeholder(R.drawable.photo)
                    .centerCrop()
                    .transforms(CenterCrop(), RoundedCorners(1000))
            ).into(holder.profilePhoto)
            holder.fullname.text = photo.user.username

            holder.btnMore.setOnClickListener {

            }

            holder.btnUser.setOnClickListener {

            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class RVHomePageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var photo: ShapeableImageView
        var profilePhoto: ShapeableImageView
        var fullname: TextView
        var btnMore: ImageView
        var btnUser: LinearLayout

        init {
            photo = view.findViewById(R.id.shiv_photo)
            profilePhoto = view.findViewById(R.id.shiv_profile_photo)
            fullname = view.findViewById(R.id.tv_fullname)
            btnMore = view.findViewById(R.id.btn_more)
            btnUser = view.findViewById(R.id.ll_user)
        }
    }

    public interface BottomReachedListener {
        fun onBottomReachedListener(position: Int)
    }
}