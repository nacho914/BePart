package com.example.bepart.main.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bepart.INITIATIVE_KEY
import com.example.bepart.databinding.ActivityMainBinding
import com.example.bepart.presentation.DetailInitiativeActivity
import com.example.bepart.presentation.viewmodel.MainViewModel
import com.example.bepart.domain.model.Initiatives
import com.example.bepart.domain.use_case.iniciativeUseCase
import com.example.bepart.getViewModel
import com.example.bepart.main.db.MainFirebaseDataSource
import com.example.bepart.presentation.addIniciativa

class MainActivity : AppCompatActivity(), MainActivityActions {

    private lateinit var binding: ActivityMainBinding
    private lateinit var initiativeAdapter: InitiativeAdapter
    private val initiativeList = mutableListOf<Initiatives>()

    private val viewModel : MainViewModel by lazy {
        getViewModel {
            MainViewModel(iniciativeUseCase(MainFirebaseDataSource()))
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
                Intent(this, addIniciativa::class.java)
            )
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
        intent.putExtra(INITIATIVE_KEY, key)
        startActivity(intent)
    }
}
