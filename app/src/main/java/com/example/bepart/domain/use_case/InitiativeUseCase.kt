package com.example.bepart.domain.use_case

import com.example.bepart.domain.model.Initiatives
import com.example.bepart.domain.model.Result
import com.example.bepart.data.repository.MainFirebaseDataSourceImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn

class InitiativeUseCase(
    private val mainDataSource: MainFirebaseDataSourceImpl,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {

    suspend fun addIniciativa(iniciativa : Initiatives):Flow<Result<String, Error>>{
        return mainDataSource.addIniciativatoFireStore(iniciativa)
            .flowOn(dispatcher)
            .conflate()
    }

    suspend fun getAllIniciatives(): Flow<Result<List<Initiatives>, Error>>{
        return mainDataSource.getAllInitiatives()
    }

}