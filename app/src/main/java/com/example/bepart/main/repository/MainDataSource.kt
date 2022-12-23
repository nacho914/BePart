package com.example.bepart.main.repository

import com.example.bepart.domain.model.Initiatives
import com.example.bepart.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface MainDataSource {

    suspend fun getAllInitiatives():  Flow<Result<List<Initiatives>, Error>>

    suspend fun addVote(initiativeKey: String, votersName: String)

    suspend fun deleteVote(initiativeKey: String, votersName: String)

    suspend fun addIniciativatoFireStore(iniciativa: Initiatives) : Flow<Result<String, Error>>
}

