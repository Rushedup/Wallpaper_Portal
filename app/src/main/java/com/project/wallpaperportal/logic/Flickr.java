package com.project.wallpaperportal.logic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import com.project.wallpaperportal.R;

public class Flickr extends Fragment {
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
        super.onCreate(savedInstanceState);
    }
    //refer to https://www.androidhive.info/2015/09/android-material-design-working-with-tabs/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.flickr_tab, container, false);
    }
}
