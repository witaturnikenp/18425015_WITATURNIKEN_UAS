package com.myapplication.loginregis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.myapplication.admin.DashboardAdminActivity;
import com.myapplication.session.PrefSetting;
import com.myapplication.session.SessionManager;
import com.myapplication.user.DashboarduserActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.edtUserName)
    EditText edtUserName;
    @BindView(R.id.edtPassword) EditText edtPassword;
    ProgressDialog pDialog;
    private RequestQueue mRequestQueue;

    SessionManager sessionManager;
    SharedPreferences prefs;
    PrefSetting prefSetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        ButterKnife.bind(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        prefSetting = new PrefSetting(this);
        prefs = prefSetting.getSharePreferences();

        sessionManager = new SessionManager(this);
        prefSetting.checkLogin(sessionManager, prefs);

        mRequestQueue = Volley.newRequestQueue(this);
    }

    public void login(String username, String password) {
        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);

        Log.e("Berhasil : ", "True");

        pDialog.setMessage("Mohon Tunggu....");
        showDialog();

        JsonObjectRequest req = new JsonObjectRequest(BaseUrl.Login, new JSONObject(params),
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
                                String data = response.getString("data");
                                JSONObject jsonObject = new JSONObject(data);
                                String rules = jsonObject.getString("level");
                                String _id = jsonObject.getString("_id");
                                String nama = jsonObject.getString("nama");
                                String email = jsonObject.getString("email");
                                String username = jsonObject.getString("username");
                                sessionManager.setLogin(true);
                                Log.e("nama", nama);
                                prefSetting.storeRegIdSharePreferences(LoginActivity.this, _id, username, nama, email, rules, prefs);

                                if (rules.equals("1")) {
                                    Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(LoginActivity.this, DashboardAdminActivity.class);
                                    startActivity(i);
                                    finish();
                                } else if (rules.equals("2")) {
                                    Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(LoginActivity.this, DashboarduserActivity.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Rules Tidak Ada", Toast.LENGTH_LONG).show();
                                }

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

    @OnClick(R.id.loginBtn) void submit() {
        final String username = String.valueOf(edtUserName.getText());
        final String password = String.valueOf(edtPassword.getText());
        Log.e("username", username);
        Log.e("password", password);
        if (username.isEmpty() && password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Data Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
        } else {
            login(username, password);
        }
    }

    @OnClick(R.id.btnRegistrasi) void login() {
        Log.e("Jalan: ", "true");
        Intent i = new Intent(LoginActivity.this, RegisActivity.class);
        startActivity(i);
        finish();
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