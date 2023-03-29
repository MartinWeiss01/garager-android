package cz.mendelu.pef.golf.di

import cz.mendelu.pef.golf.ui.screens.AddPlayer.AddPlayerViewModel
import cz.mendelu.pef.golf.ui.screens.Main.MainViewModel
import cz.mendelu.pef.golf.ui.screens.PlayersList.PlayersListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { AddPlayerViewModel(get()) }
    viewModel { PlayersListViewModel(get())}
}