package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Desktop on 15/3/2018.
 */

public class ImageViewFragment extends DialogFragment{

    public static ImageViewFragment newInstance(){
        ImageViewFragment fragment = new ImageViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image,container,false);
        Bundle bundle = getArguments();
        byte[] imgBytes = bundle.getByteArray("img");
        Bitmap imgBitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
        ImageView ivPreview = v.findViewById(R.id.ivPreview);
        ivPreview.setImageBitmap(imgBitmap);
        return v;
    }

}
