package com.android.nazirshuqair.news;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nazirshuqair on 10/22/14.
 */
public class BreakingNewsFragment extends Fragment {

    final String IMG = "img";
    final String TEXT = "text";


    public static BreakingNewsFragment newInstance() {
        BreakingNewsFragment fragment = new BreakingNewsFragment();
        return fragment;
    }

    public BreakingNewsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_breaking_news, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<HashMap<String, Object>> mBreaking = new ArrayList<HashMap<String, Object>>();

        int[] path = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six,
                R.drawable.seven, R.drawable.eight, R.drawable.nine, R.drawable.ten, R.drawable.eleven, R.drawable.twelve,
                R.drawable.thirteen, R.drawable.fourteen, R.drawable.fiften, R.drawable.sixten, R.drawable.seventeen};

        String[] news = getResources().getStringArray(R.array.recent_news);

        for (int i = 0; i < path.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put(IMG, path[i]);
            map.put(TEXT, news[i]);
            mBreaking.add(map);

        }

        // Creating an array of our keys
        String[] keys = new String[] {
                IMG, TEXT
        };

        // Creating an array of our list item components.
        // Indices must match the keys array.
        int[] views = new int[] {
                R.id.break_img,
                R.id.break_text
        };

        // Creating a new SimpleAdapter that maps values to views using our keys and views arrays.
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), mBreaking, R.layout.grid_layout_view, keys, views);

        //Otherwise create a listview and set the adapter to it
        GridView mForcastGridView = (GridView) getView().findViewById(R.id.breaking_grid);
        mForcastGridView.setAdapter(adapter);

    }
}
