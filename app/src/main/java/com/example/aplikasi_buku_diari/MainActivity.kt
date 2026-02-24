package com.example.aplikasi_buku_diari

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.aplikasi_buku_diari.ui.theme.AplikasibukudiariTheme
import kotlinx.coroutines.launch
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

    val context = LocalContext.current

    val database = remember {
        DiaryDatabase.getDatabase(context)
    }

    val diaryDao = remember {
        database.diaryDao()
    }

    val scope = rememberCoroutineScope()

    var diaryText by remember { mutableStateOf("") }
    var selectedDiary by remember { mutableStateOf<Diary?>(null) }

    val diaryList by diaryDao.getAllDiary()
        .collectAsState(initial = emptyList())

    val currentDate = SimpleDateFormat(
        "dd MMMM yyyy",
        Locale.getDefault()
    ).format(Date())

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

            OutlinedTextField(
                value = diaryText,
                onValueChange = { diaryText = it },
                label = { Text("Tulis diari...") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row {

                Button(
                    onClick = {
                        if (diaryText.isNotEmpty()) {
                            val diary = Diary(
                                date = currentDate,
                                content = diaryText
                            )
                            scope.launch {
                                diaryDao.insertDiary(diary)
                            }
                            diaryText = ""
                        }
                    }
                ) {
                    Text("Simpan")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        selectedDiary?.let {
                            val updated = it.copy(content = diaryText)
                            scope.launch {
                                diaryDao.updateDiary(updated)
                            }
                            diaryText = ""
                            selectedDiary = null
                        }
                    }
                ) {
                    Text("Update")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Semua Diari:",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(diaryList) { diary ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {

                            Text(text = diary.date)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = diary.content)

                            Row {
                                TextButton(onClick = {
                                    diaryText = diary.content
                                    selectedDiary = diary
                                }) {
                                    Text("Edit")
                                }

                                TextButton(onClick = {
                                    scope.launch {
                                        diaryDao.deleteDiary(diary)
                                    }
                                }) {
                                    Text("Hapus")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}