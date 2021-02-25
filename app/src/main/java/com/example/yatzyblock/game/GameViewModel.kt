package com.example.yatzyblock.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    fun addValueToPlayer(value: Int, player: Player, rowIndex: Int) {

    }


}