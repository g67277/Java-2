package com.android.nazirshuqair.news;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by nazirshuqair on 10/22/14.
 */
public class FeaturedFragment extends Fragment {

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FeaturedFragment newInstance() {
        FeaturedFragment fragment = new FeaturedFragment();
        return fragment;
    }

    public FeaturedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_featured, container, false);
        return rootView;
    }

}
