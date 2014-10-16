package com.android.nazirshuqair.contacts;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by nazirshuqair on 10/5/14.
 */
public class MasterFragment extends Fragment {

        //Tag to identify the fragment
    public static final String TAG = "MasterFragment.TAG";

    private static final String CNAME = "name";
    private static final String CEMAIL = "email";
    private static final String CRELATION = "relation";
    private static final String CNUM = "num";

    //ListView for the games
    ListView contactsView;

    EditText et;

    public ArrayList<HashMap<String, Object>> mContactList = new ArrayList<HashMap<String, Object>>();


    //This is to create a new instance of the fragment
    public static MasterFragment newInstance() {
        MasterFragment frag = new MasterFragment();
        return frag;
    }

    public void updateDisplay (ArrayList<Contacts> _object, boolean _refresh){

        mContactList.clear();

        for (Contacts contact: _object){
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put(CNAME, contact.getContactName());
            map.put(CEMAIL, contact.getContactEmail());
            map.put(CRELATION, contact.getContactRelation());
            map.put(CNUM, contact.getContactNum());

            mContactList.add(map);
        }

        if (_refresh) {
            contactsView.invalidateViews();
        }

    }

    public interface MasterClickListener {
        public void retriveData(HashMap<String, Object> item, int position);
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
        View myFragmentView = inflater.inflate(R.layout.fragment_list, container, false);

        //Connecting the ListView
        contactsView = (ListView) myFragmentView.findViewById(R.id.contactListView);
        return myFragmentView;
    }




    @Override
    public void onActivityCreated(Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);

        // Creating an array of our keys
        String[] keys = new String[] {
                CNAME, CNUM
        };

        // Creating an array of our list item components.
        // Indices must match the keys array.
        int[] views = new int[] {
                R.id.contName,
                R.id.contNum
        };

        //creating an adapter to populate the listview
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), mContactList,  R.layout.list_item, keys, views);
        contactsView.setAdapter(adapter);

        contactsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Call the displayDetails method and pass the adapter view and position
                //to populate details elements
                mListener.retriveData(mContactList.get(position), position);
            }
        });
    }


}
