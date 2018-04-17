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

import com.gpaschos_aikmpel.hotelbeaconapplication.R;

import java.io.ByteArrayOutputStream;

public class ImageViewFragment extends DialogFragment {

    public static final String TAG = "imageViewFragment";
    private static final String image_KEY = "img";

    public ImageViewFragment(){
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getDialog() == null) {
            return;
        }

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        getDialog().getWindow().setLayout(size.x, size.y / 2);
    }

    public static ImageViewFragment newInstance(Bitmap bitmap) {

        ImageViewFragment fragment = new ImageViewFragment();

        Bundle args = new Bundle();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        args.putByteArray(image_KEY, stream.toByteArray());

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

        Bundle bundle = getArguments();
        byte[] imgBytes = bundle.getByteArray(image_KEY);

        Bitmap imgBitmap = null;

        if (imgBytes != null) {
            imgBitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
        }

        ImageView ivPreview = view.findViewById(R.id.ivPreview);
        ivPreview.setImageBitmap(imgBitmap);

    }
}