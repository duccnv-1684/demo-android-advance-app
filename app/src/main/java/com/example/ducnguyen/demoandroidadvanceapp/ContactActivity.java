package com.example.ducnguyen.demoandroidadvanceapp;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {
    private static final int REQUEST_READ_CONTACTS = 101;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private List<Contact> mContacts;
    private LoadContactTask mLoadContactTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        mRecyclerView = findViewById(R.id.recycler_contacts);
        mProgressBar = findViewById(R.id.progress_bar_contact);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mLoadContactTask = new LoadContactTask();
        if (isGrantedPermission()) mLoadContactTask.execute();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_CONTACTS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLoadContactTask.execute();
                } else {
                    Toast.makeText(this, R.string.message_permission_denied
                            , Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private boolean isGrantedPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;
        else if (checkSelfPermission(Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS}
                    , REQUEST_READ_CONTACTS);
        }
        return false;
    }
    private void getContactList() {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if ((cursor != null ? cursor.getCount() : 0) > 0) {
            mContacts = new ArrayList<>();
            while (cursor.moveToNext()) {
                String name;
                String phone = getString(R.string.R_string_no_phone_number);
                String id = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts._ID));

                name = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cursor.getInt(cursor.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    if ((phoneCursor != null ? phoneCursor.getCount() : 0) > 0) {
                        while (phoneCursor.moveToNext()) {
                            phone = phoneCursor.getString(phoneCursor.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                        }
                        phoneCursor.close();
                    }
                }
                mContacts.add(new Contact(name, phone));
            }
            cursor.close();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadContactTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            getContactList();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            ContactAdapter contactAdapter = new ContactAdapter(mContacts);
            mRecyclerView.setAdapter(contactAdapter);
        }
    }
}
