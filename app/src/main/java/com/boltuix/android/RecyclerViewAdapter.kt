package com.boltuix.android

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.boltuix.android.databinding.RecyclerViewDesignBinding

class RecyclerViewAdapterViewHolder(val bindingDesign: RecyclerViewDesignBinding) : RecyclerView.ViewHolder(bindingDesign.root)

class RecyclerViewAdapter(private val event: (RecyclerViewDesignBinding, RecyclerViewModel) -> Unit) : RecyclerView.Adapter<RecyclerViewAdapterViewHolder>() {
    lateinit var context : Context
    private val itemDifferCallback = object: DiffUtil.ItemCallback<RecyclerViewModel>(){
        override fun areItemsTheSame(oldItem: RecyclerViewModel, newItem: RecyclerViewModel): Boolean {
            return oldItem.label == newItem.label
        }

        override fun areContentsTheSame(oldItem: RecyclerViewModel, newItem: RecyclerViewModel): Boolean {
            return oldItem == newItem
        }

    }
    // to do job asynchronously
    private val itemDiffer = AsyncListDiffer(this, itemDifferCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapterViewHolder {
        context = parent.context
        return RecyclerViewAdapterViewHolder(
            RecyclerViewDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapterViewHolder, position: Int) {
        val currentItem = itemDiffer.currentList[position]
        with(holder) {
            Log.d("s1001","::: onBindViewHolder")
            bindingDesign.apply {

                titleText.text = currentItem.label

                descriptionText.text = currentItem.description

               // imageViewCircleWithStroke.setImageResource(currentItem.drawable!!)

                cardView.setOnClickListener {
                    event(holder.bindingDesign,currentItem)
                }
            }
        }
    }

    override fun getItemCount(): Int = itemDiffer.currentList.size
    fun submitList(list: List<RecyclerViewModel?>?) {
        itemDiffer.submitList(list)
    }

}