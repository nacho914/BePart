package com.example.bepart.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bepart.COLLECTION_NAME
import com.example.bepart.main.db.MainFirebaseDataSource
import com.example.bepart.domain.model.Initiatives
import com.example.bepart.main.repository.MainRepository
import com.example.bepart.presentation.IniciativesMappers
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailInitiativeViewModel : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val initiativeMutable = MutableLiveData<Initiatives>()
    private val isLoading = MutableLiveData<Boolean>()
    private val voted = MutableLiveData<Boolean>()
    private val mainRepository = MainRepository(MainFirebaseDataSource())

    fun addLiveListener(key: String, userId: String) {
        val db = Firebase.firestore
        db.collection(COLLECTION_NAME).document(key)
            .addSnapshotListener { value, error ->
                isLoading.postValue(true)
                if (error != null) {
                    isLoading.postValue(false)
                    return@addSnapshotListener
                }
                value?.let {
                    val initiative = IniciativesMappers.fromLiveDataToInitiative(it)
                    voted.postValue(voted(initiative, userId))
                    isLoading.postValue(false)
                    initiativeMutable.postValue(
                        initiative
                    )
                }
            }
    }

    private fun voted(initiatives: Initiatives, userId: String): Boolean =
        initiatives.voters.contains(userId)

    fun addVote(
        initiativeKey: String,
        votersName: String
    ) {
        isLoading.postValue(true)
        coroutineScope.launch {
            mainRepository.addVote(initiativeKey, votersName)
            isLoading.postValue(false)
        }
    }

    fun removeVote(
        initiativeKey: String,
        votersName: String
    ) {
        isLoading.postValue(true)
        coroutineScope.launch {
            mainRepository.removeVote(initiativeKey, votersName)
            isLoading.postValue(false)
        }
    }

    fun getInitiative(): MutableLiveData<Initiatives> {
        return initiativeMutable
    }

    fun getStatus(): MutableLiveData<Boolean> {
        return isLoading
    }

    fun getVote(): MutableLiveData<Boolean> {
        return voted
    }
}
