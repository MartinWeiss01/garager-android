package cz.mendelu.pef.golf.navigation

import androidx.navigation.NavController

interface INavigationRouter {
    fun returnBack()
    fun navigateToAddPlayerScreen()
    fun navigateToPlayersListScreen()
    fun getNavConotroller(): NavController
}