package com.example.yatzyblock.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yatzyblock.models.Player

class MainViewModel : ViewModel() {

    private var _listPlayers = MutableLiveData<MutableList<Player>>(mutableListOf())
    val listPlayer: LiveData<MutableList<Player>>
        get() = _listPlayers

    fun addPlayer(player: Player) {
        _listPlayers.value?.add(player)
        _listPlayers.notifyObservers()
    }

    private fun <T> MutableLiveData<MutableList<T>>.notifyObservers() {
        this.value = this.value
    }
}