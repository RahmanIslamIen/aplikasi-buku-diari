package com.example.aplikasi_buku_diari

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {

    @Insert
    suspend fun insertDiary(diary: Diary)

    @Update
    suspend fun updateDiary(diary: Diary)

    @Delete
    suspend fun deleteDiary(diary: Diary)

    @Query("SELECT * FROM diary ORDER BY id DESC")
    fun getAllDiary(): Flow<List<Diary>>
}