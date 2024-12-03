package com.example.myapplication.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.myapplication.data.Note
import com.example.myapplication.ui.NoteViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import coil.compose.rememberImagePainter

@Composable
fun HomeScreen(navController: NavController, viewModel: NoteViewModel) {
    val notes by viewModel.allNotes.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(onClick = { navController.navigate("detail") }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Add Note")
        }

        notes.forEach { note: Note ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate("detail/${note.id}") },
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {

                    note.photoUri?.let {
                        Image(
                            painter = rememberImagePainter(it),
                            contentDescription = "Note Photo",
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surface)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = note.title,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = { viewModel.delete(note) },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Note",
                            tint = Color.Red
                        )
                    }
                }
            }
        }
    }
}
