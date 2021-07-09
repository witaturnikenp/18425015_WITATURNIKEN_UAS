package com.myapplication.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.myapplication.R;
import com.myapplication.Server.BaseUrl;
import com.myapplication.session.PrefSetting;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    PrefSetting prefSetting;
    ProgressDialog pDialog;

    TextView NamaBarang, HargaBarang, Stok, Kategori;
    EditText Jumlah;
    ImageView imgGambarBuku;
    Button btnBeli;

    private RequestQueue mRequestQueue;

    String username;
    String strNamaBarang;
    String strHargaBarang;
    String strStok;
    String strKategori;
    String strGamabr;
    String _id;
    String strJumlah;
    String Status;
    int Total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().hide();
        ButterKnife.bind(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        mRequestQueue = Volley.newRequestQueue(this);

        NamaBarang = (TextView) findViewById(R.id.NamaBarang);
        HargaBarang = (TextView) findViewById(R.id.HargaBarang);
        Stok = (TextView) findViewById(R.id.StokBarang);
        Kategori = (TextView) findViewById(R.id.KategoriBarang);
        Jumlah = (EditText) findViewById(R.id.extJumlahBarang);

        imgGambarBuku = (ImageView) findViewById(R.id.gambar);

        btnBeli = (Button) findViewById(R.id.btnBeli);

        Intent i = getIntent();
        strNamaBarang = i.getStringExtra("namaBarang");
        strHargaBarang = i.getStringExtra("hargaBarang");
        strStok = i.getStringExtra("stok");
        strKategori = i.getStringExtra("kategori");
        strGamabr = i.getStringExtra("gambar");
        _id = i.getStringExtra("_id");

        NamaBarang.setText(strNamaBarang);
        HargaBarang.setText(strHargaBarang);
        Kategori.setText(strKategori);
        Stok.setText(strStok);
        Picasso.get().load(BaseUrl.Url + "gambar/" + strGamabr)
                .into(imgGambarBuku);

        btnBeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strNamaBarang = String.valueOf(NamaBarang.getText());
                strHargaBarang = String.valueOf(HargaBarang.getText());
                strJumlah = String.valueOf(Jumlah.getText());
                if (strJumlah.equals("")) {
                    strJumlah = "1";
                }
                username = String.valueOf(PrefSetting.username);
                Status = "Sedang Proses";
                Total = Integer.parseInt(strHargaBarang) * Integer.parseInt(strJumlah);

                if (Integer.parseInt(strStok) > Integer.parseInt(strJumlah)) {
                    int JumlahAsli = Integer.parseInt(strStok) - Integer.parseInt(strJumlah);
                    GoTransaksi(username, strNamaBarang, strHargaBarang, strJumlah, Status, String.valueOf(JumlahAsli), _id);
                } else {
                    Toast.makeText(getApplicationContext(), "Pesanan Anda Melebihi Stok Yang Ada", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void GoTransaksi(String username, String Barang, String Harga, String Jum, String Stat, String JumlahBarang, String IdBarang) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("NamaBarang", Barang);
        params.put("HargaBarang", Harga);
        params.put("jumlahBarang", Jum);
        params.put("Status", Stat);
        params.put("IdBarang", IdBarang);
        params.put("JumlahBarang", JumlahBarang);
        params.put("Total", String.valueOf(Total));

        pDialog.setMessage("Mohon Tunggu....");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(BaseUrl.InputTransaksi, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            hiddenDialog();
                            String strMsg = response.getString("msg");
                            boolean status = response.getBoolean("error");
                            if (status) {
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();

                                Intent i = new Intent(DetailActivity.this, DashboarduserActivity.class);
                                startActivity(i);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

// add the request object to the queue to be executed
        mRequestQueue.add(req);
    }

    private void showDialog() {
        if (!pDialog.isShowing()) {
            pDialog.show();
        }
    }
    private void hiddenDialog() {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
}