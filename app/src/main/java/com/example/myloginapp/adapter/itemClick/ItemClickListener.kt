package com.example.myloginapp.adapter.itemClick

import android.view.View
import androidx.recyclerview.widget.RecyclerView


interface ItemClickListener {
    fun onItemClicked(position: Int, view: View)
}

//Extensions
fun RecyclerView.addOnItemClickListener(onClickListener: ItemClickListener) {
    this.addOnChildAttachStateChangeListener(object :
        RecyclerView.OnChildAttachStateChangeListener {
        override fun onChildViewDetachedFromWindow(view: View) {
            view.setOnClickListener(null)
        }

        override fun onChildViewAttachedToWindow(view: View) {
            view.setOnClickListener {
                val holder = getChildViewHolder(view)
                onClickListener.onItemClicked(holder.adapterPosition, view)
            }
        }
    })
}