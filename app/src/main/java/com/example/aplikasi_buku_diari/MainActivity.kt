package com.example.aplikasi_buku_diari

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.aplikasi_buku_diari.ui.theme.AplikasibukudiariTheme
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AplikasibukudiariTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DiaryScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryScreen() {
    // State untuk menyimpan input teks
    var diaryText by remember { mutableStateOf("") }
    val context = LocalContext.current
    val currentDate = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(Date())

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Buku Diari Digital") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Hari ini: $currentDate",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Input tempat menulis diari
            OutlinedTextField(
                value = diaryText,
                onValueChange = { diaryText = it },
                label = { Text("Ceritakan harimu...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                minLines = 10
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tombol Simpan
            Button(
                onClick = {
                    if (diaryText.isNotEmpty()) {
                        Toast.makeText(context, "Diari berhasil disimpan!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Isi dulu diarinya ya!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan ke Memori")
            }
        }
    }
}