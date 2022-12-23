package com.example.bepart.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.bepart.domain.model.Initiatives
import com.example.bepart.presentation.viewHolders.IniciativasViewHolder
import com.example.bepart.presentation.MainActivityActions

class InitiativeAdapter(
    var items: MutableList<Initiatives>,
    var actions: MainActivityActions
) : ListAdapter<Initiatives, IniciativasViewHolder>(TaskDiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IniciativasViewHolder {
        val binding = com.example.bepart.databinding.ItemIniciativasBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IniciativasViewHolder(binding, actions)
    }

    override fun onBindViewHolder(holder: IniciativasViewHolder, position: Int) {
        with(items[position]) {
            holder.bind(this)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    object TaskDiffCallBack : DiffUtil.ItemCallback<Initiatives>() {

        override fun areItemsTheSame(oldItem: Initiatives, newItem: Initiatives): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Initiatives, newItem: Initiatives): Boolean =
            oldItem.nombre == newItem.nombre
    }
}
