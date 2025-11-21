package com.example.mobileassignment

import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        val db = MainDatabase.getInstance(this)
        val dao = db.flashcardDao()
        var selectedCard: Flashcard? = null
        val spinner = findViewById<Spinner>(R.id.spinner)
        val questionText = findViewById<TextView>(R.id.ShowQuestion)
        val answerText = findViewById<TextView>(R.id.showAnswer)
        val showQBtn = findViewById<Button>(R.id.QuestionButton)
        val showABtn = findViewById<Button>(R.id.AnswerButton)
        lifecycleScope.launch(Dispatchers.IO) {
            val categories = dao.getAllCategories()
            val adapter = ArrayAdapter(  this@MainActivity2,
                android.R.layout.simple_spinner_item,
                categories)
            spinner.adapter = adapter


        }

        showQBtn.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val cat = spinner.selectedItem.toString()
                val card = dao.getRandomFlashcard(cat)

                selectedCard = card   // <-- save the card for answer

                withContext(Dispatchers.Main) {
                    if (card != null) {
                        questionText.text = card.question
                        answerText.text = ""  // clear previous answer
                    } else {
                        questionText.text = "No flashcards found"
                        answerText.text = ""
                    }
                }
            }
        }


        showABtn.setOnClickListener {
            if (selectedCard != null) {
                answerText.text = selectedCard!!.answer
            } else {
                Toast.makeText(this, "Please select a question first", Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}