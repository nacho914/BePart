package com.example.bepart.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bepart.COLLECTION_NAME
import com.example.bepart.Utils
import com.example.bepart.main.db.MainFirebaseDataSource
import com.example.bepart.main.model.Initiatives
import com.example.bepart.main.repository.MainRepository
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("NAME_SHADOWING")
class MainViewModel : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val initiativeMutableList = MutableLiveData<List<Initiatives>>()
    private val mainRepository = MainRepository(MainFirebaseDataSource())
    private val isLoading = MutableLiveData<Boolean>()

    init {
        getInitiatives()
        addLiveListener()
    }

    private fun getInitiatives() {
        isLoading.postValue(true)
        coroutineScope.launch {
            initiativeMutableList.postValue(
                mainRepository.getAllInitiatives()
            )
            isLoading.postValue(false)
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
                Utils.fromLiveDataToInitiative(doc)
            )
        }
        initiativesList.sortByDescending { it.totalVotantes }
        initiativeMutableList.postValue(initiativesList)
        isLoading.postValue(false)
    }

    fun getInitiativesList(): MutableLiveData<List<Initiatives>> {
        return initiativeMutableList
    }

    fun getStatus(): MutableLiveData<Boolean> {
        return isLoading
    }
}
