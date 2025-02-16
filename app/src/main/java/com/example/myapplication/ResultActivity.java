package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private TextView tvUserName, tvScore;
    private Button btnShare, btnRestart;

    private String userName;
    private int score, totalQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvUserName = findViewById(R.id.tvUserName);
        tvScore = findViewById(R.id.tvScore);
        btnShare = findViewById(R.id.btnShare);
        btnRestart = findViewById(R.id.btnRestart);

        // Get Data from Intent
        userName = getIntent().getStringExtra("USER_NAME");
        score = getIntent().getIntExtra("SCORE", 0);
        totalQuestions = getIntent().getIntExtra("TOTAL", 0);

        // Set Text
        tvUserName.setText("Congratulations, " + userName + "!");
        tvScore.setText("Your Score: " + score + "/" + totalQuestions);

        // Share Score using Implicit Intent
        btnShare.setOnClickListener(v -> {
            String shareMessage = userName + " scored " + score + " out of " + totalQuestions + " in the Quiz App! 🎉";
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Share your score via"));
        });

        // Restart Quiz (Go back to MainActivity)
        btnRestart.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
