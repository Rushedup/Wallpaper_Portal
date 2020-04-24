package com.project.wallpaperportal.ui.main;

import androidx.fragment.app.FragmentPagerAdapter;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentPagerAdapter;
import com.project.wallpaperportal.R;
import com.project.wallpaperportal.logic.Flickr;

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
        switch (position) {
            case 1:
                return PlaceholderFragment.newInstance(position + 19);
            case 2:
                return PlaceholderFragment.newInstance(position + 28);
            default:
                return Flickr.newInstance(position + 1);
        }

        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
//        return PlaceholderFragment.newInstance(position + 1);
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
