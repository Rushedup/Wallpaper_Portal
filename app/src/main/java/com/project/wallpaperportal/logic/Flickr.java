package com.project.wallpaperportal.logic;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import com.project.wallpaperportal.R;

import java.io.IOException;

public class Flickr extends Fragment {
    private Bitmap image;
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static Flickr newInstance(int index) {
        Flickr fragment = new Flickr();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        System.out.println("flickr on create");
        super.onCreate(savedInstanceState);
        System.out.println("flickr on create");
    }
    //refer to https://www.androidhive.info/2015/09/android-material-design-working-with-tabs/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("flickr on create view");
        return inflater.inflate(R.layout.flickr_tab, container, false);
    }

    /**
     * Instead of creating functions like these again for the button, just call it from the flickr class
     * @param result the image to be set
     */
    private void setWallapaper(Bitmap result) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getContext());
        try {
            wallpaperManager.setBitmap(result);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void buttonHandler(Button button) {
        button.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWallapaper(image);
            }
        });
    }
}
