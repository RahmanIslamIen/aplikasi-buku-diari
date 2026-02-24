package com.example.aplikasi_buku_diari

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diary")
data class Diary(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String,
    val content: String
)