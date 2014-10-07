package com.android.nazirshuqair.java2test1;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by nazirshuqair on 10/5/14.
 */
public class MasterFragment extends Fragment {

        //Tag to identify the fragment
    public static final String TAG = "MasterFragment.TAG";

    //ListView for the games
    ListView movieList;
    ArrayList<String> dynamicList;

    EditText et;

    //This is to create a new instance of the fragment
    public static MasterFragment newInstance() {
        MasterFragment frag = new MasterFragment();
        return frag;
    }

    public interface MasterClickListener {
        public void retriveData(String _text);
    }

    private MasterClickListener mListener;

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);

        if(_activity instanceof MasterClickListener) {
            mListener = (MasterClickListener)_activity;
        } else {
            throw new IllegalArgumentException("Containing activity must implement OnButtonClickListener interface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Connecting the view
        View myFragmentView = inflater.inflate(R.layout.master_fragment, container, false);
        //Connecting the edittext
        et = (EditText) myFragmentView.findViewById(R.id.user_input);
        Button submitBtn = (Button) myFragmentView.findViewById(R.id.submit);
        submitBtn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                //need to fix this!!
                dynamicList.add(et.getText().toString());
                movieList.invalidateViews();
                mListener.retriveData(et.getText().toString());
            }
        });
        //Connecting the ListView
        movieList = (ListView) myFragmentView.findViewById(R.id.games_list);
        return myFragmentView;
    }




    @Override
    public void onActivityCreated(Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);

        //Updating the games list data
        String[] games = getResources().getStringArray(R.array.movies);
        dynamicList = new ArrayList<String>();

        for (int i = 0; i < games.length; i++){
            dynamicList.add(games[i]);
        }

        //creating an adapter to populate the listview
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, dynamicList);
        movieList.setAdapter(adapter);

        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Call the displayDetails method and pass the adapter view and position
                //to populate details elements
                mListener.retriveData((String) adapterView.getItemAtPosition(position));
            }
        });
    }

}
