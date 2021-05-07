package com.example.yatzyblock.services

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.yatzyblock.models.Player
import com.google.gson.Gson

class PersistenceService(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("Shared_Preferences", Context.MODE_PRIVATE)


    fun getPlayerData(): Player {
        val playerJson: String? =
            preferences.getString("highscore_player", "No highscore_player found")
        if (playerJson == "No highscore_player found") return Player("NoName")
        return Gson().fromJson(playerJson, Player::class.java)
    }

    fun insertPlayerData(player: Player) {
        val editor = preferences.edit()
        val jsonPlayer = Gson().toJson(player)
        editor.putString("highscore_player", jsonPlayer)
        editor.apply()
        editor.commit()
    }
}