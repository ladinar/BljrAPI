package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.login.Adapter.LeadRegisterAdapter;
import com.example.login.model.Leads;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Server;

public class LeadRegister extends AppCompatActivity {
    public List<Leads> lList = new ArrayList<>();
    ProgressBar progresslead;
    TextView mName, mEmail, mlead, mopp, mcoba;
    LeadRegisterAdapter leadsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_register);
        mlead = findViewById(R.id.mlead);
        mcoba = findViewById(R.id.mcobalead);
        mopp = findViewById(R.id.mopp);
        mName = findViewById(R.id.mNama);
        mEmail = findViewById(R.id.mEmail);
        progresslead = findViewById(R.id.progresslead);

        mlead.setVisibility(View.GONE);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        tampilkanlead();

        leadsAdapter = new LeadRegisterAdapter(lList);
        recyclerView.setAdapter(leadsAdapter);
    }

    private void tampilkanlead() {
        final JSONObject jobj = new JSONObject();
        final String lead_id = "null";
        final String opp_name = "null";
        try {
            jobj.put("lead_id", lead_id);
            jobj.put("opp_name", opp_name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, Server.URL_Lead, jobj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.i("response", response.toString());
                    JSONObject jObj = response;
                    String success = jObj.getString("success");
                    if (success.equals("1")) {
                        JSONObject leads = new JSONObject();
                        JSONArray jray = jObj.getJSONArray("lead");
                        JSONObject lead = jray.getJSONObject(0);
                        String lead_id = lead.getString("lead_id");
                        String opt_name = lead.getString("opp_name");

                        Intent intent = getIntent();
                        String extraName = intent.getStringExtra("name");
                        String extraEmail = intent.getStringExtra("email");

                        mName.setText(extraName);
                        mEmail.setText(extraEmail);

                        progresslead.setVisibility(View.GONE);
                        Log.i("response", String.valueOf(jray.length()));
                        mlead.setVisibility(View.VISIBLE);
                        mlead.setText(lead_id);
                        mopp.setText(opt_name);

                        if (response.length() > 0) {
                            for (int i = 0; i < jray.length(); i++) {
                                JSONObject o = jray.getJSONObject(i);
                                Leads item = new Leads();
                                item.setLead_id(o.getString("lead_id"));
                                item.setOpp_name(o.getString("opp_name"));
                                Log.i("response", o.getString("opp_name"));
                                lList.add(item);
                            }
                            leadsAdapter.notifyDataSetChanged();
                            //wes,
                            //ohh bedone construktor mbe getter setter ngono
                            //ngko keroso nek project gede dan butuh variabel akeh
                            //nek ngene iso gak kabeh variable mbuk isi
                            //ngunu lah
                            //method e post update delete ngno yo nggae getter setter syad?
                            //sng ndi iku
                            //urung nggae sih, iki sek view
                            //iki gaono hubungane mbe api, cuman yoopo carane  nggwe dan nganggo model
                            //oh brrti pokok model getter setter luwih better opo pun BE nd api ne
                            //yo ngunu lah nek pahammu ngono
                            //terus, asline ono cara luwih gampang maneh, dadi otomatis json mu dadi list
                            //tapi kapan2 ae cek ruh basic e sek
                            //oke syad, sipoke
                            //uwes yo mene maneh nek kurang
                            //tak tutup iki, oke
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("lead_id", lead_id);
                params.put("opp_name", opp_name);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(strReq);
    }

}


