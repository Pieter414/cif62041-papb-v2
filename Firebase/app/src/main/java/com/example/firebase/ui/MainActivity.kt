package com.example.firebase.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.firebase.data.model.Note
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    private val noteViewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                NotesScreen(noteViewModel)
            }
        }
        noteViewModel.fetchNotes()
    }
}

@Composable
fun NotesScreen(noteViewModel: NoteViewModel) {
    val notes by noteViewModel.notes.observeAsState(emptyList())

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    // State tambahan untuk edit mode
    var isEditing by remember { mutableStateOf(false) }
    var editingNoteId by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {

        Text(
            text = if (isEditing) "Edit Catatan" else "Tambah Catatan Baru",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Judul") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Isi Catatan") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Tombol Simpan / Update
        Button(onClick = {
            if (title.isNotBlank() && content.isNotBlank()) {
                if (isEditing && editingNoteId != null) {
                    // 🔁 Update note
                    noteViewModel.updateNote(
                        editingNoteId!!,
                        Note(title = title, content = content)
                    )
                } else {
                    // ➕ Tambah note baru
                    noteViewModel.addNote(Note(title = title, content = content))
                }

                // Reset form
                title = ""
                content = ""
                isEditing = false
                editingNoteId = null
            }
        }) {
            Text(if (isEditing) "Perbarui Catatan" else "Simpan Catatan")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Daftar Catatan",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))

        notes.forEach { note ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .clickable {
                        // Klik untuk edit
                        title = note.title
                        content = note.content
                        editingNoteId = note.id
                        isEditing = true
                    }
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = note.title, style = MaterialTheme.typography.titleMedium)
                    Text(text = note.content, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))

                    Row {
                        // Tombol Hapus
                        Button(
                            onClick = { note.id?.let { noteViewModel.deleteNote(it) } },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Hapus")
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        // Tombol Edit
                        Button(onClick = {
                            title = note.title
                            content = note.content
                            editingNoteId = note.id
                            isEditing = true
                        }) {
                            Text("Edit")
                        }
                    }
                }
            }
        }
    }
}
