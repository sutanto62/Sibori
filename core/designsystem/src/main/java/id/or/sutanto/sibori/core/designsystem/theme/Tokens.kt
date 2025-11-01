package id.or.sutanto.sibori.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme

/** Spacing scale used across the app's UI. */
data class SpacingTokens(
    val xxs: Dp = 2.dp,
    val xs: Dp = 4.dp,
    val sm: Dp = 8.dp,
    val md: Dp = 12.dp,
    val lg: Dp = 16.dp,
    val xl: Dp = 24.dp,
    val xxl: Dp = 32.dp,
)

/** Corner radii for shapes. */
data class RadiiTokens(
    val xs: Dp = 4.dp,
    val sm: Dp = 8.dp,
    val md: Dp = 12.dp,
    val lg: Dp = 16.dp,
    val xl: Dp = 20.dp,
)

/** Elevation levels for surfaces (Material tonal elevation Dp). */
data class ElevationTokens(
    val level0: Dp = 0.dp,
    val level1: Dp = 1.dp,
    val level2: Dp = 3.dp,
    val level3: Dp = 6.dp,
    val level4: Dp = 8.dp,
    val level5: Dp = 12.dp,
)

internal val LocalSpacing = staticCompositionLocalOf { SpacingTokens() }
internal val LocalRadii = staticCompositionLocalOf { RadiiTokens() }
internal val LocalElevations = staticCompositionLocalOf { ElevationTokens() }

val MaterialTheme.spacing: SpacingTokens
    @Composable @ReadOnlyComposable get() = LocalSpacing.current

val MaterialTheme.radii: RadiiTokens
    @Composable @ReadOnlyComposable get() = LocalRadii.current

val MaterialTheme.elevations: ElevationTokens
    @Composable @ReadOnlyComposable get() = LocalElevations.current
