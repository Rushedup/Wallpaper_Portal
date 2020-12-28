package com.project.wallpaperportal.logic;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

public class Spotify extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static Spotify newInstance(int index) {
        Spotify fragment = new Spotify();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }
}
