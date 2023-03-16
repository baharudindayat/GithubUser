package com.baharudindayat.githubuser.adapter

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.baharudindayat.githubuser.R
import com.baharudindayat.githubuser.apiservice.ItemsItem
import com.baharudindayat.githubuser.ui.DetailActivity
import com.bumptech.glide.Glide


class MainAdapter(private val listUser: List<ItemsItem>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val (name,profile) = listUser[position]
        viewHolder.tvItem.text = name
        Glide.with(viewHolder.itemView.context)
            .load(profile)
            .circleCrop()
            .into(viewHolder.ivProfile)

        viewHolder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(listUser[viewHolder.adapterPosition])
        }

        viewHolder.itemView.setOnClickListener{
            val intent = Intent(viewHolder.itemView.context,DetailActivity::class.java)
            intent.putExtra("USERNAME", listUser[viewHolder.position].login)
            viewHolder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = listUser.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItem: TextView = view.findViewById(R.id.tvItem)
        val ivProfile: ImageView = view.findViewById(R.id.ivProfile)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }
}