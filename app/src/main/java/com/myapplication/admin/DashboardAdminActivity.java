package com.myapplication.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.myapplication.R;
import com.myapplication.session.PrefSetting;
import com.myapplication.session.SessionManager;


import butterknife.ButterKnife;

public class DashboardAdminActivity extends AppCompatActivity {
    SessionManager session;
    SharedPreferences prefs;
    PrefSetting prefSetting;
    CardView CardExit, CardData, CardInput, CardProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin2);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        prefSetting = new PrefSetting(this);
        prefs = prefSetting.getSharePreferences();

        session = new SessionManager(DashboardAdminActivity.this);
        prefSetting.isLogin(session, prefs);

        CardData = (CardView) findViewById(R.id.CardData);
        CardInput = (CardView) findViewById(R.id.CardInput);
        CardProfile = (CardView) findViewById(R.id.CardProfile);
        CardExit = (CardView) findViewById(R.id.CardExit);
        CardExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                session.setLogin(false);
//                session.setSessid(0);
                Intent i = new Intent(DashboardAdminActivity.this, DataTransaksiActivity.class);
                startActivity(i);
                finish();
            }
        });

        CardData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardAdminActivity.this, DataActivity.class);
                startActivity(i);
                finish();
            }
        });

        CardInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardAdminActivity.this, inputDataActivity.class);
                startActivity(i);
                finish();
            }
        });

        CardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardAdminActivity.this, profilActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}