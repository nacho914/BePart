package com.example.bepart.presentation

import com.example.bepart.INITIATIVES_CATEGORY
import com.example.bepart.INITIATIVES_CREATOR
import com.example.bepart.INITIATIVES_DESCRIPTION
import com.example.bepart.INITIATIVES_NAME
import com.example.bepart.INITIATIVES_VOTERS
import com.example.bepart.domain.model.Initiatives
import com.google.firebase.firestore.DocumentSnapshot

class IniciativesMappers {
    companion object {
        fun fromLiveDataToInitiative(doc: DocumentSnapshot): Initiatives {
            var votersCount = 0
            if (doc[INITIATIVES_VOTERS] != null) {
                votersCount = (doc[INITIATIVES_VOTERS] as ArrayList<*>).size
            }

            return Initiatives(
                nombre = doc[INITIATIVES_NAME].toString(),
                descripcion = doc[INITIATIVES_DESCRIPTION].toString(),
                categoria = doc[INITIATIVES_CATEGORY].toString(),
                creador = doc[INITIATIVES_CREATOR].toString(),
                totalVotantes = votersCount
            )
        }
    }
}