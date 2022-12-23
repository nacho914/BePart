package com.example.bepart.data.repository

import com.example.bepart.domain.model.Initiatives
import com.example.bepart.domain.model.Result
import com.example.bepart.domain.repository.iniciativasRepository
import kotlinx.coroutines.flow.Flow

class iniciativaServiceImpl(): iniciativasRepository {

    override fun getAllInitiatives(): Flow<Result<List<Initiatives>, Error>>{
        TODO("Not yet implemented")
    }

    override suspend fun addIniciativatoFireStore(iniciativa: Initiatives): Flow<Result<Initiatives, Error>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteIniciativatoFireStore(id: Int): Flow<Result<Initiatives, Error>> {
        TODO("Not yet implemented")
    }
}