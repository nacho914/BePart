package com.example.bepart.presentation.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.example.bepart.databinding.ItemIniciativasBinding
import com.example.bepart.domain.model.Initiatives
import com.example.bepart.presentation.MainActivityActions

class IniciativasViewHolder(
    private val binding: ItemIniciativasBinding,
    private var actions: MainActivityActions
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        initiatives: Initiatives
    ) {
        binding.nameText.text = initiatives.nombre
        binding.categoryText.text = initiatives.categoria
        binding.descriptionText.text = initiatives.descripcion
        binding.votesText.text = initiatives.totalVotantes.toString()
        setCardViewListener(initiatives.id)
    }

    private fun setCardViewListener(key: String) {
        binding.itemCardView.setOnClickListener {
            actions.openInitiativeDetailActivity(key)
        }
    }
}
