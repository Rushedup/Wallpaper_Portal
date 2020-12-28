package com.project.wallpaperportal.ui.main;

import androidx.fragment.app.FragmentPagerAdapter;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.project.wallpaperportal.R;
import com.project.wallpaperportal.logic.Flickr;
import com.project.wallpaperportal.logic.NASA;
import com.project.wallpaperportal.logic.Spotify;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.flickr, R.string.spotify, R.string.nasa};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return  Flickr.newInstance(position);
        } else if (position == 1) {
            return Spotify.newInstance(position);
        } else {
            return  NASA.newInstance(position);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }
}
