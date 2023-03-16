package com.baharudindayat.githubuser.adapter


import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.baharudindayat.githubuser.R
import com.baharudindayat.githubuser.apiservice.FollowersItem
import com.bumptech.glide.Glide


class FollowerAdapter(private val listUser: List<FollowersItem>) : RecyclerView.Adapter<FollowerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val (name,profile) = listUser[position]
        viewHolder.tvItem.text = name
        Glide.with(viewHolder.itemView.context)
            .load(profile)
            .circleCrop()
            .into(viewHolder.ivProfile)

    }

    override fun getItemCount() = listUser.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItem: TextView = view.findViewById(R.id.tvItem)
        val ivProfile: ImageView = view.findViewById(R.id.ivProfile)
    }

}