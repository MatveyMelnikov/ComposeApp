package com.example.composeapp

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class ListItem(
    val title: String,
    val subText: String
)

@Composable
fun ListScreen() {
    val itemsList = listOf(
        ListItem(
            title = "Text0",
            subText = "Text1"
        ),
        ListItem(
            title = "Text2",
            subText = "Text3"
        )
    )

    LazyColumn {
        items(15) { index ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Text(text = itemsList[index % 2].title)
                Text(text = itemsList[index % 2].subText)
            }
        }
    }
}