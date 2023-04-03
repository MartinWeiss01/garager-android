package cz.martinweiss.garager.di

import cz.martinweiss.garager.ui.screens.vehicleList.VehicleListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        VehicleListViewModel(get())
    }
}