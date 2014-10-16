package com.android.nazirshuqair.contacts;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by nazirshuqair on 10/14/14.
 */
public class ViewFragment extends Fragment {

    //Tag to identify the fragment
    public static final String TAG = "ViewFragment.TAG";

    private static final String ARG_NAME = "ViewFragment.ARG_NAME";
    private static final String ARG_EMAIL = "ViewFragment.ARG_EMAIL";
    private static final String ARG_RELATION = "ViewFragment.ARG_RELATION";
    private static final String ARG_NUM = "ViewFragment.ARG_NUM";
    private static final String ARG_POSITION = "ViewFragment.ARG_POSITION";

    TextView nameView;
    TextView numView;
    TextView emailView;
    TextView relationView;

    //This is to create a new instance of the fragment
    public static ViewFragment newInstance(String _name, String _email, String _relation, String _num, int _position) {
        ViewFragment frag = new ViewFragment();

        Bundle args = new Bundle();
        args.putString(ARG_NAME, _name);
        args.putString(ARG_EMAIL, _email);
        args.putString(ARG_RELATION, _relation);
        args.putString(ARG_NUM, _num);
        args.putInt(ARG_POSITION, _position);
        frag.setArguments(args);


        return frag;
    }

    public interface DeleteListener {
        public void deleteContact(String _name);
    }

    private DeleteListener mListener;

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);

        if(_activity instanceof DeleteListener) {
            mListener = (DeleteListener)_activity;
        } else {
            throw new IllegalArgumentException("Containing activity must implement OnButtonClickListener interface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Connecting the view
        View myFragmentView = inflater.inflate(R.layout.view_fragment, container, false);
        //Connecting the ListView
        return myFragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Bundle args = getArguments();
        if (args != null && args.containsKey(ARG_NAME)){
            setDisplay(args.getString(ARG_NAME),
                    args.getString(ARG_EMAIL),
                    args.getString(ARG_RELATION),
                    args.getString(ARG_NUM));

            Button deleteBtn = (Button) getView().findViewById(R.id.delete_button);
            deleteBtn.setOnClickListener(new Button.OnClickListener(){

                @Override
                public void onClick(View view) {
                    mListener.deleteContact(args.getString(ARG_NAME));
                }
            });

            Button callBtn = (Button) getView().findViewById(R.id.call_button);
            callBtn.setOnClickListener(new Button.OnClickListener(){

                @Override
                public void onClick(View view) {

                    String phoneNumPlain = args.getString(ARG_NUM).replaceAll("\\D", "");
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumPlain));
                    startActivity(intent);
                }
            });

            Button textBtn = (Button) getView().findViewById(R.id.message_button);
            textBtn.setOnClickListener(new Button.OnClickListener(){

                @Override
                public void onClick(View view) {
                    String phoneNumPlain = args.getString(ARG_NUM).replaceAll("\\D", "");

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phoneNumPlain, null)));

                }
            });

            Button emailBtn = (Button) getView().findViewById(R.id.email_button);
            emailBtn.setOnClickListener(new Button.OnClickListener(){

                @Override
                public void onClick(View view) {

                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL  , new String[]{args.getString(ARG_EMAIL)});
                    try {
                        startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        //Toast.makeText(MyActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    public void setDisplay(String _name, String _email, String _relation, String _num){

        //Connecting the edittext
        nameView = (TextView) getView().findViewById(R.id.name_text_view);
        numView = (TextView) getView().findViewById(R.id.num_text_view);
        emailView = (TextView) getView().findViewById(R.id.email_text_view);
        relationView = (TextView) getView().findViewById(R.id.relation_text_view);

        nameView.setText(_name);
        numView.setText(_num);
        emailView.setText(_email);
        relationView.setText(_relation);

    }
}
