package com.example.ardogame

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ardogame.databinding.ItemInBinding

class InAdapter(
    var lines: List<InItem>
) : RecyclerView.Adapter<InAdapter.InViewHolder>() {

    inner class InViewHolder(val binding: ItemInBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InViewHolder {
        val binding = ItemInBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: InViewHolder,
        position: Int
    ) {
        holder.binding.tvTitle.text = lines[position].text
    }

    override fun getItemCount(): Int {
        return lines.size
    }
}