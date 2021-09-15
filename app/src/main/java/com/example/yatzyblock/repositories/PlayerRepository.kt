package com.example.yatzyblock.repositories

import com.example.yatzyblock.models.Player
import com.example.yatzyblock.models.PlayerInformation
import com.example.yatzyblock.services.PlayerAPI
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class PlayerRepository {

    suspend fun storePlayerData(players: MutableList<Player>) {
        val convertedPlayerList: MutableList<PlayerInformation> = players.map { player ->
            PlayerInformation(player.name, player.wins, player.endSumme)
        }.toMutableList()

        val gson = Gson()
        val json = gson.toJson(convertedPlayerList)

        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

        PlayerAPI.retrofitService.updatePlayers(requestBody)
    }


}