package com.android.nazirshuqair.contacts;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by nazirshuqair on 10/13/14.
 */
public class DetailActivity extends Activity implements EditFragment.MasterClickListener, ViewFragment.DeleteListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#a6d815")));

        Intent intent = getIntent();

        boolean test = intent.getBooleanExtra("edit", true);

        if (intent.getBooleanExtra("edit", true)){
            if(savedInstanceState == null) {
                EditFragment frag = EditFragment.newInstance();
                getFragmentManager().beginTransaction().replace(R.id.detail_activity, frag, EditFragment.TAG).commit();
            }
        }else {

            if(savedInstanceState == null) {
                ViewFragment frag = ViewFragment.newInstance(intent.getStringExtra("contactName"),
                        intent.getStringExtra("contactEmail"),
                        intent.getStringExtra("contactRelation"),
                        intent.getStringExtra("contactNum"),
                        intent.getIntExtra("position", 0));
                getFragmentManager().beginTransaction().replace(R.id.detail_activity, frag, ViewFragment.TAG).commit();
            }
        }


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        EditFragment frag = (EditFragment)getFragmentManager().findFragmentByTag(EditFragment.TAG);

        MenuItem saveItem = menu.add("Save");
        saveItem.setShowAsAction(1);


        saveItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                EditFragment frag = (EditFragment)getFragmentManager().findFragmentByTag(EditFragment.TAG);

                frag.saveItem();

                return false;
            }
        });
        MenuItem resetForm = menu.add("Reset");
        resetForm.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                EditFragment frag = (EditFragment)getFragmentManager().findFragmentByTag(EditFragment.TAG);

                frag.clearForm();

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public void pushData(String _inputName, String _inputNum, String _inputEmail, String _inputRelation) {

        Intent intent = new Intent();
        intent.putExtra("contactName", _inputName);
        intent.putExtra("contactEmail", _inputEmail);
        intent.putExtra("contactRelation", _inputRelation);
        intent.putExtra("contactNum", _inputNum);

        setResult(RESULT_OK, intent);
        finish();

        //Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteContact(String _name) {
        Intent intent = new Intent();
        intent.putExtra("contactName", _name);
        setResult(RESULT_OK, intent);
        finish();
    }
}
