package com.example.yatzyblock.repositories

import com.example.yatzyblock.models.Player
import com.example.yatzyblock.services.PersistenceService

class SharedPreferencesRepository(private val persistenceService: PersistenceService) {

    fun getPlayerData(): Player {
        return persistenceService.getPlayerData()
    }

    fun insertPlayerData(player: Player) {
        persistenceService.insertPlayerData(player)
    }


}