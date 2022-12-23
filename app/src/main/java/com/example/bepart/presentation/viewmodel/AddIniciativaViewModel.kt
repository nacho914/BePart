package com.example.bepart.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bepart.domain.model.Initiatives
import com.example.bepart.domain.model.Result
import com.example.bepart.domain.use_case.InitiativeUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class AddIniciativaViewModel(
    private val iniciativeUseCase: InitiativeUseCase
) : ViewModel() {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    val complete = MutableLiveData<Boolean>()

    fun addIniciativa(iniciativa: Initiatives){
        coroutineScope.launch {
            iniciativeUseCase.addIniciativa(iniciativa)
                .onStart {  }
                .onCompletion {  }
                .collect{ result->
                    when (result){
                        is Result.Success -> {
                            complete.postValue(true)
                        }
                        is Result.Failure -> {
                            val error = result.reason
                            Log.e("AddIniciativaViewModel", error.message.toString())
                        }
                    }
                }

        }
    }
}