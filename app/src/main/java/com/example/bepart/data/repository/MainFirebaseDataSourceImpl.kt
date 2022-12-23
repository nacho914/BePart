package com.example.bepart.data.repository

import android.util.Log
import com.example.bepart.COLLECTION_NAME
import com.example.bepart.domain.model.Initiatives
import com.example.bepart.domain.model.Result
import com.example.bepart.domain.model.Result.Success
import com.example.bepart.domain.repository.MainDataSource
import com.example.bepart.presentation.mappers.IniciativesMappers
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class MainFirebaseDataSourceImpl : MainDataSource {


    override suspend fun addIniciativatoFireStore(iniciativa: Initiatives): Flow<Result<String, Error>> {
        val db = Firebase.firestore
        val iniciativaHashMap = hashMapOf(
            "nombre" to iniciativa.nombre,
            "categoria" to iniciativa.categoria,
            "descripcion" to iniciativa.descripcion,
            "creador" to iniciativa.creador,
            "votantes" to arrayListOf<Int>()
        )
        db.collection(COLLECTION_NAME).apply {
            add(iniciativaHashMap)

        }

        return flow { emit(Success("OK")) }


    }

    override suspend fun getAllInitiatives(): Flow<Result<List<Initiatives>, Error>> {
        val initiativeList = mutableListOf<Initiatives>()
        val db = Firebase.firestore
        val docRef = db.collection(COLLECTION_NAME)
        Log.d("FireBase", "starting")

        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                for (document in document.documents) {
                    initiativeList.add(
                        IniciativesMappers.fromLiveDataToInitiative(document)
                    )
                }
                initiativeList.sortByDescending { it.totalVotantes }
                Log.d("FireBase", document.toString())
            } else {
                Log.d("FireBase", "No such document")
            }
        }.addOnFailureListener { exception ->
            Log.d("FireBase", "get failed with ", exception)
        }.await()
        return flow { Success(initiativeList) }
    }

    override suspend fun addVote(
        initiativeKey: String,
        votersName: String
    ) {
        val db = Firebase.firestore
        var data: MutableMap<String, ArrayList<String>> = hashMapOf()
        val docRef = db.collection(COLLECTION_NAME)
            .document(initiativeKey).update("votantes", FieldValue.arrayUnion(votersName))
    }

    override suspend fun deleteVote(
        initiativeKey: String,
        votersName: String
    ) {
        val db = Firebase.firestore
        var data: MutableMap<String, ArrayList<String>> = hashMapOf()
        val docRef = db.collection(COLLECTION_NAME)
            .document(initiativeKey).update("votantes", FieldValue.arrayRemove(votersName))
    }
}
