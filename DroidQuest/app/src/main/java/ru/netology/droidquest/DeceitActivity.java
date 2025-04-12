package ru.netology.droidquest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DeceitActivity extends AppCompatActivity {

    private TextView warningTextView;
    private TextView answerTextView; // Новый TextView для отображения ответа
    private Button showAnswerButton;
    private String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deceit);

        warningTextView = findViewById(R.id.warningTextView);
        answerTextView = findViewById(R.id.answerTextView);
        showAnswerButton = findViewById(R.id.show_answer_button);

        // Получение ответа из Intent
        answer = getIntent().getStringExtra("ANSWER");
        warningTextView.setText("Вы уверены, что хотите это сделать?");

        showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer != null) { // Проверка на null перед показом ответа
                    answerTextView.setText(answer); // Текст ответа
                } else {
                    answerTextView.setText("Ответ не доступен");
                }
            }
        });
    }
}