package com.example.login;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import util.Server;


public class AddLeadFragment extends Fragment {

    public List<String> SalesName, ContactName;
    OnFragmentInteractionListener mListener;
    Spinner spinContact, spinnSales;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar;
    EditText closingdate, opp_name, amount, info;
    Button btn_submit;
    String sales2, contact2, opp_name2, tgl2, amount2, info2;

    public AddLeadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_lead, container, false);
        spinContact = view.findViewById(R.id.spinner_contact);
        spinnSales = view.findViewById(R.id.spinner_sales);
        closingdate = view.findViewById(R.id.edittext_select_date);
        btn_submit = view.findViewById(R.id.btn_submit);
        opp_name = view.findViewById(R.id.textview_enter_oppty);
        amount = view.findViewById(R.id.edittext_enter_amount);
        info = view.findViewById(R.id.edittext_info);

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

        closingdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sales2 = spinnSales.getSelectedItem().toString().trim();
                contact2 = spinContact.getSelectedItem().toString().trim();
                opp_name2 = opp_name.getText().toString().trim();
                tgl2 = closingdate.getText().toString().trim();
                amount2 = amount.getText().toString().trim();
                info2 = info.getText().toString().trim();
                if (!sales2.isEmpty() && !contact2.isEmpty() && !opp_name2.isEmpty() && !tgl2.isEmpty() && !info2.isEmpty() && !amount2.isEmpty()) {
                    storeLead();
                } else {
                    opp_name.setError("Please Insert Email!");
                    closingdate.setError("Please insert Password!");
                }
            }
        });
        return view;


    }

    private void storeLead() {
        final JSONObject jobj = new JSONObject();
        try {
            jobj.put("spinContact", contact2);
            jobj.put("mopp", opp_name2);
            jobj.put("spinnSales", sales2);
            jobj.put("closing_date", tgl2);
            jobj.put("amount", amount2);
            jobj.put("info", info2);

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
                        Toast.makeText(getActivity(), "Lead Id Created Successfully :)", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "email atau password salah", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();

                    Toast.makeText(getActivity(), "Ora oleh data", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(strReq);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SalesName = new ArrayList<String>();
        ContactName = new ArrayList<String>();
        tampilkansales();

    }

    private void updatelabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        closingdate.setText(sdf.format(myCalendar.getTime()));
    }

    private void tampilkansales() {
        final JSONObject jobj = new JSONObject();
        final String contact = "null";
        final String sales = "null";
        try {
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
                        JSONArray jray_user = jObj.getJSONArray("sales_list");
                        JSONArray jray_contact = jObj.getJSONArray("contact_list");

                        if (response.length() > 0) {

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
                            spinnSales.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, SalesName));

                            spinContact.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, ContactName)
                            );
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(strReq);
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}