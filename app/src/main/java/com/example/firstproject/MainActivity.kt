package com.example.firstproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val pinkTheme = lightColorScheme(
                primary = Color(0xFFE91E63),
                secondary = Color(0xFFF8BBD0),
                background = Color.White,
                surface = Color.White
            )

            MaterialTheme(
                colorScheme = pinkTheme
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PinkTaggedScreen()
                }
            }
        }
    }
}

@Composable
fun PinkTaggedScreen() {

    var searchQuery by remember { mutableStateOf("") }
    var tagQuery by remember { mutableStateOf("") }
    var tags by remember {
        mutableStateOf(
            listOf(
                "AndroidFP",
                "Deitel",
                "Google",
                "iPhoneFP",
                "JavaFP",
                "JavaHTP"
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Search query") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = tagQuery,
                onValueChange = { tagQuery = it },
                label = { Text("Tag your query") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    if (tagQuery.isNotBlank()) {
                        tags = tags + tagQuery
                        tagQuery = ""
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Save")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Tagged Searches",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFFAD1457)
            )

            Spacer(modifier = Modifier.height(12.dp))

            tags.forEach { tag ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .background(
                            color = Color(0xFFFCE4EC),
                            shape = CircleShape
                        )
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = tag,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFFAD1457)
                    )

                    TextButton(onClick = { }) {
                        Text("Edit")
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { tags = emptyList() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null
            )
        }
    }
}