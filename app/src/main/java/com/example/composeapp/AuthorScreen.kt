package com.example.composeapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AuthorScreen(fontFamily: FontFamily) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(32.dp)
            .background(Color(0xFF101010))
            .border(5.dp, Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Мельников Матвей Алексеевич",
            fontFamily = fontFamily,
            color = Color.White,
            fontSize = 35.sp,
            lineHeight = 35.sp,
            textAlign = TextAlign.Center
        )
        Box(modifier = Modifier.height(5.dp).fillMaxWidth().background(Color.White))
        Text(
            text = "ИКБО-06-21",
            fontFamily = fontFamily,
            color = Color.White,
            fontSize = 40.sp
        )
    }
}