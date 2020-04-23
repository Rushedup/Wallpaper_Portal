package com.project.wallpaperportal.logic;

import android.os.Bundle;

//import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class Flickr extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static Flickr newInstance(int index) {
        Flickr fragment = new Flickr();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
