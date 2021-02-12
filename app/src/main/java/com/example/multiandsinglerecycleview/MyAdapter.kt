package com.example.multiandsinglerecycleview

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.multiandsinglerecycleview.MyAdapter.MyViewHolder
import java.util.*

class MyAdapter(private val context: Context, private val list: List<MyData>) : RecyclerView.Adapter<MyViewHolder>() {
    private var selectedIds: List<Int> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.adapter_item_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = list[position].title
        val id = list[position].id
        if (selectedIds.contains(id)) {
            //if item is selected then,set foreground color of FrameLayout.
            holder.rootView.foreground = ColorDrawable(ContextCompat.getColor(context, R.color.colorControlActivated))
        } else {
            //else remove selected item color.
            holder.rootView.foreground = ColorDrawable(ContextCompat.getColor(context, android.R.color.transparent))
        }
        holder.itemView.setOnClickListener{
            Toast.makeText(context, holder.title.text, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getItem(position: Int): MyData {
        return list[position]
    }

    fun setSelectedIds(selectedIds: List<Int>) {
        this.selectedIds = selectedIds
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var rootView: FrameLayout

        init {
            title = itemView.findViewById(R.id.title)
            rootView = itemView.findViewById(R.id.root_view)
        }
    }
}