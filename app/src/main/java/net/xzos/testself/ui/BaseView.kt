package net.xzos.testself.ui

import androidx.compose.runtime.Composable
import net.xzos.testself.ui.theme.TestSelfTheme

@Composable
fun BaseTheme(
    content: @Composable () -> Unit
) {
    TestSelfTheme {
        content()
    }
}
