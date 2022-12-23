package com.example.bepart.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bepart.COLLECTION_NAME
import com.example.bepart.domain.model.Initiatives
import com.example.bepart.domain.model.Result.*
import com.example.bepart.domain.use_case.InitiativeUseCase
import com.example.bepart.presentation.mappers.IniciativesMappers
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@Suppress("NAME_SHADOWING")
class MainViewModel(
    private val iniciativeUseCase: InitiativeUseCase
) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val initiativeMutableList = MutableLiveData<List<Initiatives>>()
    private val isLoading = MutableLiveData<Boolean>()

    init {
        getInitiatives()
        addLiveListener()
    }

    private fun getInitiatives() {
        coroutineScope.launch {
            iniciativeUseCase.getAllIniciatives()
                .onStart { isLoading.postValue(false) }
                .onCompletion { isLoading.postValue(false) }
                .collect{ result ->
                    when (result){
                        is Success -> {
                            val data = result.value
                            initiativeMutableList.postValue(data)
                            isLoading.postValue(false)
                        }
                        is Failure -> {
                            val error = result.reason
                        }
                    }
                }
        }
    }

    private fun addLiveListener() {
        val db = Firebase.firestore
        db.collection(COLLECTION_NAME)
            .addSnapshotListener { value, error ->
                isLoading.postValue(true)
                if (error != null) {
                    isLoading.postValue(false)
                    return@addSnapshotListener
                }
                value?.let { getLiveInitiatives(it) }
            }
    }

    private fun getLiveInitiatives(query: QuerySnapshot) {
        val initiativesList = ArrayList<Initiatives>()
        for (doc in query) {
            initiativesList.add(
                IniciativesMappers.fromLiveDataToInitiative(doc)
            )
        }
        initiativesList.sortByDescending { it.totalVotantes }
        initiativeMutableList.postValue(initiativesList)
        isLoading.postValue(false)
    }


fun getInitiativesList(): MutableLiveData<List<Initiatives>>{
        return initiativeMutableList
    }

    fun getStatus(): MutableLiveData<Boolean> {
        return isLoading
    }
}
