package com.example.barang_hilang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class register extends AppCompatActivity {
    TextInputEditText name,username,password;
    Button btn_register,btn_login;
    SweetAlertDialog pd;
    ProgressBar loading;
    String URL_POST = "http://10.0.2.2/api_barang_hilang/insert_data.php?table=users&field=name,username,password";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loading = findViewById(R.id.loading);
        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(register.this, MainActivity.class);
                startActivity(i);
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mName = name.getText().toString().trim();
                String mUsername = username.getText().toString().trim();
                String mPassword = password.getText().toString().trim();

                if (mName.isEmpty() || mUsername.isEmpty() || mPassword.isEmpty()) {
                    pd = new SweetAlertDialog(register.this,SweetAlertDialog.WARNING_TYPE);
                    pd.setTitleText("Oops Error");
                    pd.setContentText("Data Tidak Boleh Kosong");
                    pd.show();
                } else {
                    Simpan(mName,mUsername,mPassword);
                }
            }
        });
    }

    private void Simpan(final String mName, final String mUsername, final String mPassword) {
        loading.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.GONE);
        btn_register.setVisibility(View.GONE);
        StringRequest request = new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
//                          String code = jsonObject.getString("code");
                    String message = jsonObject.getString("status");
                    pd = new SweetAlertDialog(register.this,SweetAlertDialog.SUCCESS_TYPE);
                    pd.setTitleText("Data Berhasil Ditambahkan !!");
                    pd.setContentText(message);
                    pd.show();
                    loading.setVisibility(View.GONE);
                    btn_login.setVisibility(View.VISIBLE);
                    btn_register.setVisibility(View.VISIBLE);
                }catch (JSONException e) {
                    e.printStackTrace();
                    loading.setVisibility(View.GONE);
                    btn_login.setVisibility(View.VISIBLE);
                    pd = new SweetAlertDialog(register.this,SweetAlertDialog.ERROR_TYPE);
                    pd.setTitleText("Terjadi Kesalahan !!");
                    pd.setContentText(e.toString());
                    pd.show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        btn_login.setVisibility(View.VISIBLE);
                        btn_register.setVisibility(View.VISIBLE);
                        pd = new SweetAlertDialog(register.this,SweetAlertDialog.ERROR_TYPE);
                        pd.setTitleText("Terjadi Kesalahan !!");
                        pd.setContentText(error.toString());
                        pd.show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> p = new HashMap<>();
                p.put("name", mName);
                p.put("username", mUsername);
                p.put("password",mPassword);
                return  p;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
