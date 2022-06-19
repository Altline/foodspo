package altline.foodspo.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Spaces(
    val small: Dp = 4.dp,
    val medium: Dp = 8.dp,
    val large: Dp = 12.dp,
    val xl: Dp = 16.dp,
    val xxl: Dp = 20.dp,
    val xxxl: Dp = 24.dp
)

val LocalSpaces = staticCompositionLocalOf { Spaces() }