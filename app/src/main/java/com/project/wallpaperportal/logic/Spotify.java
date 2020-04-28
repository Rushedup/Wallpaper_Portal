package com.project.wallpaperportal.logic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.project.wallpaperportal.R;

public class Spotify extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static Spotify newInstance(int index) {
        Spotify fragment = new Spotify();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        System.out.println("spotify on create");
        super.onCreate(savedInstanceState);
        System.out.println("spotify on create");
    }

    //refer to https://www.androidhive.info/2015/09/android-material-design-working-with-tabs/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("spotify on create view");
        return inflater.inflate(R.layout.spotify_tab, container, false);
    }
    private void authenticateSpotify() {
        
    }
}