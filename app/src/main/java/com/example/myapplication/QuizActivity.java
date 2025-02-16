package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView tvQuestionNumber, tvQuestion;
    private RadioGroup rgOptions;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private Button btnPrevious, btnNext, btnSubmit;

    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int[] selectedAnswers; // Stores selected answers

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        userName = getIntent().getStringExtra("USER_NAME");

        // Initialize UI components
        tvQuestionNumber = findViewById(R.id.tvQuestionNumber);
        tvQuestion = findViewById(R.id.tvQuestion);
        rgOptions = findViewById(R.id.rgOptions);
        rbOption1 = findViewById(R.id.rbOption1);
        rbOption2 = findViewById(R.id.rbOption2);
        rbOption3 = findViewById(R.id.rbOption3);
        rbOption4 = findViewById(R.id.rbOption4);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Load Questions
        loadQuestions();
        selectedAnswers = new int[questionList.size()]; // Initialize array
        for (int i = 0; i < selectedAnswers.length; i++) {
            selectedAnswers[i] = -1; // Default: No selection
        }

        displayQuestion();

        // Handle Next Button
        btnNext.setOnClickListener(v -> {
            saveSelectedAnswer();
            if (currentQuestionIndex < questionList.size() - 1) {
                currentQuestionIndex++;
                displayQuestion();
            }
        });

        // Handle Previous Button
        btnPrevious.setOnClickListener(v -> {
            saveSelectedAnswer();
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                displayQuestion();
            }
        });

        // Handle Submit Button
        btnSubmit.setOnClickListener(v -> {
            saveSelectedAnswer();
            calculateScore();
            Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
            intent.putExtra("USER_NAME", userName);
            intent.putExtra("SCORE", score);
            intent.putExtra("TOTAL", questionList.size());
            startActivity(intent);
            finish();
        });

        updateButtons();
    }

    private void loadQuestions() {
        questionList = new ArrayList<>();
        questionList.add(new Question("In what year did the United States host the FIFA World Cup for the first time?",
                "1986", "1994", "2002", "2010", 1));
        questionList.add(new Question("Which planet is known as the Red Planet?",
                "Earth", "Mars", "Jupiter", "Venus", 1));
        questionList.add(new Question("Who wrote 'To Kill a Mockingbird'?",
                "Harper Lee", "J.K. Rowling", "Ernest Hemingway", "Jane Austen", 0));
        questionList.add(new Question("What is the capital of France?",
                "Berlin", "Madrid", "Paris", "Rome", 2));
        questionList.add(new Question("Which is the largest ocean on Earth?",
                "Atlantic", "Indian", "Arctic", "Pacific", 3));
    }

    private void displayQuestion() {
        Question question = questionList.get(currentQuestionIndex);
        tvQuestionNumber.setText((currentQuestionIndex + 1) + "/" + questionList.size());
        tvQuestion.setText(question.getQuestion());
        rbOption1.setText(question.getOption1());
        rbOption2.setText(question.getOption2());
        rbOption3.setText(question.getOption3());
        rbOption4.setText(question.getOption4());

        rgOptions.clearCheck(); // Reset selection

        // Restore previous selection
        if (selectedAnswers[currentQuestionIndex] != -1) {
            ((RadioButton) rgOptions.getChildAt(selectedAnswers[currentQuestionIndex])).setChecked(true);
        }

        updateButtons();
    }

    private void saveSelectedAnswer() {
        int selectedId = rgOptions.getCheckedRadioButtonId();
        if (selectedId != -1) {
            int selectedIndex = rgOptions.indexOfChild(findViewById(selectedId));
            selectedAnswers[currentQuestionIndex] = selectedIndex;
        }
    }

    private void updateButtons() {
        btnPrevious.setVisibility(currentQuestionIndex == 0 ? View.INVISIBLE : View.VISIBLE);
        btnNext.setVisibility(currentQuestionIndex == questionList.size() - 1 ? View.INVISIBLE : View.VISIBLE);
        btnSubmit.setVisibility(currentQuestionIndex == questionList.size() - 1 ? View.VISIBLE : View.INVISIBLE);
    }

    private void calculateScore() {
        score = 0;
        for (int i = 0; i < questionList.size(); i++) {
            if (selectedAnswers[i] == questionList.get(i).getCorrectAnswer()) {
                score++;
            }
        }
    }
}
