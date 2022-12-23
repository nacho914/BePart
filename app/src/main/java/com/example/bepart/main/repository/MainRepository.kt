package com.example.bepart.main.repository

class MainRepository(private val dataSource: MainDataSource) {
    suspend fun getAllInitiatives() = dataSource.getAllInitiatives()

    suspend fun addVote(initiativeKey: String, votersName: String) =
        dataSource.addVote(initiativeKey, votersName)

    suspend fun removeVote(initiativeKey: String, votersName: String) =
        dataSource.deleteVote(initiativeKey, votersName)
}
