package com.example.login;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkResponse;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import util.Server;

public class LeadRegister extends AppCompatActivity {
    public List<Leads> lList = new ArrayList<>();
    public List<String> SalesName, ContactName;
    ProgressBar progresslead;
    Spinner spinContact, spinnSales;
    TextView mName, mEmail, mlead, mopp, mcoba;
    LeadRegisterAdapter leadsAdapter;
    Button btnAddlead;
    ArrayAdapter<String> SpinnerAdapter;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar;
    EditText closing_date, opp_name;
    String sales2, contact2, opp_name2, tgl2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_register);

        mopp = findViewById(R.id.opty_name);
        progresslead = findViewById(R.id.progresslead);
        btnAddlead = findViewById(R.id.addlead);
        spinnSales = findViewById(R.id.sales);
        spinContact = findViewById(R.id.contact);
        closing_date = findViewById(R.id.closing_date);


        SalesName = new ArrayList<String>();
        ContactName = new ArrayList<String>();

        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updatelabel();
            }
        };

        closing_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(LeadRegister.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnAddlead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sales2 = spinnSales.getSelectedItem().toString().trim();
                contact2 = spinContact.getSelectedItem().toString().trim();
                opp_name2 = mopp.getText().toString().trim();
                tgl2 = closing_date.getText().toString().trim();
                if (!sales2.isEmpty() && !contact2.isEmpty() && !opp_name2.isEmpty() && !tgl2.isEmpty()) {
                    storeLead();
                } else {
                    mopp.setError("Please Insert Email!");
                    closing_date.setError("Please insert Password!");
                }

            }
        });


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        tampilkanlead();

        leadsAdapter = new LeadRegisterAdapter(lList);
        recyclerView.setAdapter(leadsAdapter);
    }


    private void updatelabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        closing_date.setText(sdf.format(myCalendar.getTime()));
    }

    private void storeLead() {
        final JSONObject jobj = new JSONObject();
        try {
            jobj.put("spinContact", contact2);
            jobj.put("mopp", opp_name2);
            jobj.put("spinnSales", sales2);
            jobj.put("closing_date", tgl2);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, Server.URL_storeLead, jobj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    Log.i("response", response.toString());
                    JSONObject jObj = response;
                    String success = jObj.getString("success");

                    if (success.equals("1")) {
                        Toast.makeText(LeadRegister.this, "Lead Id Created Successfully :)", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(LeadRegister.this, "email atau password salah", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();

                    Toast.makeText(LeadRegister.this, "Ora oleh data", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                NetworkResponse response = error.networkResponse;
                String errorMsg = "";
                if (response != null && response.data != null) {
                    String errorString = new String(response.data);
                    Log.i("log error", errorString);
                }
                Toast.makeText(LeadRegister.this, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(strReq);

    }


    private void tampilkanlead() {
        final JSONObject jobj = new JSONObject();
        final String lead_id = "null";
        final String opp_name = "null";
        final String contact = "null";
        final String sales = "null";
        try {
            jobj.put("lead_id", lead_id);
            jobj.put("opp_name", opp_name);
            jobj.put("contact", contact);
            jobj.put("sales", sales);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, Server.URL_Lead, jobj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.i("response", response.toString());
                    JSONObject jObj = response;
                    String success = jObj.getString("success");
                    if (success.equals("1")) {
//                        JSONObject leads = new JSONObject();
                        JSONArray jray = jObj.getJSONArray("lead");
                        JSONArray jray_user = jObj.getJSONArray("sales_list");
                        JSONArray jray_contact = jObj.getJSONArray("contact_list");
//                        JSONObject lead = jray.getJSONObject(0);
//                        Intent intent = getIntent();
//                        String extraName = intent.getStringExtra("name");
//                        String extraEmail = intent.getStringExtra("email");

//                        mName.setText(extraName);
//                        mEmail.setText(extraEmail);

                        progresslead.setVisibility(View.GONE);
                        Log.i("response", String.valueOf(jray.length()));
//                        mlead.setVisibility(View.VISIBLE);
//                        mlead.setText(lead_id);
//                        mopp.setText(opt_name);

                        if (response.length() > 0) {
                            for (int i = 0; i < jray.length(); i++) {
                                JSONObject o = jray.getJSONObject(i);
                                Leads item = new Leads();
                                item.setLead_id(o.getString("lead_id"));
                                item.setOpp_name(o.getString("opp_name"));
                                item.setNik(o.getString("name"));
                                item.setId_customer(o.getString("customer_legal_name"));
                                Log.i("response", o.getString("opp_name"));
//                                String sales = o.getString("lead_id");
                                lList.add(item);
                            }
                            leadsAdapter.notifyDataSetChanged();

                            for (int i = 0; i < jray_user.length(); i++) {
                                JSONObject users = jray_user.getJSONObject(i);
                                String sales = users.getString("name");
                                SalesName.add(sales);
                            }

                            for (int i = 0; i < jray_contact.length(); i++) {
                                JSONObject contact = jray_contact.getJSONObject(i);
                                String contacts = contact.getString("brand_name");
                                ContactName.add(contacts);

                            }
                            spinnSales.setAdapter(new ArrayAdapter<String>(LeadRegister.this, android.R.layout.simple_spinner_dropdown_item, SalesName));

                            spinContact.setAdapter(new ArrayAdapter<String>(LeadRegister.this, android.R.layout.simple_spinner_dropdown_item, ContactName)
                            );

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


