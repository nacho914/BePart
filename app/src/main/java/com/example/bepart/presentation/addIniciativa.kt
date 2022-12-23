package com.example.bepart.presentation

import android.os.Bundle
import android.provider.Settings
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.bepart.BuildConfig
import com.example.bepart.databinding.ActivityAddIniciativaBinding
import com.example.bepart.domain.model.Initiatives
import com.example.bepart.domain.use_case.iniciativeUseCase
import com.example.bepart.main.db.MainFirebaseDataSource
import com.example.bepart.presentation.viewmodel.AddIniciativaViewModel

class addIniciativa : AppCompatActivity() {

    private lateinit var binding: ActivityAddIniciativaBinding
    private val viewModel : AddIniciativaViewModel by lazy {
        AddIniciativaViewModel(iniciativeUseCase(MainFirebaseDataSource()))

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddIniciativaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.sendButton.setOnClickListener {
            val iniciativa = Initiatives(
                binding.textViewNomnbreIniciativa.editText?.text.toString(),
                binding.textViewCategoriaIniciativa.editText?.text.toString(),
                Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID),
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