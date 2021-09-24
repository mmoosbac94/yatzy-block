package com.example.yatzyblock.game

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yatzyblock.EntryType
import com.example.yatzyblock.models.Player
import com.example.yatzyblock.repositories.PlayerRepository
import kotlinx.coroutines.launch
import java.lang.Exception
import java.net.ConnectException


class GameViewModel(
    players: Array<Player>,
    private val playerRepository: PlayerRepository
) : ViewModel() {

    private var _playerList = MutableLiveData<MutableList<Player>>(mutableListOf())
    val playerList: LiveData<MutableList<Player>>
        get() = _playerList

    private var _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String>
        get() = _toastMessage


    init {
        players.map { player ->
            _playerList.value?.add(player)
        }
    }

    fun storePlayerData() {
        viewModelScope.launch {
            playerList.value?.let {
                try {
                    playerRepository.storePlayerData(it)
                    updateToast(ResponseState.SUCCESS)
                } catch (e: Exception) {
                    when (e) {
                        is ConnectException -> updateToast(ResponseState.CONNECTION_ERROR)
                        else -> updateToast(ResponseState.ERROR)
                    }
                }
            }
        }
    }

    private fun updateToast(responseState: ResponseState) {
        when (responseState) {
            ResponseState.SUCCESS -> _toastMessage.value = "Erfolgreich gespeichert"
            ResponseState.ERROR -> _toastMessage.value = "Ein Fehler ist aufgetreten"
            ResponseState.CONNECTION_ERROR -> _toastMessage.value = "Keine Internetverbindung"
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