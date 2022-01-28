package com.example.mohammad_jaha.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mohammad_jaha.R
import com.example.mohammad_jaha.databinding.DatabaseRvBinding
import com.example.mohammad_jaha.data.Data

class InformationRVAdapter(private val list: ArrayList<Data>) :
    RecyclerView.Adapter<InformationRVAdapter.ItemViewHolder>() {

    private lateinit var myListener: OnClickListener

    class ItemViewHolder(val binding: DatabaseRvBinding, listener: OnClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
            binding.deleteButton.setOnClickListener {
                listener.onDeleteClick(adapterPosition)
            }
        }
    }

    interface OnClickListener {
        fun onItemClick(position: Int)
        fun onDeleteClick(position: Int)
    }

    fun setOnClickListener(listener: OnClickListener) {
        myListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            DatabaseRvBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            myListener
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val information = list[position]

        holder.binding.apply {
            nameTV.text = information.name
            languageTV.text = information.language
            if (information.picture != null) {
                Glide.with(mainLay)
                    .load(information.picture)
                    .into(imageView3)
            }
            else{
                imageView3.setImageResource(R.drawable.update_ic_emoji)
            }
        }
    }

    override fun getItemCount() = list.size

    fun updateRVMain() {
        notifyDataSetChanged()
    }
}