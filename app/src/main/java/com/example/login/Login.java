package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.AppController;
import util.Server;

public class Login extends AppCompatActivity {

    EditText email,password;
    Button login;
    String email2,password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        email = findViewById(R.id.editText_email);
        password = findViewById(R.id.editText_password);
        login = findViewById(R.id.button_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email2 = email.getText().toString().trim();
                password2 = password.getText().toString().trim();
                if (!email2.isEmpty() || !password2.isEmpty()){
                    handlekoneksilogin(email2,password2);
                } else {
                    email.setError("Please Insert Email!");
                    password.setError("Please insert Password!");
                }

            }
        });

    }

    public void handlekoneksilogin(final String email, final String password) {

        StringRequest strReq = new StringRequest(Request.Method.POST, Server.URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    String success = jObj.getString("success");
                    JSONArray jsonArray = jObj.getJSONArray("login");

                    if (success.equals("1")) {

                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String email = jObj.getString("email");
                            String password = jObj.getString("password");

                            Toast.makeText(Login.this, "Loginmu bener", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(Login.this, "email atau password salah", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    Toast.makeText(Login.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", "view");
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(strReq);
    }
}
