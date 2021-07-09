package com.myapplication.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myapplication.Adapter.AdapterBarang;
import com.myapplication.R;
import com.myapplication.Server.BaseUrl;
import com.myapplication.model.ModelBarang;
import com.myapplication.session.PrefSetting;
import com.myapplication.session.SessionManager;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboarduserActivity extends AppCompatActivity {
    ProgressDialog pDialog;

    AdapterBarang adapter;
    ListView list;

    ArrayList<ModelBarang> newsList = new ArrayList<ModelBarang>();
    private RequestQueue mRequestQueue;

    FloatingActionButton floatingExit;

    SessionManager session;
    SharedPreferences prefs;
    PrefSetting prefSetting;
    TextView txUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboarduser);
        Log.e("nama", PrefSetting.nama);
        txUser = (TextView) findViewById(R.id.txtUser);
        txUser.setText(PrefSetting.nama);

        getSupportActionBar().hide();
        ButterKnife.bind(this);


        prefSetting = new PrefSetting(this);
        prefs = prefSetting.getSharePreferences();

        session = new SessionManager(DashboarduserActivity.this);

        prefSetting.isLogin(session, prefs);

        getSupportActionBar().setTitle("Data Barang");
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list);


        newsList.clear();
        adapter = new AdapterBarang(DashboarduserActivity .this, newsList);
        list.setAdapter(adapter);
        getAllBuku();


//        txtNama = (TextView) findViewById(R.id.txtNama);
//        txtNama.setText(PrefSetting.namaLengkap);

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
                                    final String kategori = jsonObject.getString("kategori");
                                    final String stok = jsonObject.getString("stok");
                                    final String gamabr = jsonObject.getString("image");
                                    barang.setHargaBarang(hargaBarang);
                                    barang.setNamaBarang(namaBarang);
                                    barang.setKategori(kategori);
                                    barang.setStok(stok);
                                    barang.setImage(gamabr);
                                    barang.set_id(_id);

                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            // TODO Auto-generated method stub
                                            Intent a = new Intent(DashboarduserActivity.this, DetailActivity.class);
                                            a.putExtra("hargaBarang", newsList.get(position).getHargaBarang());
                                            a.putExtra("_id", newsList.get(position).get_id());
                                            a.putExtra("namaBarang", newsList.get(position).getNamaBarang());
                                            a.putExtra("kategori", newsList.get(position).getKategori());
                                            a.putExtra("stok", newsList.get(position).getStok());
                                            a.putExtra("gambar", newsList.get(position).getImage());
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

    @OnClick(R.id.btnProfile) void Profile () {
        Intent i = new Intent(DashboarduserActivity.this, ProfileuserActivity.class);
        startActivity(i);
        finish();
    }

    @OnClick(R.id.btnHistory) void History () {
        Intent i = new Intent(DashboarduserActivity.this, HistoriTransaksiActivity.class);
        startActivity(i);
        finish();
    }
}