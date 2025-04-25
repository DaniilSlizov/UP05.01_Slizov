package ru.netology.bookdepository;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class PhotoDialogFragment extends DialogFragment {
    private static final String ARG_PHOTO_PATH = "photo_path";

    public static PhotoDialogFragment newInstance(String photoPath) {
        Bundle args = new Bundle();
        args.putString(ARG_PHOTO_PATH, photoPath);
        PhotoDialogFragment fragment = new PhotoDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String path = requireArguments().getString(ARG_PHOTO_PATH);
        ImageView imageView = new ImageView(requireContext());
        Bitmap bmp = PictureUtils.getScaledBitmap(path, requireActivity());
        imageView.setImageBitmap(bmp);
        imageView.setAdjustViewBounds(true);

        return new AlertDialog.Builder(requireContext())
                .setView(imageView)
                .create();
    }
}

