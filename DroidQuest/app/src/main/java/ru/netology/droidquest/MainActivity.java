package ru.netology.droidquest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast; // Импортируем для работы с тостами
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView questionTextView;
    private Button trueButton;
    private Button falseButton;
    private Button backButton;
    private Button nextButton;

    private Question[] questionBank;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextView = findViewById(R.id.questionTextView);
        trueButton = findViewById(R.id.trueButton);
        falseButton = findViewById(R.id.falseButton);
        backButton = findViewById(R.id.backButton);
        nextButton = findViewById(R.id.nextButton);

        // Вопросы и их правильные ответы
        questionBank = new Question[] {
                new Question("ОС Android основана на ядре Linux", true),
                new Question("ОС Android — это Windows", false),
                new Question("Java используется для разработки под Android", true),
                new Question("iOS — это система для Android", false)
                // Добавьте дополнительные вопросы здесь
        };

        updateQuestion();

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentIndex > 0) {
                    currentIndex--;
                    updateQuestion();
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentIndex < questionBank.length - 1) {
                    currentIndex++;
                    updateQuestion();
                }
            }
        });
    }

    private void updateQuestion() {
        String questionText = questionBank[currentIndex].getText();
        questionTextView.setText(questionText);
    }

    private void checkAnswer(boolean userAnswer) {
        boolean correctAnswer = questionBank[currentIndex].isAnswerTrue();
        if (userAnswer == correctAnswer) {
            // Правильный ответ
            showToast("Правильно!");
        } else {
            // Неправильный ответ
            showToast("Неправильно!");
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show(); // Показываем тост
    }
}
