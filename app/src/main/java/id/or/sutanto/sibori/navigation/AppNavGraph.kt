package id.or.sutanto.sibori.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import id.or.sutanto.sibori.feature.home.HomeDestination
import id.or.sutanto.sibori.feature.home.homeScreen
import id.or.sutanto.sibori.ui.ScheduleScreen
import id.or.sutanto.sibori.ui.ProfileScreen

object AppDestinations {
    const val HOME = HomeDestination.route
    const val SCHEDULE = "schedule"
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
        composable(AppDestinations.SCHEDULE) { ScheduleScreen() }
        composable(AppDestinations.PROFILE) { ProfileScreen() }
    }
}
