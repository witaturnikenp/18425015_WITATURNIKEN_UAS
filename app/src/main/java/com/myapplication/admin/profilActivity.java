package com.myapplication.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.myapplication.R;
import com.myapplication.loginregis.LoginActivity;
import com.myapplication.session.PrefSetting;
import com.myapplication.session.SessionManager;
import com.ornach.nobobutton.NoboButton;


public class profilActivity extends AppCompatActivity {
    NoboButton Exit;
    SessionManager session;
    TextView txtUserName, txtNamaLengkap, txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        getSupportActionBar().setTitle("Profile Admin");

        session = new SessionManager(profilActivity.this);

        txtUserName = (TextView) findViewById(R.id.userName);
        txtNamaLengkap = (TextView) findViewById(R.id.namaLengkap);
        txtEmail = (TextView) findViewById(R.id.email);
        Exit = (NoboButton) findViewById(R.id.btnExit);

        txtUserName.setText(PrefSetting.username);
        txtNamaLengkap.setText(PrefSetting.nama);
        txtEmail.setText(PrefSetting.email);

        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Keluar:", "OK");
                session.setLogin(false);
                session.setSessid(0);
                Intent i = new Intent(profilActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(profilActivity.this, DashboardAdminActivity.class);
        startActivity(i);
        finish();

    }
}