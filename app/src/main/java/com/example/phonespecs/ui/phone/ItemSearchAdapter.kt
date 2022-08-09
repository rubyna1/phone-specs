package com.example.phonespecs.ui.phone

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.phonespecs.R
import com.example.phonespecs.entity.Phones

class ItemSearchAdapter(
    val context: Context,
    private val listOfData: List<Phones>,
    private val listener: OnItemCallbacks
) :
    RecyclerView.Adapter<ItemSearchAdapter.ItemSearchDataViewHolder>() {
    class ItemSearchDataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.item_phone_name_text_view)
        val imageView: ImageView = view.findViewById(R.id.item_phone_image_view)
        val mainContainer: ConstraintLayout = view.findViewById(R.id.item_phone_main_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSearchDataViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_phone, parent, false)
        return ItemSearchDataViewHolder(view)
    }

    interface OnItemCallbacks {
        fun onItemClicked(phoneSlug: String)
    }

    override fun onBindViewHolder(
        holder: ItemSearchDataViewHolder,
        position: Int
    ) {
        val model = listOfData[position]
        holder.nameTextView.text = model.phoneName
        Glide.with(context).load(model.image).into(holder.imageView)
        holder.mainContainer.setOnClickListener {
            model.slug?.let { it1 -> listener.onItemClicked(it1) }
        }
    }

    override fun getItemCount(): Int {
        return listOfData.size
    }

}