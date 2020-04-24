package com.project.wallpaperportal.logic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.project.wallpaperportal.R;

public class NASA extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static NASA newInstance(int index) {
        NASA fragment = new NASA();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        System.out.println("nasa on create");
        super.onCreate(savedInstanceState);
        System.out.println("nasa on create");
    }
    //refer to https://www.androidhive.info/2015/09/android-material-design-working-with-tabs/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("nasa on create view");
        return inflater.inflate(R.layout.nasa_tab, container, false);
    }
}
