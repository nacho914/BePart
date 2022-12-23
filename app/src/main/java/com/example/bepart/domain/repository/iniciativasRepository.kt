package com.example.bepart.domain.repository

import com.example.bepart.domain.model.Initiatives
import kotlinx.coroutines.flow.Flow
import com.example.bepart.domain.model.Result

interface iniciativasRepository {

    fun getAllInitiatives(): Flow<Result<List<Initiatives>, Error>>

    suspend fun addIniciativatoFireStore(iniciativa: Initiatives) : Flow<Result<Initiatives, Error>>

    suspend fun deleteIniciativatoFireStore(id: Int) : Flow<Result<Initiatives, Error>>
}