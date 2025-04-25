package ru.netology.bookdepository;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class BookFragment extends Fragment {
    private static final String ARG_BOOK_ID = "book_id";
    private static final String DIALOG_DATE = "DialogDate";
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO = 1;
    private Book mBook;
    private File mPhotoFile;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mReadedCheckBox;
    private Button mRemoveBookButton;
    private Button mReportButton;

    public static BookFragment newInstance(UUID bookId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOK_ID, bookId);
        BookFragment fragment = new BookFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID bookId = (UUID) getArguments().getSerializable(ARG_BOOK_ID);
        mBook = BookLab.getBookLab(getActivity()).getBook(bookId);
        mPhotoFile = BookLab.getBookLab(getActivity()).getPhotoFile(mBook);
    }

    @Override
    public void onPause() {
        super.onPause();
        BookLab.getBookLab(getActivity()).updateBook(mBook);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book, container, false);

        mTitleField = v.findViewById(R.id.book_title);
        mTitleField.setText(mBook.getTitle());
        mDateButton = v.findViewById(R.id.book_date);
        mReadedCheckBox = v.findViewById(R.id.book_readed);
        mReadedCheckBox.setChecked(mBook.isReaded());
        mRemoveBookButton = v.findViewById(R.id.remove_book_button);
        mPhotoButton = v.findViewById(R.id.book_camera);
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                mBook.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        Date date = mBook.getDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d, MMMM, yyyy");
        String formattedDate = dateFormat.format(date);
        mDateButton.setText(formattedDate);
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mBook.getDate());
                dialog.setTargetFragment(BookFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mReadedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mBook.setReaded(isChecked);
            }
        });

        mRemoveBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });

        mReportButton = v.findViewById(R.id.book_report);
        mReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getBookReport());
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.book_report_subject));
                i = Intent.createChooser(i, getString(R.string.send_report));
                startActivity(i);
            }
        });
        mPhotoButton = v.findViewById(R.id.book_camera);
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getBookReport());
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.book_report_subject));
                i = Intent.createChooser(i, getString(R.string.send_report));
                startActivity(i);
            }
        });


        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        PackageManager packageManager = getActivity().getPackageManager();
        boolean canTakePhoto = mPhotoFile != null &&
                captureImage.resolveActivity(packageManager) != null;

        if (canTakePhoto) {
            Uri uri;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                uri = Uri.fromFile(mPhotoFile);
            } else {
                uri = FileProvider.getUriForFile(
                        getActivity(),
                        getActivity().getPackageName() + ".provider",
                        mPhotoFile
                );
            }
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            captureImage.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            mPhotoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(captureImage, REQUEST_PHOTO);
                    mPhotoButton = (ImageButton) v.findViewById(R.id.book_camera);

                }
            });
        }
        return v;
    }

    private void showConfirmationDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Подтверждение удаления")
                .setMessage("Вы уверены, что хотите удалить эту книгу?")
                .setPositiveButton("Удалить", (dialog, which) -> {
                    BookLab.getBookLab(getActivity()).removeBook(mBook.getId()); // Удаление книги
                    getActivity().finish(); // Закрыть текущую активность
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mBook.setDate(date);
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d, MMMM, yyyy");
            mDateButton.setText(dateFormat.format(mBook.getDate()));
        } else if (requestCode == REQUEST_PHOTO) {
            updatePhotoView();
        }
    }

    private String getBookReport() {
        String readedString;
        if (mBook.isReaded()) {
            readedString = getString(R.string.book_report_readed);
        } else {
            readedString = getString(R.string.book_report_unreaded);
        }
        String dateFormat = "EEE, MM dd";
        String dateString = DateFormat
                .getDateInstance(DateFormat.MEDIUM).format(mBook.getDate());
        String report = getString(R.string.book_report, mBook.getTitle(), dateString, readedString);
        return report;
    }
    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {

            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }
}