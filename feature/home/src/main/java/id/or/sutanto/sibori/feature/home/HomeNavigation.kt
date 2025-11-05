package id.or.sutanto.sibori.feature.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

object HomeDestination {
    const val route: String = "home"
}

fun NavGraphBuilder.homeScreen() {
    composable(HomeDestination.route) {
        HomeRoute()
    }
}
