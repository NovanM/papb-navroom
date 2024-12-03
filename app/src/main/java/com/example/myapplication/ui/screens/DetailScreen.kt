package com.example.myapplication.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.data.Note
import com.example.myapplication.ui.NoteViewModel
import androidx.compose.foundation.Image
import coil.compose.rememberImagePainter

@Composable
fun DetailScreen(noteId: Int?, navController: NavController, viewModel: NoteViewModel) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var photoUri by remember { mutableStateOf<String?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { photoUri = it.toString() }
    }

    LaunchedEffect(noteId) {
        noteId?.let {
            val note = viewModel.getNoteById(it)
            note?.let {
                title = it.title
                content = it.content
                photoUri = it.photoUri
            }

        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text(text = "Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text(text = "Content") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        photoUri?.let {
            Image(
                painter = rememberImagePainter(it),
                contentDescription = "Uploaded Photo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }

        Button(
            onClick = { launcher.launch("image/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Upload Photo")
        }

        Button(
            onClick = {
                val note = Note(
                    id = noteId ?: 0,
                    title = title,
                    content = content,
                    photoUri = photoUri
                )
                if (noteId == null) {
                    viewModel.insert(note)
                } else {
                    viewModel.update(note)
                }
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (noteId == null) "Add Note" else "Update Note")
        }
    }


}


@Composable
fun pickImage(onImagePicked: (String) -> Unit) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onImagePicked(it.toString()) }
    }
    Button(onClick = { launcher.launch("image/*") }) {
        Text("Pick Image")
    }
}