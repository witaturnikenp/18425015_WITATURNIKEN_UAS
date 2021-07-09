package com.myapplication.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.myapplication.Adapter.AdapterTransaksi;
import com.myapplication.R;
import com.myapplication.Server.BaseUrl;
import com.myapplication.model.ModelTransaksi;
import com.myapplication.session.PrefSetting;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoriTransaksiActivity extends AppCompatActivity {

    ProgressDialog pDialog;
    String idUser;

    AdapterTransaksi adapter;
    ListView list;

    ArrayList<ModelTransaksi> newsList = new ArrayList<ModelTransaksi>();
    private RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histori_transaksi);

        idUser = String.valueOf(PrefSetting.username);

        getSupportActionBar().setTitle("History Transaksi");
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list_history);
        newsList.clear();
        adapter = new AdapterTransaksi(HistoriTransaksiActivity.this, newsList);
        list.setAdapter(adapter);
        getAllTransaksi();
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(HistoriTransaksiActivity.this, DashboarduserActivity.class);
        startActivity(i);
        finish();
    }

    private void getAllTransaksi() {
        // Pass second argument as "null" for GET requests
        pDialog.setMessage("Loading");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, BaseUrl.DataTransaksi + idUser, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");
                            if (status == false) {
                                Log.e("data barang = ", response.toString());
                                String data = response.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    final ModelTransaksi transaksi = new ModelTransaksi();
                                    final String _id = jsonObject.getString("_id");
                                    final String namaBarang = jsonObject.getString("NamaBarang");
                                    final String hargaBarang = jsonObject.getString("HargaBarang");
                                    final String jumlahBarang = jsonObject.getString("jumlahBarang");
                                    final String Status = jsonObject.getString("Status");
                                    final String Total = jsonObject.getString("Total");
                                    final String username = jsonObject.getString("username");
                                    transaksi.setNamaUser(PrefSetting.nama);
                                    transaksi.setNamaBarang(namaBarang);
                                    transaksi.setHarga(hargaBarang);
                                    transaksi.setJumlah(jumlahBarang);
                                    transaksi.setStatus(Status);
                                    transaksi.setTotal(Total);
                                    transaksi.set_Id(username);

//                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                        @Override
//                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                            // TODO Auto-generated method stub
//                                            Intent a = new Intent(HistoryTransaksiPembeli.this, EditBukuDanHapusActivity.class);
//                                            a.putExtra("namaBarang", newsList.get(position).getNamaBarang());
//                                            a.putExtra("_id", newsList.get(position).get_id());
//                                            a.putExtra("hargaBarang", newsList.get(position).getHargaBarang());
//                                            a.putExtra("stok", newsList.get(position).getStok());
//                                            a.putExtra("kategori", newsList.get(position).getKategori());
//                                            a.putExtra("image", newsList.get(position).getImage());
//                                            startActivity(a);
//                                        }
//                                    });
                                    newsList.add(transaksi);
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