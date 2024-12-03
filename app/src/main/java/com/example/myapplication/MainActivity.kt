package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.NoteRepository
import com.example.myapplication.navigation.AppNavGraph
import com.example.myapplication.ui.NoteViewModel
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Contoh: Menambahkan kolom baru untuk foto
                database.execSQL("ALTER TABLE notes ADD COLUMN photoUri TEXT")
            }
        }

        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "note_database"
        )
            .addMigrations(MIGRATION_1_2)
            .build()

        val repository = NoteRepository(database.noteDao())
        val viewModel = NoteViewModel(repository)


        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController, viewModel)
            }
        }
    }
}
