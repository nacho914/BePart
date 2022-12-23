package com.example.bepart.domain.model

data class Initiatives(
    val nombre: String,
    val categoria: String,
    val creador: String,
    val descripcion: String,
    val totalVotantes: Int,
    val id: String = "1",
    val voters: List<String> = emptyList()
)
