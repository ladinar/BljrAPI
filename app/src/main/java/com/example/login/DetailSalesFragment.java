package com.example.login;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.example.login.model.Leads;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import util.Server;

public class DetailSalesFragment extends Fragment {
    TextView tvLead, tvOppName, tvLead2, tvpresales;
    String lead_edit, etOppName2, etLead2, etassesment2, etproposed2, etproof2, etproject_budget2, etpriority, etproject_size, etNik2;
    EditText etLead, etassesment, etproposed, etproject_budget, etproof, etNik;
    Spinner spinnerPriority, spinnerProjectSize;
    Button btnSubmitsd, btnTp;
    HorizontalStepView horizontalsStepView;
    List<StepBean> sources = new ArrayList<>();
    private OnFragmentInteractionListener mListener;

    public DetailSalesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_sales, container, false);

        Leads lead = (Leads) getActivity().getIntent().getSerializableExtra(LeadRegister.LEADS1);
        tvLead = view.findViewById(R.id.edit_lead_fragment);
        tvLead2 = view.findViewById(R.id.lead_detail);
        tvLead.setText(lead.getLead_id());
        tvLead2.setText(lead.getLead_id());

        tvOppName = view.findViewById(R.id.edit_opp_name_fragment);
        tvOppName.setText(lead.getOpp_name());
        tvpresales = view.findViewById(R.id.presales_detail_lead);
        etLead = view.findViewById(R.id.edit_lead_id_fragment);
        etLead.setText(lead.getLead_id());
        etLead2 = etLead.getText().toString().trim();
        etLead.setVisibility(View.GONE);
        etassesment = view.findViewById(R.id.edit_assesment);
        etproposed = view.findViewById(R.id.edit_proposed);
        etproof = view.findViewById(R.id.edit_proof);
        etproject_budget = view.findViewById(R.id.edit_project_budget);
        etproject_budget.addTextChangedListener(onTextChangedListener());
        spinnerPriority = view.findViewById(R.id.spinner_priority);
        spinnerProjectSize = view.findViewById(R.id.spinner_project_size);
        btnSubmitsd = view.findViewById(R.id.btn_submit_sd);
        btnSubmitsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etassesment2 = etassesment.getText().toString().trim();
                etproposed2 = etproposed.getText().toString().trim();
                etproof2 = etproof.getText().toString();
                etproject_budget2 = etproject_budget.getText().toString().trim().replaceAll(",", "");
                etpriority = spinnerPriority.getSelectedItem().toString().trim();
                etproject_size = spinnerProjectSize.getSelectedItem().toString().trim();
                updatesd();
            }
        });

        btnTp = view.findViewById(R.id.btn_tp);
        etNik = view.findViewById(R.id.edit_nik_fragment);
        etNik.setVisibility(View.GONE);
        /*etNik.setText(lead.getNik());
        etNik2 = etNik.getText().toString().trim();*/

//        tampilsd();

        tampilkanpresales();

        horizontalsStepView = view.findViewById(R.id.horizontalStepview);
        Log.i(lead.getResult(), "onCreateView: ");
        if (lead.getResult().equals("INITIAL")) {
            sources.add(new StepBean("Initial", 0));
            sources.add(new StepBean("Open", -1));
            sources.add(new StepBean("SD", -1));
            sources.add(new StepBean("TP", -1));
            sources.add(new StepBean("Win/Lose", -1));
        } else if (lead.getResult().equals("OPEN")) {
            sources.add(new StepBean("Initial", 1));
            sources.add(new StepBean("Open", 0));
            sources.add(new StepBean("SD", -1));
            sources.add(new StepBean("TP", -1));
            sources.add(new StepBean("Win/Lose", -1));
        } else if (lead.getResult().equals("SOLUTION DESIGN")) {
            sources.add(new StepBean("Initial", 1));
            sources.add(new StepBean("Open", 1));
            sources.add(new StepBean("SD", 0));
            sources.add(new StepBean("TP", -1));
            sources.add(new StepBean("Win/Lose", -1));
        } else if (lead.getResult().equals("TENDER PROCESS")) {
            sources.add(new StepBean("Initial", 1));
            sources.add(new StepBean("Open", 1));
            sources.add(new StepBean("SD", 1));
            sources.add(new StepBean("TP", 0));
            sources.add(new StepBean("Win/Lose", -1));
        } else if (lead.getResult().equals("WIN")) {
            sources.add(new StepBean("Initial", 1));
            sources.add(new StepBean("Open", 1));
            sources.add(new StepBean("SD", 1));
            sources.add(new StepBean("TP", 1));
            sources.add(new StepBean("Win", 1));
        } else if (lead.getResult().equals("LOSE")) {
            sources.add(new StepBean("Initial", 1));
            sources.add(new StepBean("Open", 1));
            sources.add(new StepBean("SD", 1));
            sources.add(new StepBean("TP", 1));
            sources.add(new StepBean("Lose", 1));
        }


//        sources.add(new StepBean("Initial",1));
//        sources.add(new StepBean("Open",1));
//        sources.add(new StepBean("SD",1));
//        sources.add(new StepBean("TP",0));
//        sources.add(new StepBean("Win/Lose",-1));

        horizontalsStepView.setStepViewTexts(sources)
                .setTextSize(10)
                .setStepsViewIndicatorCompletedLineColor(Color.parseColor("#FFFF00"))
                .setStepViewComplectedTextColor(Color.parseColor("#FFFF00"))
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(getActivity(), R.color.uncompleted_text_color))
                .setStepsViewIndicatorUnCompletedLineColor(Color.parseColor("#FFFFFF"))
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getActivity(), R.drawable.complted))
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getActivity(), R.drawable.attention))
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getActivity(), R.drawable.default_icon));

        return view;
    }

    private void tampilsd() {
        final JSONObject jobj = new JSONObject();
        final String presales = "null";
        try {

            jobj.put("spinnerProjectSize", etproject_size);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, Server.URL_update_sd, jobj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("response", response.toString());
                JSONObject jObj = response;
                String success = null;
                try {
                    success = jObj.getString("success");

                    if (success.equals("1")) {
                        Toast.makeText(getActivity(), "Solution Design Updated Successfully :)", Toast.LENGTH_LONG).show();

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

    private void tampilkanpresales() {
        final JSONObject jobj = new JSONObject();
        final String presales = "null";
        try {
            jobj.put("etLead", etLead2);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, Server.URL_detail_lead, jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.i("response", response.toString());
                    JSONObject jObj = response;
                    String success = jObj.getString("success");
                    if (success.equals("1")) {
                        JSONObject presales = jObj.getJSONObject("presales_detail");
                        tvpresales.setText(presales.getString("name"));
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
                params.put("lead_id", presales);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(strReq);
    }

    private void updatesd() {
        final JSONObject jobj = new JSONObject();
        try {
            jobj.put("etassesment", etassesment2);
            jobj.put("etproposed", etproposed2);
            jobj.put("etproof", etproof2);
            jobj.put("etproject_budget", etproject_budget2);
            jobj.put("spinnerPriority", etpriority);
            jobj.put("spinnerProjectSize", etproject_size);
            jobj.put("etLead", etLead2);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, Server.URL_update_sd, jobj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("response", response.toString());
                JSONObject jObj = response;
                String success = null;
                try {
                    success = jObj.getString("success");

                    if (success.equals("1")) {
                        Toast.makeText(getActivity(), "Solution Design Updated Successfully :)", Toast.LENGTH_LONG).show();

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
                etproject_budget.removeTextChangedListener(this);

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
                    etproject_budget.setText(formattedString);
                    etproject_budget.setSelection(etproject_budget.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                etproject_budget.addTextChangedListener(this);
            }
        };
    }
}
