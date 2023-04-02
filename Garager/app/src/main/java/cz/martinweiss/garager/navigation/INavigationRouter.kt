package cz.martinweiss.garager.navigation

import androidx.navigation.NavController

interface INavigationRouter {
    fun returnBack()
    fun getNavController(): NavController
}