package com.android.nazirshuqair.contacts;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by nazirshuqair on 10/14/14.
 */
public class EditFragment extends Fragment {

    //Tag to identify the fragment
    public static final String TAG = "EditFragment.TAG";

    EditText nameEdit;
    EditText numEdit;
    EditText emailEdit;
    EditText relationEdit;


    //This is to create a new instance of the fragment
    public static EditFragment newInstance() {
        EditFragment frag = new EditFragment();
        return frag;
    }

    public interface MasterClickListener {
        public void pushData(String _inputName, String _inputNum, String _inputEmail, String _inputRelation);
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
        View myFragmentView = inflater.inflate(R.layout.edit_fragment, container, false);
        //Connecting the edittext
        nameEdit = (EditText) myFragmentView.findViewById(R.id.name_text_edit);
        numEdit = (EditText) myFragmentView.findViewById(R.id.num_text_edit);
        emailEdit = (EditText) myFragmentView.findViewById(R.id.email_text_edit);
        relationEdit = (EditText) myFragmentView.findViewById(R.id.relation_text_edit);


        //Check if email is valid through onFocus Listener
        emailEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    //do job here owhen Edittext lose focus
                    if (!isEmailValid(emailEdit.getText().toString())){
                        emailEdit.setText("");
                        emailEdit.setHint(Html.fromHtml("<font color='#FF0000'>Please enter a valid email</font> "));
                    }
                }
            }
        });


        /*Button submitBtn = (Button) myFragmentView.findViewById(R.id.submit_button);
        submitBtn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {


                String phoneNumPlain = numEdit.getText().toString().replaceAll("\\D", "");
                String formattedPhone = String.valueOf(phoneNumPlain).replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1)-$2-$3");
                mListener.pushData(nameEdit.getText().toString(), formattedPhone,
                        emailEdit.getText().toString(), relationEdit.getText().toString());
            }
        });*/
        //Connecting the ListView
        return myFragmentView;
    }

    public void saveItem(){

        String phoneNumPlain = numEdit.getText().toString().replaceAll("\\D", "");
        String formattedPhone = String.valueOf(phoneNumPlain).replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1)-$2-$3");
        mListener.pushData(nameEdit.getText().toString(), formattedPhone,
                emailEdit.getText().toString(), relationEdit.getText().toString());
    }

    public void clearForm(){
        nameEdit.setText("");
        numEdit.setText("");
        emailEdit.setText("");
        relationEdit.setText("");
    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
