package com.example.bepart.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bepart.INITIATIVE_KEY
import com.example.bepart.databinding.ActivityMainBinding
import com.example.bepart.presentation.viewmodel.MainViewModel
import com.example.bepart.domain.model.Initiatives
import com.example.bepart.domain.use_case.InitiativeUseCase
import com.example.bepart.getViewModel
import com.example.bepart.data.repository.MainFirebaseDataSourceImpl
import com.example.bepart.presentation.adapters.InitiativeAdapter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), MainActivityActions {

    private lateinit var binding: ActivityMainBinding
    private lateinit var initiativeAdapter: InitiativeAdapter
    private val initiativeList = mutableListOf<Initiatives>()

    private val viewModel : MainViewModel by lazy {
        getViewModel {
            MainViewModel(InitiativeUseCase(MainFirebaseDataSourceImpl()))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loadingWheel.visibility = View.VISIBLE
        initRecyclerView(initiativeList)

        binding.addInitiativeBtn.setOnClickListener {
            startActivity(
                Intent(this, AddInitiativeActivity::class.java)
            )
        }

        binding.refreshLayout.setOnRefreshListener {
            viewModel.getInitiativesList()
            binding.refreshLayout.isRefreshing = false
        }
    }

    override fun onStart() {
        super.onStart()
        callVieModel()
    }

    private fun callVieModel() {
        val observer = Observer<List<Initiatives>> {
            initiativeList.clear()
            initiativeList.addAll(it as ArrayList<Initiatives>)
            initiativeAdapter.notifyDataSetChanged()
            binding.loadingWheel.visibility = View.GONE
        }

        val statusObserver = Observer<Boolean> {
            when (it) {
                true -> binding.loadingWheel.visibility = View.VISIBLE
                false -> binding.loadingWheel.visibility = View.GONE
            }
        }

        viewModel.getInitiativesList().observe(this, observer)
        viewModel.getStatus().observe(this, statusObserver)
    }

    private fun initRecyclerView(
        initiativeList: MutableList<Initiatives>
    ) {
        initiativeAdapter = InitiativeAdapter(
            initiativeList,
            this
        )
        binding.initiativeRecycler.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.initiativeRecycler.adapter = initiativeAdapter
    }

    override fun openInitiativeDetailActivity(key: String) {
        val intent = Intent(this, DetailInitiativeActivity::class.java)
        val id = Firebase.auth.currentUser.toString()
        intent.putExtra(INITIATIVE_KEY, key)
        //intent.putExtra(INITIATIVE_KEY, id)
        startActivity(intent)
    }
}
