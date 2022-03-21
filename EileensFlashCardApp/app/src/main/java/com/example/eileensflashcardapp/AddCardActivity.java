package com.example.eileensflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);


        findViewById(R.id.saveBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent data = new Intent(); // create a new Intent, this is where we will put our data
                String questionString = ((EditText) findViewById(R.id.questionTextField)).getText().toString();
                String answerString = ((EditText) findViewById(R.id.answerTextField)).getText().toString();
                data.putExtra("string1", questionString); // puts one string into the Intent, with the key as 'string1'
                data.putExtra("string2", answerString); // puts another string into the Intent, with the key as 'string2
                setResult(RESULT_OK, data); // set result code and bundle data for response
                finish(); // closes this activity and pass data to the original activity that launched this activity
            }
        });
        findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}