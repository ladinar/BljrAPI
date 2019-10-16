package com.example.login;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.login.model.Leads;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import util.Server;


public class TenderFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    Button btn_submit_tp;
    EditText etno_doc, etsubmit_price, etdeal_price, etproject_name, submit_date, etLead, etquote;
    Spinner spinproject_class, spinwin_prob;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar;
    String etLead2, etno_doc2, etsubmit_price2, etdeal_price2, etproject_name2, etsubmit_date2, etquote2, etproject_class, etwin_prob;


    public TenderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tender, container, false);
        Leads lead = (Leads) getActivity().getIntent().getSerializableExtra(LeadRegister.LEADS1);
        btn_submit_tp = view.findViewById(R.id.btn_submit_tp);
        submit_date = view.findViewById(R.id.edittext_submit_date);
        etLead = view.findViewById(R.id.edit_lead_id_fragment);
        etLead.setText(lead.getLead_id());
        etLead2 = etLead.getText().toString().trim();
        etLead.setVisibility(View.GONE);
        etno_doc = view.findViewById(R.id.edittext_no_doc);
        etsubmit_price = view.findViewById(R.id.edittext_submit_price);
        etsubmit_price.addTextChangedListener(onTextChangedListener());
        spinproject_class = view.findViewById(R.id.spinner_project_class);
        etdeal_price = view.findViewById(R.id.edittext_deal_price);
        etdeal_price.addTextChangedListener(onTextChangedListener());
        spinwin_prob = view.findViewById(R.id.spinner_win_prob);
        etproject_name = view.findViewById(R.id.edittext_project_name);
        etquote = view.findViewById(R.id.edittext_quote);

        btn_submit_tp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etno_doc2 = etno_doc.getText().toString().trim();
                etsubmit_price2 = etsubmit_price.getText().toString().trim();
                etdeal_price2 = etdeal_price.getText().toString().trim();
                etproject_name2 = etproject_name.getText().toString().trim();
                etquote2 = etquote.getText().toString().trim();
                etproject_class = spinproject_class.getSelectedItem().toString().trim();
                etwin_prob = spinwin_prob.getSelectedItem().toString().trim();
                updatetp();
            }
        });

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
        submit_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        return view;
    }


    private void updatetp() {
        final JSONObject jobj = new JSONObject();
        try {
            jobj.put("etno_doc", etno_doc2);
            jobj.put("etsubmit_price", etsubmit_price2);
            jobj.put("submit_date", etsubmit_date2);
            jobj.put("spinproject_class", etproject_class);
            jobj.put("spinwin_prob", etwin_prob);
            jobj.put("etquote", etquote2);
            jobj.put("etdeal_price", etdeal_price2);
            jobj.put("etLead", etLead2);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, Server.URL_update_tp, jobj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("response", response.toString());
                JSONObject jObj = response;
                String success = null;
                try {
                    success = jObj.getString("success");

                    if (success.equals("1")) {
                        Intent intent = new Intent(getActivity(), LeadRegister.class);
                        Toast.makeText(getActivity(), "Tender Process Updated Successfully :)", Toast.LENGTH_LONG).show();
                        startActivity(intent);

                    } else {
                        Toast.makeText(getActivity(), "salah!", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(getActivity(), "Ora oleh data", Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
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

    private void updatelabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        submit_date.setText(sdf.format(myCalendar.getTime()));
    }

    private TextWatcher onTextChangedListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etsubmit_price.removeTextChangedListener(this);
                etdeal_price.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }

                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    etsubmit_price.setText(formattedString);
                    etsubmit_price.setSelection(etsubmit_price.getText().length());
                    etdeal_price.setText(formattedString);
                    etdeal_price.setSelection(etdeal_price.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                etsubmit_price.addTextChangedListener(this);
                etdeal_price.addTextChangedListener(this);
            }
        };
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
