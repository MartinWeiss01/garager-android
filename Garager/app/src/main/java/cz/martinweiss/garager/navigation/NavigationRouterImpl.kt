package cz.martinweiss.garager.navigation

import androidx.navigation.NavController

class NavigationRouterImpl(private val navController: NavController) : INavigationRouter {
    override fun returnBack() {
        navController.popBackStack()
    }

    override fun getNavController(): NavController = navController
}