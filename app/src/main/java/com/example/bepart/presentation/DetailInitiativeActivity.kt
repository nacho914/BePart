package com.example.bepart.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bepart.INITIATIVE_KEY
import com.example.bepart.R
import com.example.bepart.databinding.ActivityDetailInitiativeBinding
import com.example.bepart.presentation.viewmodel.DetailInitiativeViewModel
import com.example.bepart.domain.model.Initiatives
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class DetailInitiativeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailInitiativeBinding
    private lateinit var viewModel: DetailInitiativeViewModel
    private lateinit var key: String
    private var userID = Firebase.auth.currentUser!!.uid
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailInitiativeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        key = intent.getStringExtra(INITIATIVE_KEY).orEmpty()
        callViewModel()
        viewModel.addLiveListener(key, userID)
    }

    private fun callViewModel() {
        viewModel = ViewModelProvider(this)[DetailInitiativeViewModel::class.java]

        val observer = setUpInitiativeObserver()
        val statusObserver = setUpStatusObserver()
        val votedObserver = setUpVotedObserver()

        viewModel.getInitiative().observe(this, observer)
        viewModel.getStatus().observe(this, statusObserver)
        viewModel.getVote().observe(this, votedObserver)
    }

    private fun setUpInitiativeObserver(): Observer<Initiatives> {
        return Observer<Initiatives> {
            setUpData(it)
            binding.loadingWheel.visibility = View.GONE
        }
    }

    private fun setUpStatusObserver(): Observer<Boolean> = Observer<Boolean> {
        when (it) {
            true -> binding.loadingWheel.visibility = View.VISIBLE
            false -> binding.loadingWheel.visibility = View.GONE
        }
    }

    private fun setUpVotedObserver(): Observer<Boolean> =
        Observer<Boolean> {
            when (it) {
                true -> {
                    binding.addSupportBtn.setImageResource(R.drawable.ic_thumb_black_56)
                    binding.addSupportBtn.setOnClickListener {
                        viewModel.removeVote(key, "Nacho")
                    }
                }
                false -> {
                    binding.addSupportBtn.setImageResource(R.drawable.ic_thumb_white_56)
                    binding.addSupportBtn.setOnClickListener {
                        viewModel.addVote(key, "Nacho")
                    }
                }
            }
        }

    private fun setUpData(initiatives: Initiatives?) {
        binding.nameText.text = initiatives?.nombre
        binding.categoryText.text = initiatives?.categoria
        binding.descriptionText.text = initiatives?.descripcion
        binding.creatorName.text = initiatives?.creador
        binding.votesText.text = initiatives?.totalVotantes.toString()
    }
}
