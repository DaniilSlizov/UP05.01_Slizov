package ru.netology.droidquest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "QuestionActivity"; // Тег для логирования

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
        Log.d(TAG, "onCreate: Activity создана");

        questionTextView = findViewById(R.id.questionTextView);
        trueButton = findViewById(R.id.trueButton);
        falseButton = findViewById(R.id.falseButton);
        backButton = findViewById(R.id.backButton);
        nextButton = findViewById(R.id.nextButton);

        questionBank = new Question[] {
                new Question("ОС Android основана на ядре Linux", true),
                new Question("ОС Android — это Windows", false),
                new Question("Java используется для разработки под Android", true),
                new Question("Android поддерживает языки программирования C++ и Kotlin", true),
                new Question("Google является основным разработчиком ОС Android", true),
                new Question("Android — это система только для мобильных телефонов", false),
                new Question("Google Play является официальным маркером приложений для Android", true),
                new Question("Android является открытой платформой", true),
                new Question("Версия Android с именем 'Marshmallow' имеет номер 6.0", true),
                new Question("Все устройства на Android имеют одинаковый интерфейс", false)
        };
        updateQuestion();

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Нажата кнопка 'Да'");
                checkAnswer(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Нажата кнопка 'Нет'");
                checkAnswer(false);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentIndex > 0) {
                    currentIndex--;
                    updateQuestion();
                    Log.d(TAG, "onClick: Переход к предыдущему вопросу, индекс: " + currentIndex);
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentIndex < questionBank.length - 1) {
                    currentIndex++;
                    updateQuestion();
                    Log.d(TAG, "onClick: Переход к следующему вопросу, индекс: " + currentIndex);
                }
            }
        });
    }

    private void updateQuestion() {
        String questionText = questionBank[currentIndex].getText();
        questionTextView.setText(questionText);
        Log.d(TAG, "updateQuestion: Обновлен вопрос: " + questionText);
    }

    private void checkAnswer(boolean userAnswer) {
        boolean correctAnswer = questionBank[currentIndex].isAnswerTrue();
        if (userAnswer == correctAnswer) {
            Toast.makeText(this, "Правильно!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "checkAnswer: Пользователь ответил правильно");
        } else {
            Toast.makeText(this, "Неправильно!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "checkAnswer: Пользователь ответил неправильно");
        }
    }
}
