package com.example.login;

import android.os.Bundle;
import android.widget.TextView;

public class LeadRegister extends LoginMain {

    TextView mName, mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_register);

        mName = findViewById(R.id.mNama);
        mEmail = findViewById(R.id.mEmail);

        mName.setText("masih text biasa");

    }
}
