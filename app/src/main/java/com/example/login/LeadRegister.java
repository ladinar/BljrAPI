package com.example.login;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class LeadRegister extends AppCompatActivity implements LeadRegisterAdapter.ILeadAdapter {

    public static final String LEADS1 = "leads";
    public List<Leads> lList = new ArrayList<>();
    public List<String> SalesName, ContactName, PresalesName;
    public static String lead_id;
    public static String name_sales;
    //    SearchView searchView;
    ProgressBar progresslead;
    Spinner spinContact, spinnSales, spinPresales;
    TextView mName, mEmail, mlead, mopp, mcoba, mstatus, mAmount;
    LeadRegisterAdapter leadsAdapter;
    Button btnAddlead;
    ArrayAdapter<String> SpinnerAdapter;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar;
    EditText closing_date, opp_name, searchText;
    String sales2, contact2, presales2, opp_name2, tgl2, amounts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_register);

        mopp = findViewById(R.id.opty_name);
        mstatus = findViewById(R.id.mStatus);
        progresslead = findViewById(R.id.progresslead);
        btnAddlead = findViewById(R.id.addlead);
        spinnSales = findViewById(R.id.sales);
        spinContact = findViewById(R.id.contact);
        spinPresales = findViewById(R.id.presales);
        closing_date = findViewById(R.id.closing_date);
        searchText = findViewById(R.id.search_view);
        mAmount = findViewById(R.id.maamount);

//      swipeRefreshLayout = findViewById(R.id.swiperefreshLayout);
        tampilkanlead();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        leadsAdapter = new LeadRegisterAdapter(this, lList);
        recyclerView.setAdapter(leadsAdapter);

        SalesName = new ArrayList<String>();
        ContactName = new ArrayList<String>();
        PresalesName = new ArrayList<String>();

//        mAmount.addTextChangedListener(watch);

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
//                sales2 = spinnSales.getSelectedItem().toString().trim();
//                contact2 = spinContact.getSelectedItem().toString().trim();
//                presales2 = spinPresales.getSelectedItem().toString().trim();
//                opp_name2 = mopp.getText().toString().trim();
//                tgl2 = closing_date.getText().toString().trim();
//                if (!sales2.isEmpty() && !contact2.isEmpty() && !opp_name2.isEmpty() && !tgl2.isEmpty()) {
//                    storeLead();
//                } else {
//                    mopp.setError("Please Insert Email!");
//                    closing_date.setError("Please insert Password!");
//                }
                addlead();


            }
        });

//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String queryString) {
//                {
//                    return false;
//                }
//            }
//
//            @Override
//            public boolean onQueryTextChange(String queryString) {
//                leadsAdapter.getFilter().filter(queryString);
//                Log.i(queryString, "onQueryTextSubmit: ");
//                return false;
//            }
//        });

        searchText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    private void filter(String s) {
        ArrayList<Leads> filteredList = new ArrayList<>();

        for (Leads item : lList) {
            if (item.getLead_id().toLowerCase().contains(s.toLowerCase())) {
                filteredList.add(item);
            }
        }

        leadsAdapter.filterList(filteredList);
    }


    private void addlead() {
        Intent intent = new Intent(LeadRegister.this, AddLeadActivity.class);
        startActivity(intent);
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
        final String status = "null";
        final String amount = "null";
        try {
            jobj.put("lead_id", lead_id);
            jobj.put("opp_name", opp_name);
            jobj.put("contact", contact);
            jobj.put("sales", sales);
            jobj.put("status", status);
            jobj.put("amounts", amount);
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
                        JSONArray jray_presales = jObj.getJSONArray("presales_list");
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
                                item.setClosing_date(o.getString("closing_dates"));
                                item.setResult(o.getString("results"));
                                item.setAmount(o.getString("amounts"));
                                lList.add(item);
                                Log.i(item.getResult(), "onResponse: ");
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

                            for (int i = 0; i < jray_presales.length(); i++) {
                                JSONObject presales = jray_presales.getJSONObject(i);
                                String presaleses = presales.getString("name");
                                PresalesName.add(presaleses);

                            }
                            spinnSales.setAdapter(new ArrayAdapter<String>(LeadRegister.this, android.R.layout.simple_spinner_dropdown_item, SalesName));

                            spinContact.setAdapter(new ArrayAdapter<String>(LeadRegister.this, android.R.layout.simple_spinner_dropdown_item, ContactName)
                            );

                            spinPresales.setAdapter(new ArrayAdapter<String>(LeadRegister.this, android.R.layout.simple_spinner_dropdown_item, PresalesName));

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

    @Override
    public void doClick(int pos) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(LEADS1, lList.get(pos));
        startActivity(intent);

    }

//
}


