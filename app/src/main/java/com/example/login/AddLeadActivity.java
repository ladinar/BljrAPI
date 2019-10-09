package com.example.login;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AddLeadActivity extends AppCompatActivity implements AddLeadFragment.OnFragmentInteractionListener, DetailSalesFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lead);

        String addlead = getIntent().getStringExtra("addlead");
        String detaillead = getIntent().getStringExtra("detail_lead");
        if (addlead != null) {
            Fragment androidFragment = new AddLeadFragment();
            replaceFragment(androidFragment);
        } else if (detaillead != null) {
            Fragment androidFragment2 = new DetailSalesFragment();
            replaceFragment(androidFragment2);
        }
    }

    public void replaceFragment(Fragment destFragment) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.add_lead_fragment, destFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
