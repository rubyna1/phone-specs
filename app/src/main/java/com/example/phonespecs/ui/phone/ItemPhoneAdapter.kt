package com.example.phonespecs.ui.phone

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.phonespecs.R
import com.example.phonespecs.entity.Phones

val DIFF_UTILS = object : DiffUtil.ItemCallback<Phones>() {
    override fun areItemsTheSame(oldItem: Phones, newItem: Phones): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Phones, newItem: Phones): Boolean {
        return oldItem == newItem
    }

}

class ItemPhoneDataAdapter(val context: Context, private val listener: OnItemCallbacks) :
    PagedListAdapter<Phones, ItemPhoneDataAdapter.ItemPhoneDataViewHolder>(DIFF_UTILS) {
    class ItemPhoneDataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.item_phone_name_text_view)
        val imageView: ImageView = view.findViewById(R.id.item_phone_image_view)
        val mainContainer: ConstraintLayout = view.findViewById(R.id.item_phone_main_view)
    }

    override fun onBindViewHolder(holder: ItemPhoneDataViewHolder, position: Int) {
        val model = getItem(position)
        if (model != null) {
            holder.nameTextView.text = model.phoneName
            Glide.with(context).load(model.image).into(holder.imageView)
            holder.mainContainer.setOnClickListener {
                model.slug?.let { it1 -> listener.onItemClicked(it1) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPhoneDataViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_phone, parent, false)
        return ItemPhoneDataViewHolder(view)
    }

    interface OnItemCallbacks {
        fun onItemClicked(phoneSlug: String)
    }
}