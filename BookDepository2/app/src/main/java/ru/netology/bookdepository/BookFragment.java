package ru.netology.bookdepository;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.CompoundButton;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BookFragment extends Fragment {
    private Book mBook;
    private EditText mTitleEditText;
    private Button mDateButton;
    private CheckBox mReadedCheckBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mBook = (Book) savedInstanceState.getSerializable("book");
        } else {
            mBook = new Book();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book, container, false);

        mTitleEditText = v.findViewById(R.id.titleEditText);
        mDateButton = v.findViewById(R.id.book_date);
        mReadedCheckBox = v.findViewById(R.id.book_readed);

        if (mBook != null) {
            mTitleEditText.setText(mBook.getTitle() != null ? mBook.getTitle() : "");
            mReadedCheckBox.setChecked(mBook.isReaded());

            // Форматирование даты
            if (mBook.getDate() != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
                mDateButton.setText(formatter.format(mBook.getDate()));
            } else {
                mDateButton.setText(""); // Или текст по умолчанию
            }
        }
        mTitleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // noop
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mBook != null) {
                    mBook.setTitle(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // noop
            }
        });

        mReadedCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (mBook != null) {
                mBook.setReaded(isChecked);
            }
        });
        return v;
    }
}
