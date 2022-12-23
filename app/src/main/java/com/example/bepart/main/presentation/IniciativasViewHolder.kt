package com.example.bepart.main.presentation

import androidx.recyclerview.widget.RecyclerView
import com.example.bepart.databinding.ItemIniciativasBinding
import com.example.bepart.main.model.Initiatives

class IniciativasViewHolder(
    private val binding: ItemIniciativasBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        iniciativas: Initiatives
    ) {
        binding.nameText.text = iniciativas.nombre
        binding.categoryText.text = iniciativas.categoria
        binding.descriptionText.text = iniciativas.descripcion
        binding.votesText.text = iniciativas.totalVotantes.toString()
    }
}
