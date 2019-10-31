package com.example.barang_hilang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barang_hilang.Adapter.lihat_data_adapter;
import com.example.barang_hilang.Model.model_lihat_data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class lihat_data_rombel extends AppCompatActivity {
    ImageView img_arrow;
    RecyclerView recyclerView;
    SweetAlertDialog pd;
    String url_get ="http://10.0.2.2/api_barang_hilang/get_data.php?table=inventors";
    List<model_lihat_data> model_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data_rombel);
        img_arrow = findViewById(R.id.img_arrow);
        recyclerView = findViewById(R.id.recycle_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        model_data = new ArrayList<>();
        getData();
        img_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(lihat_data_rombel.this, dashboard.class);
                startActivity(i);
            }
        });
    }

    private void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_get, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray user = jsonObject.getJSONArray("data");

                        for (int i = 0; i < user.length(); i++) {
                            JSONObject jsonobject = user.getJSONObject(i);
                            String nis = jsonobject.getString("nis");
                            String nama = jsonobject.getString("nama");
                            String rombel = jsonobject.getString("rombel");
                            String rayon = jsonobject.getString("rayon");
                            String item_name = jsonobject.getString("item_name");
                            String item_size = jsonobject.getString("item_size");
                            String merek = jsonobject.getString("merk");
                            String tempat = jsonobject.getString("tempat");


                            model_lihat_data lihat_data = new model_lihat_data(nis, nama, rombel,rayon, item_name,item_size,merek,tempat);
                            model_data.add(lihat_data);
                        }
                    lihat_data_adapter adapter = new lihat_data_adapter(lihat_data_rombel.this,model_data);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e){
                    Toast.makeText(lihat_data_rombel.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(lihat_data_rombel.this, "Error response" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
