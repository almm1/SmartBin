package com.example.smart_bin.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smart_bin.databinding.NewsItemListBinding
import com.example.smart_bin.presentation.model.News


class NewsAdapter(private val context: Context) :
    PagingDataAdapter<News, NewsAdapter.ViewHolder>(PassengersComparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            NewsItemListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindPassenger(it) }
    }

    inner class ViewHolder(private val binding: NewsItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindPassenger(item: News) = with(binding) {
            titleText.text = item.title
            datePubText.text=item.dataPub
            sourceText.text = item.source
            Glide.with(context)
                .load(item.imageLink)
                .into(logoImage)
        }
    }

    object PassengersComparator : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }
    }
}