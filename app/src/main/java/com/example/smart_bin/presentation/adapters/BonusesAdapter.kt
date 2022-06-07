package com.example.smart_bin.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smart_bin.databinding.BonusItemBinding
import com.example.smart_bin.presentation.model.Bonus


class BonusesAdapter : ListAdapter<Bonus, ViewHolder>(Comparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class Comparator : DiffUtil.ItemCallback<Bonus>() {
        override fun areItemsTheSame(oldItem: Bonus, newItem: Bonus): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Bonus, newItem: Bonus): Boolean {
            return oldItem.promocode == newItem.promocode
        }
    }
}

class ViewHolder(private val binding: BonusItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(current: Bonus) {
        with(binding) {
            bonusBodyText.text = current.body
            bonusCodeText.text = current.promocode
            if (current.is_used == true)
                isUsedImage.visibility = View.VISIBLE
            else
                isUsedImage.visibility = View.GONE
        }
    }

    companion object {
        fun create(parent: ViewGroup): ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = BonusItemBinding.inflate(inflater, parent, false)
            return ViewHolder(binding)
        }
    }
}