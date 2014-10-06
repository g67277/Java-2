package com.android.nazirshuqair.java2test1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ListFragment;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by nazirshuqair on 10/5/14.
 */
public class MasterFragment extends Fragment {

    //Tag to identify the fragment
    public static final String TAG = "MasterFragment.TAG";

    //ListView for the games
    ListView gamesList;


    //This is to create a new instance of the fragment
    public static MasterFragment newInstance() {
        MasterFragment frag = new MasterFragment();
        return frag;
    }

    public interface MasterClickListener {
        public void displayText(String _text, int position);
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
        //Connecting the edittexxt
        EditText et = (EditText) myFragmentView.findViewById(R.id.user_input);
        //Connecting the ListView
        gamesList = (ListView) myFragmentView.findViewById(R.id.games_list);
        return myFragmentView;
    }


    @Override
    public void onActivityCreated(Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);

        //Updating the games list data
        String[] games = getResources().getStringArray(R.array.games);

        //creating an adapter to populate the listview
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, games);
        gamesList.setAdapter(adapter);

        gamesList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Call the displayDetails method and pass the adapter view and position
                //to populate details elements
                Toast.makeText(getActivity(), (String) adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                mListener.displayText((String) adapterView.getItemAtPosition(position), position);
            }
        });
    }

}
