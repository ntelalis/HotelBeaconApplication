package com.gpaschos_aikmpel.hotelbeaconapplication.fragments;

import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.LocalVariables;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

public class ImageViewFragment extends DialogFragment {

    public static final String TAG = "imageViewFragment";
    private static final String image_KEY = "img";

    public ImageViewFragment() {
    }

    public static ImageViewFragment newInstance(String imgFileName) {

        ImageViewFragment fragment = new ImageViewFragment();

        Bundle args = new Bundle();

        args.putString(image_KEY, imgFileName);

        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        String imgFileName = getArguments().getString(image_KEY);

        Bitmap imgBitmap = LocalVariables.readImage(getActivity(), imgFileName);

        ImageView ivPreview = view.findViewById(R.id.ivPreview);
        ivPreview.setImageBitmap(imgBitmap);

        if (getDialog() == null) {
            return;
        }

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int imgHeight = imgBitmap.getHeight();
        int imgWidth = imgBitmap.getWidth();
        if (imgWidth < size.x) {
            size.x = imgWidth;
        }
        if (imgHeight < size.y) {
            size.y = imgHeight;
        }

        Objects.requireNonNull(getDialog().getWindow()).setLayout(size.x, size.y);

    }
}