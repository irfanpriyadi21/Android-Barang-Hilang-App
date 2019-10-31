package com.example.barang_hilang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class input_data extends AppCompatActivity {
    TextInputEditText nis,nama,rombel,rayon,item_size,merek,tempat,nama_barang;
    Button btn_simpan;
    SweetAlertDialog pd;
    ProgressBar loading;
    ImageView img_arrow;
    String URL_POST = "http://10.0.2.2/api_barang_hilang/insert_data.php?table=inventors&field=nis,nama,rombel,rayon,item_name,item_size,merk,tempat,status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);
        img_arrow = findViewById(R.id.img_arrow);
        loading = findViewById(R.id.loading);
        nis = findViewById(R.id.nis);
        nama = findViewById(R.id.nama);
        rombel = findViewById(R.id.rombel);
        rayon = findViewById(R.id.rayon);
        item_size = findViewById(R.id.item_size);
        merek = findViewById(R.id.merek);
        tempat = findViewById(R.id.tempat);
        nama_barang = findViewById(R.id.nama_barang);
        btn_simpan = findViewById(R.id.btn_simpan);
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mNis = nis.getText().toString().trim();
                String mNama = nama.getText().toString().trim();
                String mRombel = rombel.getText().toString().trim();
                String mRayon = rayon.getText().toString().trim();
                String mItem_size = rayon.getText().toString().trim();
                String mMerek = merek.getText().toString().trim();
                String mTempat = tempat.getText().toString().trim();
                String mNama_Barang = nama_barang.getText().toString().trim();

                if (mNis.isEmpty() || mNama.isEmpty() || mRombel.isEmpty() || mRayon.isEmpty() || mItem_size.isEmpty() || mMerek.isEmpty() || mTempat.isEmpty() || mNama_Barang.isEmpty()) {
                    pd = new SweetAlertDialog(input_data.this,SweetAlertDialog.WARNING_TYPE);
                    pd.setTitleText("Oops Error");
                    pd.setContentText("Data Tidak Boleh Kosong");
                    pd.show();
                } else {
                    Simpan(mNis,mNama,mRombel,mRayon,mItem_size,mMerek,mTempat,mNama_Barang);
                }
            }
        });
        img_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(input_data.this, dashboard.class);
                startActivity(i);
            }
        });
    }

    private void Simpan(final String mNis, final String mNama, final String mRombel, final String mRayon, final String mItem_size, final String mMerek, final String mTempat, final String mNama_Barang) {
        loading.setVisibility(View.VISIBLE);
        btn_simpan.setVisibility(View.GONE);
        StringRequest request = new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
//                          String code = jsonObject.getString("code");
                    String message = jsonObject.getString("status");
                    pd = new SweetAlertDialog(input_data.this,SweetAlertDialog.SUCCESS_TYPE);
                    pd.setTitleText("Data Berhasil Ditambahkan !!");
                    pd.setContentText(message);
                    pd.show();
                    loading.setVisibility(View.GONE);
                    btn_simpan.setVisibility(View.VISIBLE);
                }catch (JSONException e) {
                    e.printStackTrace();
                    loading.setVisibility(View.GONE);
                    btn_simpan.setVisibility(View.VISIBLE);
                    pd = new SweetAlertDialog(input_data.this,SweetAlertDialog.ERROR_TYPE);
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
                        btn_simpan.setVisibility(View.VISIBLE);
                        pd = new SweetAlertDialog(input_data.this,SweetAlertDialog.ERROR_TYPE);
                        pd.setTitleText("Terjadi Kesalahan !!");
                        pd.setContentText(error.toString());
                        pd.show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> p = new HashMap<>();
                p.put("nis", mNis);
                p.put("nama", mNama);
                p.put("rombel",mRombel);
                p.put("rayon",mRayon);
                p.put("item_name",mNama_Barang);
                p.put("item_size",mItem_size);
                p.put("merk",mMerek);
                p.put("tempat",mTempat);
                p.put("status","2");
                return  p;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
