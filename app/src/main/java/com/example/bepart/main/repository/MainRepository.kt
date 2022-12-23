package com.example.bepart.main.repository

class MainRepository(private val dataSource: MainDataSource) {
    suspend fun getAllInitiatives() = dataSource.getAllInitiatives()
}
