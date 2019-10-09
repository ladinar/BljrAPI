package com.example.login;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Server;

public class LeadRegister extends AppCompatActivity implements LeadRegisterAdapter.ILeadAdapter {

    public static final String LEADS1 = "leads";
    public static final int REQUEST_CODE_EDIT = 99;
    public List<Leads> lList = new ArrayList<>();
    public List<String> SalesName, ContactName, PresalesName;
    public static String lead_id;
    public static String name_sales;
    //    SearchView searchView;
    ProgressBar progresslead;
    Spinner spinContact, spinnSales, spinPresales;
    TextView mName, mEmail, mlead, mopp, mcoba, mstatus, mAmount, tvLead, ivAssign;
    LeadRegisterAdapter leadsAdapter;
    Button btnAddlead, btnPresales;
    ArrayAdapter<String> SpinnerAdapter;
    DatePickerDialog.OnDateSetListener date;
    EditText searchText, eLead;
    FloatingActionButton fabutton;
    String cek_status;
    int itemPos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_register);

        mopp = findViewById(R.id.opty_name);
        mstatus = findViewById(R.id.mStatus);
        searchText = findViewById(R.id.search_view);
        mAmount = findViewById(R.id.maamount);
        btnPresales = findViewById(R.id.btn_presales);
        ivAssign = findViewById(R.id.iv_assign);

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

        fabutton = findViewById(R.id.fab);
        fabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addlead();
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
        intent.putExtra(LEADS1, leadsAdapter.getItem(pos));
        startActivity(intent);

    }

    @Override
    public void doEdit(int pos) {
        itemPos = pos;
        Intent intent = new Intent(LeadRegister.this, AddLeadActivity.class);
        intent.putExtra(LEADS1, leadsAdapter.getItem(pos));
        startActivityForResult(intent, REQUEST_CODE_EDIT);
    }

    @Override
    public void doAssign(int pos) {
        PresalesName = new ArrayList<String>();

        itemPos = pos;
        Leads lead = leadsAdapter.getItem(pos);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(LeadRegister.this);
        View mView = getLayoutInflater().inflate(R.layout.add_lead, null);
        mBuilder.setView(mView);

        spinPresales = mView.findViewById(R.id.spinner_presales);
        btnPresales = mView.findViewById(R.id.btn_presales);
        tvLead = mView.findViewById(R.id.lead_id);


        tvLead.setText(lead.getLead_id());
        AlertDialog dialog = mBuilder.create();
        dialog.show();

        final JSONObject jobj = new JSONObject();
        final String presales = null;
        try {
            jobj.put("presales", presales);
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
                        JSONArray jray_lead = jObj.getJSONArray("lead");
                        JSONArray jray_presales = jObj.getJSONArray("presales_list");
                        if (response.length() > 0) {


                            for (int i = 0; i < jray_presales.length(); i++) {
                                JSONObject presales = jray_presales.getJSONObject(i);
                                String presaleses = presales.getString("name");
                                PresalesName.add(presaleses);

                            }
                            Log.i(jray_presales.toString(), "onResponse: ");
                            spinPresales.setAdapter(new ArrayAdapter<String>(LeadRegister.this, android.R.layout.simple_spinner_dropdown_item, PresalesName));

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
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(strReq);

        btnPresales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assignPresales();
            }
        });

    }

    private void assignPresales() {

        final String presales = spinPresales.getSelectedItem().toString().trim();
        String lead_id = tvLead.getText().toString().trim();

        final JSONObject jobj = new JSONObject();
        try {
            jobj.put("spinner_presales", presales);
            jobj.put("lead_id", lead_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, Server.URL_assign, jobj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("response", response.toString());
                JSONObject jObj = response;

                Intent intent = getIntent();
                finish();
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Successfully Add Presales", Toast.LENGTH_LONG).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(strReq);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK) {
            Leads lead = (Leads) data.getSerializableExtra(LEADS1);
            lList.remove(itemPos);
            lList.add(itemPos, lead);
            leadsAdapter.notifyDataSetChanged();

        }
    }

//
}


