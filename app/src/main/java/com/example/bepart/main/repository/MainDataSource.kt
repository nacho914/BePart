package com.example.bepart.main.repository

import com.example.bepart.main.model.Initiatives

interface MainDataSource {

    suspend fun getAllInitiatives(): List<Initiatives>

    suspend fun addVote(initiativeKey: String, votersName: String)

    suspend fun deleteVote(initiativeKey: String, votersName: String)
}
