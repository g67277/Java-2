package com.android.nazirshuqair.contacts;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends Activity implements MasterFragment.MasterClickListener {

    public static final int REQUEST_CODE = 1;
    public static final int REQUEST_CODE2 = 2;



    Contacts mContacts;

    ArrayList<Contacts> contactsList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffff0912")));

        MasterFragment frag = null;
        if(savedInstanceState == null) {
            frag = MasterFragment.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.main_activity, frag, MasterFragment.TAG).commit();
        }

        try {
            readFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (contactsList.size() > 0) {

            frag.updateDisplay(contactsList, false);

        }
        mContacts = null;

    }


    public void retriveData(HashMap<String, Object> item, int position){

        Intent intent = new Intent(MainActivity.this, DetailActivity.class);

        String testing = (String) item.get("name");
        intent.putExtra("edit", false);
        intent.putExtra("contactName", (String) item.get("name"));
        intent.putExtra("contactEmail", (String) item.get("email"));
        intent.putExtra("contactRelation", (String) item.get("relation"));
        intent.putExtra("contactNum", (String) item.get("num"));
        intent.putExtra("position", position);

        startActivityForResult(intent, REQUEST_CODE2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void addContact(MenuItem item){

        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("edit", true);
        startActivityForResult(intent, REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        MasterFragment frag = (MasterFragment) getFragmentManager().findFragmentByTag(MasterFragment.TAG);

        if (requestCode == REQUEST_CODE2 && resultCode == RESULT_OK){

            try {
                deleteContactFile(data.getStringExtra("contactName"));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //Toast.makeText(this, "This means I need to delete" + data.getIntExtra("position", 0), Toast.LENGTH_SHORT).show();

        }else if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){

            String contactName = data.getStringExtra("contactName");
            String contactNum = data.getStringExtra("contactNum");
            String contactEmail = data.getStringExtra("contactEmail");
            String contactRelation = data.getStringExtra("contactRelation");

            Contacts contacts = new Contacts(contactName, contactEmail, contactRelation, contactNum);
            try {
                writeToFile(contacts, contactName);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                readFromFile();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            frag.updateDisplay(contactsList, true);

        }

    }

    public void deleteContactFile (String _fileName) throws IOException, ClassNotFoundException {

        File mydir = this.getDir("mydir", Context.MODE_PRIVATE); //Creating an internal dir;
        File fileWithinMyDir = new File(mydir, _fileName); //Getting a file within the dir.
        fileWithinMyDir.delete();

        readFromFile();

        MasterFragment frag = (MasterFragment) getFragmentManager().findFragmentByTag(MasterFragment.TAG);

        frag.updateDisplay(contactsList, true);

    }

    //Write the object to storage
    private void writeToFile(Contacts _data, String _filename) throws IOException {

        File mydir = this.getDir("mydir", Context.MODE_PRIVATE); //Creating an internal dir;
        File fileWithinMyDir = new File(mydir, _filename); //Getting a file within the dir.
        FileOutputStream out = new FileOutputStream(fileWithinMyDir); //Use the stream as usual to write into the file.
        ObjectOutputStream oos = new ObjectOutputStream(out);

        if (mContacts == null){
            mContacts = new Contacts();
        }
        mContacts.setData(_data);
        oos.writeObject(mContacts);
        oos.close();

    }

    private void readFromFile() throws IOException, ClassNotFoundException {

        File dir = new File("/data/data/com.android.nazirshuqair.contacts/app_mydir");
        File[] filelist = dir.listFiles();
        contactsList.clear();

        if (filelist != null) {
            for (File file : filelist) {
                if (file.isFile()) {
                    FileInputStream fin = new FileInputStream(file);
                    ObjectInputStream oin = new ObjectInputStream(fin);

                    mContacts = (Contacts) oin.readObject();

                    oin.close();

                    contactsList.add(mContacts);
                }
            }
        }
    }

}
