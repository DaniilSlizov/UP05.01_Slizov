package ru.netology.droidquest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "QuestionActivity";
    private TextView questionTextView;
    private Button trueButton;
    private Button falseButton;
    private Button backButton;
    private Button nextButton;
    private Button deceitButton;
    private Question[] questionBank;
    private int currentIndex = 0;
    private boolean[] userAnswers; // Массив для хранения ответов пользователя
    private boolean[] deceitUsed; // Массив для отслеживания использования "Обмануть" для каждого вопроса
    private List<Integer> deceitQuestions; // Список для хранения индексов вопросов, где использовалась кнопка "Обмануть"
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
        deceitButton = findViewById(R.id.deceitButton);
        questionBank = new Question[]{
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
        userAnswers = new boolean[questionBank.length]; // Инициализация массива для хранения ответов
        deceitUsed = new boolean[questionBank.length]; // Инициализация массива для отслеживания использования "Обмануть"
        deceitQuestions = new ArrayList<>(); // Инициализация списка для хранения индексов вопросов
        updateQuestion(); // Обновление текста вопроса
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deceitUsed[currentIndex]) {
                    showDeceitWarning(); // Показ уведомления, если кнопка "Обмануть" была нажата для текущего вопроса
                } else {
                    Log.d(TAG, "onClick: Нажата кнопка 'Да'");
                    checkAnswer(true);
                }
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deceitUsed[currentIndex]) {
                    showDeceitWarning(); // Показ уведомления, если кнопка "Обмануть" была нажата для текущего вопроса
                } else {
                    Log.d(TAG, "onClick: Нажата кнопка 'Нет'");
                    checkAnswer(false);
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentIndex > 0) {
                    currentIndex--;
                    updateQuestion();
                    Log.d(TAG, "onClick: Переход к предыдущему вопросу, индекс: " + currentIndex);
                } else {
                    Toast.makeText(MainActivity.this, "Это первый вопрос.", Toast.LENGTH_SHORT).show();
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
                } else {
                    // Если все вопросы пройдены, проверяем, есть ли вопросы с "Обмануть"
                    if (!deceitQuestions.isEmpty()) {
                        currentIndex = deceitQuestions.get(0); // Возвращаемся к первому вопросу с "Обмануть"
                        updateQuestion();
                        Toast.makeText(MainActivity.this, "Теперь вы можете ответить на вопрос, где использовалась кнопка 'Обмануть'.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Это последний вопрос.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        deceitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deceitUsed[currentIndex] = true; // Устанавливаем флаг, что кнопка "Обмануть" была использована для текущего вопроса
                deceitQuestions.add(currentIndex); // Добавляем индекс текущего вопроса в список

                // Получаем правильный ответ для текущего вопроса
                String answer = questionBank[currentIndex].isAnswerTrue() ? "Правильный ответ: Да" : "Правильный ответ: Нет";

                // Переход на DeceitActivity с передачей ответа
                Intent intent = new Intent(MainActivity.this, DeceitActivity.class);
                intent.putExtra("ANSWER", answer);
                startActivity(intent);
            }
        });
    }

    private void showDeceitWarning() {
        Toast.makeText(MainActivity.this, "Обманывать нельзя.", Toast.LENGTH_SHORT).show();
    }

    private void updateQuestion() {
        questionTextView.setText(questionBank[currentIndex].getText());
        setTitle("Вопрос " + (currentIndex + 1) + " из " + questionBank.length);
    }

    private void checkAnswer(boolean userAnswer) {
        boolean correctAnswer = questionBank[currentIndex].isAnswerTrue();
        userAnswers[currentIndex] = userAnswer; // Сохраняем ответ пользователя

        if (userAnswer == correctAnswer) {
            Toast.makeText(this, "Правильно!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Неправильно!", Toast.LENGTH_SHORT).show();
        }
    }
}