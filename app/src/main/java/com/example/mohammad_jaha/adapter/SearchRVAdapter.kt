package com.example.mohammad_jaha.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mohammad_jaha.R
import com.example.mohammad_jaha.data.Data
import com.example.mohammad_jaha.databinding.SearchRvBinding

class SearchRVAdapter(private val list: ArrayList<Data>) :
    RecyclerView.Adapter<SearchRVAdapter.ItemViewHolder>() {

    private lateinit var myListener: OnItemClickListener

    class ItemViewHolder(val binding: SearchRvBinding, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        myListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            SearchRvBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            myListener
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val information = list[position]

        holder.binding.apply {
            nameTV.text = information.name
            if (information.picture != null) {
                Glide.with(mainLay)
                    .load(information.picture)
                    .into(imageView2)
            }
            else{
                imageView2.setImageResource(R.drawable.darktheme)
            }
        }
    }

    override fun getItemCount() = list.size

    fun update() {
        notifyDataSetChanged()
    }
}
