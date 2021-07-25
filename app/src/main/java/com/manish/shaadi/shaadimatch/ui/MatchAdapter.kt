package com.manish.shaadi.shaadimatch.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.manish.shaadi.R
import com.manish.shaadi.databinding.MatchItemLayoutBinding
import com.manish.shaadi.helper.AppExecutors
import com.manish.shaadi.helper.DataBoundListAdapter
import com.manish.shaadi.shaadimatch.data.MatchData

class MatchAdapter(
    val context: Context,
    val executors: AppExecutors,
    val callback: ((String, MatchData, Int) -> Unit)?,
    val dataBindingComponent: DataBindingComponent
) : DataBoundListAdapter<MatchData, MatchItemLayoutBinding>(executors,
    object : DiffUtil.ItemCallback<MatchData>() {
        override fun areItemsTheSame(oldItem: MatchData, newItem: MatchData): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: MatchData, newItem: MatchData): Boolean {
            return oldItem == newItem
        }
    }
) {
    override fun createBinding(parent: ViewGroup): MatchItemLayoutBinding {
        return DataBindingUtil
            .inflate(
                LayoutInflater.from(parent.context),
                R.layout.match_item_layout,
                parent,
                false,
                dataBindingComponent
            )
    }

    override fun bind(binding: MatchItemLayoutBinding, item: MatchData, position: Int) {
        binding.data = item
        Glide.with(context).load(item.picture?.large)
            .into(binding.profileImg)

        binding.close.setOnClickListener {
            callback?.invoke("decline", item, position)
        }
        binding.check.setOnClickListener {
            callback?.invoke("accept", item, position)
        }

    }

}