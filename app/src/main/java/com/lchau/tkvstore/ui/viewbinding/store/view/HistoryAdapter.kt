package com.lchau.tkvstore.ui.viewbinding.store.view

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lchau.tkvstore.databinding.ItemHistoryBinding

class HistoryAdapter : ListAdapter<HistoryView, HistoryAdapter.ViewHolder>(LIST_DIFF) {

    companion object {

        private val LIST_DIFF = object : DiffUtil.ItemCallback<HistoryView>() {
            override fun areItemsTheSame(oldItem: HistoryView, newItem: HistoryView): Boolean {
                return oldItem.content == newItem.content
            }

            override fun areContentsTheSame(
                oldItem: HistoryView,
                newItem: HistoryView
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HistoryView) {
            with(binding) {
                content.text = Html.fromHtml(item.content, HtmlCompat.FROM_HTML_MODE_LEGACY)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}