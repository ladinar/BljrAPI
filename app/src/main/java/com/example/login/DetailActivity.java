package com.example.login;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        /*Leads lead = (Leads) getIntent().getSerializableExtra(LeadRegister.LEADS1);
        setTitle(lead.getLead_id());
        TextView tvLead = findViewById(R.id.lead_id);
        tvLead.setText(lead.getLead_id());
        TextView tvOppName = findViewById(R.id.opty_name);
        tvOppName.setText(lead.getOpp_name());*/

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        toolbar.setnavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
    }
}
