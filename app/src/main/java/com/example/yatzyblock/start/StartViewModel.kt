package com.example.yatzyblock.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yatzyblock.models.Player

class StartViewModel: ViewModel() {

    private val _listPlayers = MutableLiveData<MutableList<Player>>(mutableListOf())
    val listPlayers: LiveData<MutableList<Player>>
        get() = _listPlayers

    fun addPlayer(name: String) {
        val player = Player(name)
        _listPlayers.value?.add(player)
        _listPlayers.notifyObservers()
    }

    private fun <T> MutableLiveData<MutableList<T>>.notifyObservers() {
        this.value = this.value
    }


}