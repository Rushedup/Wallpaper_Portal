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
import com.project.wallpaperportal.logic.NASA;
import com.project.wallpaperportal.logic.Spotify;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.flickr, R.string.nasa};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        /*switch (position) {
            case 1:
                return Spotify.newInstance(position + 19);
            case 2:
                return NASA.newInstance(position + 28);
            default:
                return Flickr.newInstance(position + 1);
        }*/
        if (position == 0) {
            return  Flickr.newInstance(position);
        } else {
            return  NASA.newInstance(position);
        }

        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
//        return PlaceholderFragment.newInstance(position + 1);
//        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}
