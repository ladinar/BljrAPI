package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import util.Server;

public class LoginMain extends AppCompatActivity {

    EditText email,password;
    Button login;
    ProgressBar bola;
    String email2,password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        bola = findViewById(R.id.login_progress);
        email = findViewById(R.id.editText_email);
        password = findViewById(R.id.editText_password);
        login = findViewById(R.id.button_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email2 = email.getText().toString().trim();
                password2 = password.getText().toString().trim();
                if (!email2.isEmpty() && !password2.isEmpty()){
                    handlekoneksilogin(email2,password2);
                } else {
                    email.setError("Please Insert Email!");
                    password.setError("Please insert Password!");
                }

            }

        });

    }

    private void doClick(String name, String email) {
        Intent intent = new Intent(LoginMain.this, LeadRegister.class);
        intent.putExtra("name", name);
        intent.putExtra("email", email);
        startActivity(intent);
    }


    public void handlekoneksilogin(final String email, final String password) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", email);
            jsonBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //nggwe jsobobject request ae yo
        //cobaen sek wes
        //okee
        //bedone penggunaan jsonobject mbe stringrequest opo e syad?
        //kan maeng ndek postman nggwe json, cek iki iso pisan dikirim json e, nggwe jsonobjectreq
        //syad error, melbu ne error nde error reso
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, Server.URL, jsonBody, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.i("response", response.toString());
                    JSONObject jObj = response;
                    String success = jObj.getString("success");

                    if (success.equals("1")) {

                        JSONObject user = jObj.getJSONObject("users");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String password = user.getString("password");

                        Toast.makeText(LoginMain.this, "Hallo! " + name + " Loginmu bener :)", Toast.LENGTH_LONG).show();
                        bola.setVisibility(View.VISIBLE);
                        login.setVisibility(View.GONE);
                        doClick(email, name);
                    } else {
                        Toast.makeText(LoginMain.this, "email atau password salah", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();

                    Toast.makeText(LoginMain.this, "Ora oleh data" , Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //model e kyok console.log?
                //iyo, iku error e knopo syad?
                //timeout, eh ksuwen reponse time e
                //wess coba maneh
                //sek error? , iyoo syad
                // awamu wes sak jaringan? oiyo astagfirullah -_-
                //error e kok mundak akeh syad
                error.printStackTrace();
                NetworkResponse response = error.networkResponse;
                String errorMsg = "";
                if(response != null && response.data != null){
                    String errorString = new String(response.data);
                    Log.i("log error", errorString);
                }
                Toast.makeText(LoginMain.this, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(strReq);
    }
}
