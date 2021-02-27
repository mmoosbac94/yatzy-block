package com.example.yatzyblock.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yatzyblock.EntryType
import com.example.yatzyblock.models.Player

class GameViewModel : ViewModel() {

    private var _playerList = MutableLiveData<MutableList<Player>>(mutableListOf())
    val playerList: LiveData<MutableList<Player>>
        get() = _playerList


    fun addPlayers(players: Array<Player>) {
        players.map { player ->
            _playerList.value?.add(player)
        }
    }

    fun addValueToPlayer(value: Int, player: Player, entryType: EntryType) {
        when(entryType) {
            EntryType.Einser -> player.einser = value
            EntryType.Zweier -> player.zweier = value
            EntryType.Dreier -> player.dreier = value
            EntryType.Vierer -> player.vierer = value
            EntryType.Fuenfer -> player.fuenfer = value
            EntryType.Sechser -> player.sechser = value
            EntryType.SummeOben -> player.summeOben
            EntryType.HatBonus -> player.hatBonus
            EntryType.EinPaar -> player.einPaar
            EntryType.ZweiPaar -> player.zweiPaar
            EntryType.DreiGleiche -> player.dreiGleiche
            EntryType.VierGleiche -> player.vierGleiche
            EntryType.KleineStrasse -> player.kleineStrasse
            EntryType.GrosseStrasse -> player.grosseStrasse
            EntryType.VollesHaus -> player.vollesHaus
            EntryType.Chance -> player.chance
            EntryType.Yatzy -> player.yatzy
            EntryType.EndSumme -> player.endSumme
            else -> return
        }

        _playerList.value?.map {
            Log.i("INFO", it.toString())
        }

    }


}