package com.example.mobileassignment
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FlashCardsDao {


    @Insert
    suspend fun addFlashCard(flashcard: Flashcard)

    @Query("SELECT DISTINCT category FROM flashcards")
    suspend fun getAllCategories(): List<String>

    @Query("SELECT * FROM flashcards WHERE category = :category ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomFlashcard(category: String): Flashcard?
}