package com.example.bepart.presentation

import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.bepart.databinding.ActivityAddIniciativaBinding
import com.example.bepart.domain.model.Initiatives
import com.example.bepart.domain.use_case.InitiativeUseCase
import com.example.bepart.data.repository.MainFirebaseDataSourceImpl
import com.example.bepart.presentation.viewmodel.AddIniciativaViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AddInitiativeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddIniciativaBinding
    private val viewModel : AddIniciativaViewModel by lazy {
        AddIniciativaViewModel(InitiativeUseCase(MainFirebaseDataSourceImpl()))

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddIniciativaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.sendButton.setOnClickListener {
            val iniciativa = Initiatives(
                binding.textViewNomnbreIniciativa.editText?.text.toString(),
                binding.textViewCategoriaIniciativa.editText?.text.toString(),
                Firebase.auth.currentUser?.email.toString(),
                binding.textViewDescripcionIniciativa.editText?.text.toString(),
                0
            )
            viewModel.addIniciativa(iniciativa)
        }
        viewModel.complete.observe(this, Observer { complete ->
            if (complete){
                this.finish()
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onSupportNavigateUp()
        return true
    }
}