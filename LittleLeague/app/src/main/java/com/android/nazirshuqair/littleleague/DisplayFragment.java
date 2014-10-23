package com.android.nazirshuqair.littleleague;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by nazirshuqair on 10/22/14.
 */
public class DisplayFragment extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static DisplayFragment newInstance(int sectionNumber) {
        DisplayFragment fragment = new DisplayFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public DisplayFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();

        ListView namesList = (ListView) getView().findViewById(R.id.namesListView);
        ArrayAdapter<String> adapter = null;
        String[] names = null;

        switch (args.getInt(ARG_SECTION_NUMBER)){

            case 0:
                names = getResources().getStringArray(R.array.team1);
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, names);
                namesList.setAdapter(adapter);
                break;
            case 1:
                names = getResources().getStringArray(R.array.team2);
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, names);
                namesList.setAdapter(adapter);
                break;
            case 2:
                names = getResources().getStringArray(R.array.team3);
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, names);
                namesList.setAdapter(adapter);
                break;
            case 3:
                names = getResources().getStringArray(R.array.team4);
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, names);
                namesList.setAdapter(adapter);
                break;
            case 4:
                names = getResources().getStringArray(R.array.team5);
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, names);
                namesList.setAdapter(adapter);
                break;
            case 5:
                names = getResources().getStringArray(R.array.team6);
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, names);
                namesList.setAdapter(adapter);
                break;
            default:
                break;
        }

    }
}
