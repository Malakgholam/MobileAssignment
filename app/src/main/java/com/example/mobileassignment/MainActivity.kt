package com.example.mobileassignment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val db = MainDatabase.getInstance(this)
        val dao = db.flashcardDao()


            val question = findViewById<EditText>(R.id.QuestionText)
            val answer = findViewById<EditText>(R.id.AnswerText)
            val category = findViewById<EditText>(R.id.CategoryText)
            val Flashcardbtn = findViewById<Button>(R.id.FlashcardButton)
            val quizBtn = findViewById<Button>(R.id.QuizButton)
           Flashcardbtn.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val card = Flashcard(
                    question = question.text.toString(),
                    answer = answer.text.toString(),
                    category = category.text.toString().trim().lowercase()
                )

                dao.addFlashCard(card)

                Log.d("flash", "Inserted: $card")

                launch(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "saved", Toast.LENGTH_SHORT).show()
                }
            }
        }
        quizBtn.setOnClickListener {
            val intent = Intent(this , MainActivity2::class.java)
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}