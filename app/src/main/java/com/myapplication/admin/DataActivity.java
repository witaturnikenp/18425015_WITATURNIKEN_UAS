package com.myapplication.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.myapplication.Adapter.AdapterBarang;
import com.myapplication.R;
import com.myapplication.Server.BaseUrl;
import com.myapplication.model.ModelBarang;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataActivity extends AppCompatActivity {
    ProgressDialog pDialog;

    AdapterBarang adapter;
    ListView list;

    ArrayList<ModelBarang> newsList = new ArrayList<ModelBarang>();
    private RequestQueue mRequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        getSupportActionBar().setTitle("Data Barang");
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list);
        newsList.clear();
        adapter = new AdapterBarang(DataActivity.this, newsList);
        list.setAdapter(adapter);
        getAllBuku();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(DataActivity.this, DashboardAdminActivity.class);
        startActivity(i);
        finish();
    }

    private void getAllBuku() {
        // Pass second argument as "null" for GET requests
        pDialog.setMessage("Loading");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, BaseUrl.DataBarang, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");
                            if (status == false) {
                                Log.d("data barang = ", response.toString());
                                String data = response.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    final ModelBarang barang = new ModelBarang();
                                    final String _id = jsonObject.getString("_id");
                                    final String namaBarang = jsonObject.getString("namaBarang");
                                    final String hargaBarang = jsonObject.getString("hargaBarang");
                                    final String stok = jsonObject.getString("stok");
                                    final String kategori = jsonObject.getString("kategori");
                                    final String image = jsonObject.getString("image");
                                    Log.e("Image", image);
                                    barang.setNamaBarang(namaBarang);
                                    barang.setHargaBarang(hargaBarang);
                                    barang.setKategori(kategori);
                                    barang.setStok(stok);
                                    barang.setImage(image);
                                    barang.set_id(_id);

                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            // TODO Auto-generated method stub
                                            Intent a = new Intent(DataActivity.this, EditdanHapusActivity.class);
                                            a.putExtra("namaBarang", newsList.get(position).getNamaBarang());
                                            a.putExtra("_id", newsList.get(position).get_id());
                                            a.putExtra("hargaBarang", newsList.get(position).getHargaBarang());
                                            a.putExtra("stok", newsList.get(position).getStok());
                                            a.putExtra("kategori", newsList.get(position).getKategori());
                                            a.putExtra("image", newsList.get(position).getImage());
                                            startActivity(a);
                                        }
                                    });
                                    newsList.add(barang);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
            }
        });

        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}