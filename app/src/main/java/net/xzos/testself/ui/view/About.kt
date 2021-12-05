package net.xzos.testself.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.xzos.testself.BuildConfig
import net.xzos.testself.R
import net.xzos.testself.ui.theme.TextColorLight
import net.xzos.testself.ui.theme.TextColorRed

@Preview
@Composable
fun About() {
    var powerTextName by remember { mutableStateOf("xz") }
    var powerTextColor by remember { mutableStateOf(Color.Unspecified) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.TopCenter),
            text = stringResource(R.string.app_name),
            fontSize = 30.sp
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.BottomCenter),
        ) {
            Text(
                text = "version: " + BuildConfig.VERSION_NAME,
                color = TextColorLight,
            )
            Text(
                color = TextColorLight,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable {
                        powerTextName = "hh"
                        powerTextColor = TextColorRed
                    },
                text = buildAnnotatedString {
                    append("Powered by ")
                    withStyle(style = SpanStyle(color = powerTextColor)) {
                        append(powerTextName)
                    }
                }
            )
        }
    }
}