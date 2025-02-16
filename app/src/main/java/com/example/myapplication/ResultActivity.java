package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private TextView tvUserName, tvScore, tvMessage;
    private Button btnRestart, btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Retrieve data from intent
        String userName = getIntent().getStringExtra("USER_NAME");
        int score = getIntent().getIntExtra("SCORE", 0);
        int totalQuestions = getIntent().getIntExtra("TOTAL", 0);

        // Initialize UI components
        tvUserName = findViewById(R.id.tvUserName);
        tvScore = findViewById(R.id.tvScore);
        tvMessage = findViewById(R.id.tvMessage);
        btnRestart = findViewById(R.id.btnRestart);
        btnExit = findViewById(R.id.btnExit);

        // Set values
        tvUserName.setText("Player: " + userName);
        tvScore.setText("Score: " + score + " / " + totalQuestions);

        // Display a message based on score
        if (score == totalQuestions) {
            tvMessage.setText("Excellent! You got a perfect score!");
        } else if (score >= totalQuestions / 2) {
            tvMessage.setText("Good job! You did well.");
        } else {
            tvMessage.setText("Keep practicing! You'll improve.");
        }

        // Restart Quiz Button
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, QuizActivity.class);
                intent.putExtra("USER_NAME", userName);
                startActivity(intent);
                finish();
            }
        });

        // Exit Button
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity(); // Closes all activities and exits the app
            }
        });
    }
}
