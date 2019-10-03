package com.example.login;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AddLeadActivity extends AppCompatActivity implements AddLeadFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lead);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
