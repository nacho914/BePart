package com.example.bepart.domain.use_case

import com.example.bepart.domain.model.Initiatives
import com.example.bepart.domain.model.Result
import com.example.bepart.domain.repository.iniciativasRepository
import com.example.bepart.main.db.MainFirebaseDataSource
import com.example.bepart.main.repository.MainDataSource
import com.example.bepart.main.repository.MainRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

class iniciativeUseCase(
    private val mainDataSource: MainFirebaseDataSource,
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