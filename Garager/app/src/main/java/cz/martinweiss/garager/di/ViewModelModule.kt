package cz.martinweiss.garager.di

import cz.martinweiss.garager.ui.screens.fuelList.FuelListViewModel
import cz.martinweiss.garager.ui.screens.vehicleAddEdit.AddEditVehicleViewModel
import cz.martinweiss.garager.ui.screens.vehicleDetail.DetailVehicleViewModel
import cz.martinweiss.garager.ui.screens.vehicleList.VehicleListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { VehicleListViewModel(get()) }
    viewModel { AddEditVehicleViewModel(get()) }
    viewModel { DetailVehicleViewModel(get()) }
    viewModel { FuelListViewModel(get()) }
}