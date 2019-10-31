package com.example.barang_hilang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {
    TextInputEditText username, password;
    Button login, Register;
    private long backPressedTime;
    SweetAlertDialog pd;
    private ProgressBar loading;
    SessionManager sessionManager;
//    private static String URL_LOGIN = "http://192.168.154.2/api_barang_hilang/login.php";
    private static String URL_LOGIN = "http://10.0.2.2/api_barang_hilang/login.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionManager = new SessionManager(this);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.btn_login);
        loading = findViewById(R.id.loading);
        Register = findViewById(R.id.btn_register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mUsername = username.getText().toString().trim();
                String mPassword = password.getText().toString().trim();

                if(mUsername.isEmpty() || mPassword.isEmpty()){
                    pd = new SweetAlertDialog(MainActivity.this,SweetAlertDialog.WARNING_TYPE);
                    pd.setTitleText("Ooops Error");
                    pd.setContentText("Silahkan Masukan Username dan Password Anda");
                    pd.show();
                }else{
                    Login(mUsername,mPassword);
                }
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, register.class);
                startActivity(i);
            }
        });
    }
    @Override
    public void onBackPressed(){
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            finish();
        }else{
            Toast.makeText(getBaseContext(),"Press Back Again To Exit", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    private void Login(final String mUsername, final String mPassword) {
        loading.setVisibility(View.VISIBLE);
        login.setVisibility(View.GONE);
        Register.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    if(status.equals("success")){
                        JSONObject user =  new JSONObject(jsonObject.getString("data"));
                        String id = user.getString("id");
                        String name = user.getString("name");
                        String username = user.getString("username");
                        String password = user.getString("password");

                        SessionManager.createSession(name);

                        pd = new SweetAlertDialog(MainActivity.this,SweetAlertDialog.SUCCESS_TYPE);
                        pd.setTitleText("Success Login");
                        pd.setContentText(message);
                        pd.show();
                        loading.setVisibility(View.GONE);
                        Register.setVisibility(View.VISIBLE);
                        login.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent i = new Intent(MainActivity.this, dashboard.class);
                                startActivity(i);
                            }
                        },1500);//1,5 detik
                    }else{
                        pd = new SweetAlertDialog(MainActivity.this,SweetAlertDialog.ERROR_TYPE);
                        pd.setTitleText(message);
                        pd.show();
                        loading.setVisibility(View.GONE);
                        login.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    loading.setVisibility(View.GONE);
                    login.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "Error"+e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd = new SweetAlertDialog(MainActivity.this,SweetAlertDialog.ERROR_TYPE);
                        pd.setTitleText("Terjadi Kesalahan !!");
                        pd.setContentText(error.toString());
                        pd.show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",mUsername);
                params.put("password",mPassword);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
