package com.example.eileensflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;

    int currentCardDisplayedIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView flashcardQuestion = findViewById(R.id.flashcard_question);
        TextView flashcardAnswer = findViewById(R.id.flashcard_answer);
        flashcardQuestion.setVisibility(View.VISIBLE);
        flashcardAnswer.setVisibility(View.INVISIBLE);

        flashcardDatabase = new FlashcardDatabase(this);
        allFlashcards = flashcardDatabase.getAllCards();
        //flashcardDatabase.deleteAll();
        if (allFlashcards != null && allFlashcards.size() > 0) {
            flashcardQuestion.setText(allFlashcards.get(0).getQuestion());
            flashcardAnswer.setText(allFlashcards.get(0).getAnswer());
        }

        flashcardQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardQuestion.setVisibility(View.INVISIBLE);
                flashcardAnswer.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.myBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });

        findViewById(R.id.nextBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardQuestion.setVisibility(View.VISIBLE);
                flashcardAnswer.setVisibility(View.INVISIBLE);
                // don't try to go to next card if you have no cards to begin with
                if (allFlashcards.size() == 0)
                    return;
                // advance our pointer index so we can show the next card
                currentCardDisplayedIndex++;

                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                if(currentCardDisplayedIndex >= allFlashcards.size()) {
                    Snackbar.make(flashcardQuestion,
                            "You've reached the end of the cards, going back to start.",
                            Snackbar.LENGTH_SHORT)
                            .show();
                    currentCardDisplayedIndex = 0;
                }

                // set the question and answer TextViews with data from the database
                allFlashcards = flashcardDatabase.getAllCards();
                Flashcard flashcard = allFlashcards.get(currentCardDisplayedIndex);

                flashcardAnswer.setText(flashcard.getAnswer());
                flashcardQuestion.setText(flashcard.getQuestion());
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) { // this 100 needs to match the 100 we used when we called startActivityForResult!
            if(data !=null){
                String stringQuestion = data.getExtras().getString("string1"); // 'string1' needs to match the key we used when we put the string in the Intent
                String stringAnswer = data.getExtras().getString("string2");
                TextView question = findViewById(R.id.flashcard_question);
                question.setVisibility(View.VISIBLE);
                TextView answer = findViewById(R.id.flashcard_answer);
                answer.setVisibility(View.INVISIBLE);
                flashcardDatabase.insertCard(new Flashcard(stringQuestion, stringAnswer));
                allFlashcards = flashcardDatabase.getAllCards();
                currentCardDisplayedIndex++;
                Flashcard flashcard = allFlashcards.get(currentCardDisplayedIndex);
                answer.setText(flashcard.getAnswer());
                question.setText(flashcard.getQuestion());
            }

            Snackbar.make(findViewById(R.id.flashcard_question),
                    "Returned to main page",
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
    }


}