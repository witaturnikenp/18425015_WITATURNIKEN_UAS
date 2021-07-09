package com.myapplication.loginregis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisActivity extends AppCompatActivity {
    @BindView(R.id.edtUsername)
    EditText edtUsername;
    @BindView(R.id.edtName) EditText edtName;
    @BindView(R.id.edtEmail) EditText edtEmail;
    @BindView(R.id.edtPassword) EditText edtPassword;
    ProgressDialog pDialog;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);
        getSupportActionBar().hide();
        ButterKnife.bind(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        mRequestQueue = Volley.newRequestQueue(this);


    }

    public void register(String username, String nama, String email, String password ) {
        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("nama", nama);
        params.put("email", email);
        params.put("password", password);

        pDialog.setMessage("Mohon Tunggu....");
        showDialog();

        JsonObjectRequest req = new JsonObjectRequest(BaseUrl.Register, new JSONObject(params),
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

                                Intent i = new Intent(RegisActivity.this, LoginActivity.class);
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


    @OnClick(R.id.btnRegister) void submit() {
        final String username = String.valueOf(edtUsername.getText());
        final String name = String.valueOf(edtName.getText());
        final String email = String.valueOf(edtEmail.getText());
        final String password = String.valueOf(edtPassword.getText());
        if (email.isEmpty() && username.isEmpty() && email.isEmpty() && password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Data Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
        } else {
            register(username, name, email, password);
        }

    }

    @OnClick(R.id.btnAkun) void login() {
        Intent i = new Intent(RegisActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(RegisActivity.this, LoginActivity.class);
        startActivity(i);
        finish();

    }
}