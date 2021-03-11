package com.example.yatzyblock.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yatzyblock.EntryType
import com.example.yatzyblock.models.Player

class GameViewModel(players: Array<Player>) : ViewModel() {

    private var _playerList = MutableLiveData<MutableList<Player>>(mutableListOf())
    val playerList: LiveData<MutableList<Player>>
        get() = _playerList


    init {
        players.map { player ->
            _playerList.value?.add(player)
        }
    }

    fun addValueToPlayer(value: Int, player: Player, entryType: EntryType) {
        when (entryType) {
            EntryType.Einser -> player.einser = value
            EntryType.Zweier -> player.zweier = value
            EntryType.Dreier -> player.dreier = value
            EntryType.Vierer -> player.vierer = value
            EntryType.Fuenfer -> player.fuenfer = value
            EntryType.Sechser -> player.sechser = value

            EntryType.EinPaar -> player.einPaar = value
            EntryType.ZweiPaar -> player.zweiPaar = value
            EntryType.DreiGleiche -> player.dreiGleiche = value
            EntryType.VierGleiche -> player.vierGleiche = value
            EntryType.KleineStrasse -> player.kleineStrasse = value
            EntryType.GrosseStrasse -> player.grosseStrasse = value
            EntryType.VollesHaus -> player.vollesHaus = value
            EntryType.Chance -> player.chance = value
            EntryType.Yatzy -> player.yatzy = value

            else -> return
        }

        player.summeOben = calculateSummeOben(player)

        player.bonus = checkIfHatBonus(player.summeOben)

        player.endSumme = calculateEndSumme(player)

        _playerList.notifyObservers()

    }

    private fun calculateSummeOben(player: Player): Int {
        return player.einser + player.zweier + player.dreier +
                player.vierer + player.fuenfer + player.sechser
    }

    private fun checkIfHatBonus(summeOben: Int): Int {
        return if (summeOben >= 63) 30 else 0
    }

    private fun calculateEndSumme(player: Player): Int {
        return player.summeOben + player.einPaar + player.zweiPaar +
                player.dreiGleiche + player.vierGleiche +
                player.kleineStrasse + player.grosseStrasse +
                player.vollesHaus + player.chance + +player.bonus + player.yatzy
    }

    private fun <T> MutableLiveData<MutableList<T>>.notifyObservers() {
        this.value = this.value
    }


}