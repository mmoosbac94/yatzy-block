package com.example.yatzyblock.di

import com.example.yatzyblock.game.GameViewModel
import com.example.yatzyblock.models.Player
import com.example.yatzyblock.start.StartViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { StartViewModel() }

    viewModel { (players: Array<Player>) -> GameViewModel(players) }

}