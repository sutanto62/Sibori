package id.or.sutanto.sibori.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import id.or.sutanto.sibori.feature.home.HomeDestination
import id.or.sutanto.sibori.feature.home.homeScreen
import androidx.compose.material3.Text

object AppDestinations {
    const val HOME = HomeDestination.route
    const val FAVORITES = "favorites"
    const val PROFILE = "profile"
}

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String = AppDestinations.HOME,
) {
    NavHost(navController = navController, startDestination = startDestination) {
        // Feature destinations
        homeScreen()

        // App-local placeholders for now
        composable(AppDestinations.FAVORITES) { Text("Favorites screen") }
        composable(AppDestinations.PROFILE) { Text("Profile screen") }
    }
}
