package id.or.sutanto.sibori

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import id.or.sutanto.sibori.core.designsystem.theme.SiboriTheme
import id.or.sutanto.sibori.navigation.AppDestinations
import id.or.sutanto.sibori.navigation.AppNavGraph
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SiboriTheme {
                SiboriRoot()
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun SiboriRoot(widthSizeClass: WindowWidthSizeClass = WindowWidthSizeClass.Compact) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinationsItems.forEach { item ->
                item(
                    icon = { Icon(item.icon, contentDescription = item.label) },
                    label = { Text(item.label) },
                    selected = currentRoute == item.route,
                    onClick = {
                        val options = navOptions {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                        }
                        navController.navigate(item.route, options)
                    }
                )
            }
        }
    ) {
        AppNavGraph(navController = navController)
    }
}

data class AppDestItem(val route: String, val label: String, val icon: ImageVector)

private val AppDestinationsItems = listOf(
    AppDestItem(AppDestinations.HOME, "Home", Icons.Default.Home),
    AppDestItem(AppDestinations.FAVORITES, "Favorites", Icons.Default.Favorite),
    AppDestItem(AppDestinations.PROFILE, "Profile", Icons.Default.AccountBox),
)

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
private fun PlaceholderScreen(title: String, modifier: Modifier = Modifier) {
    Text(
        text = "$title screen",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SiboriTheme {
        Greeting("Android")
    }
}