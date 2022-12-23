package com.example.bepart

import com.example.bepart.main.model.Initiatives
import com.google.firebase.firestore.DocumentSnapshot

class Utils {
    companion object {
        fun fromLiveDataToInitiative(doc: DocumentSnapshot): Initiatives {
            var votersCount = 0
            var list = listOf<String>()
            if (doc[INITIATIVES_VOTERS] != null) {
                votersCount = (doc[INITIATIVES_VOTERS] as ArrayList<*>).size
                list = doc[INITIATIVES_VOTERS] as List<String>
            }

            return Initiatives(
                nombre = doc[INITIATIVES_NAME].toString(),
                descripcion = doc[INITIATIVES_DESCRIPTION].toString(),
                categoria = doc[INITIATIVES_CATEGORY].toString(),
                creador = doc[INITIATIVES_CREATOR].toString(),
                totalVotantes = votersCount,
                id = doc.id,
                voters = list
            )
        }
    }
}
