package com.example.firstproject

import android.os.Bundle
import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
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

            MaterialTheme(colorScheme = pinkTheme) {

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

    var dictQuery by remember { mutableStateOf("") }
    var dictResult by remember { mutableStateOf("") }

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

    val context = LocalContext.current
    val dictionary = remember { loadDictionary(context) }

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

            // DICTIONARY CARD (со сенка)

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                elevation = CardDefaults.cardElevation(6.dp)
            ) {

                Column(modifier = Modifier.padding(10.dp)) {

                    OutlinedTextField(
                        value = dictQuery,
                        onValueChange = { dictQuery = it },
                        label = { Text("Enter word") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            dictResult = searchInDictionary(dictionary, dictQuery)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Search")
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    if (dictResult.isNotEmpty()) {
                        Text(
                            text = "$dictQuery = $dictResult",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // TAG YOUR QUERY

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
            onClick = { tags = emptyList<String>() },
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

fun loadDictionary(context: Context): Map<String, String> {

    val map = mutableMapOf<String, String>()

    try {

        val inputStream = context.assets.open("dictionary.txt")

        inputStream.bufferedReader().forEachLine { line ->

            val parts = line.split(",")

            if (parts.size == 2) {

                val key = parts[0].trim()
                val value = parts[1].trim()

                map[key] = value
                map[value] = key
            }
        }

    } catch (e: Exception) {
        e.printStackTrace()
    }

    return map
}

fun searchInDictionary(
    dictionary: Map<String, String>,
    query: String
): String {

    return dictionary[query.trim()] ?: "Not found"
}