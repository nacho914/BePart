package com.example.bepart.domain.repository

import com.example.bepart.domain.model.Initiatives
import com.example.bepart.domain.repository.MainDataSource

class MainRepository(private val dataSource: MainDataSource) {
    suspend fun getAllInitiatives() = dataSource.getAllInitiatives()

    suspend fun addVote(initiativeKey: String, votersName: String) =
        dataSource.addVote(initiativeKey, votersName)

    suspend fun removeVote(initiativeKey: String, votersName: String) =
        dataSource.deleteVote(initiativeKey, votersName)

    suspend fun addIniciative(iniciativa: Initiatives) = dataSource.addIniciativatoFireStore(iniciativa)
}

