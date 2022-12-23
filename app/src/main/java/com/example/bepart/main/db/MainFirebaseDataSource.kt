package com.example.bepart.main.db

import android.util.Log
import com.example.bepart.COLLECTION_NAME
import com.example.bepart.Utils
import com.example.bepart.main.model.Initiatives
import com.example.bepart.main.repository.MainDataSource
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class MainFirebaseDataSource : MainDataSource {
    override suspend fun getAllInitiatives(): List<Initiatives> {
        val initiativeList = mutableListOf<Initiatives>()
        val db = Firebase.firestore
        val docRef = db.collection(COLLECTION_NAME)

        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                for (document in document.documents) {
                    initiativeList.add(
                        Utils.fromLiveDataToInitiative(document)
                    )
                }
                initiativeList.sortByDescending { it.totalVotantes }
            } else {
                Log.d("TAG", "No such document")
            }
        }.addOnFailureListener { exception ->
            Log.d("TAG", "get failed with ", exception)
        }.await()
        return initiativeList
    }
}
