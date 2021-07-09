package com.myapplication.session;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.myapplication.admin.DashboardAdminActivity;
import com.myapplication.user.DashboarduserActivity;


public class PrefSetting {

    public static String _id;
    public static String username;
    public static String nama;
    public static String email;
    public static String level;
    Activity activity;

    public PrefSetting(Activity activity) {
        this.activity = activity;
    }

    public SharedPreferences getSharePreferences() {
        SharedPreferences preferences = activity.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return preferences;
    }

    public void isLogin(SessionManager session, SharedPreferences pref) {
        session = new SessionManager(activity);
        if (session.isLoggedIn()) {
            pref = getSharePreferences();
            _id = pref.getString("_id", "");
            username = pref.getString("username", "");
            nama = pref.getString("nama", "");
            email = pref.getString("email", "");
            level = pref.getString("level", "");
        } else {
            session.setLogin(false);
            session.setSessid(0);
            Intent i = new Intent(activity, activity.getClass());
            activity.startActivity(i);
            activity.finish();
        }
    }

    public void checkLogin(SessionManager session, SharedPreferences pref) {
        session = new SessionManager(activity);
        _id = pref.getString("_id", "");
        username = pref.getString("username", "");
        nama = pref.getString("nama", "");
        email = pref.getString("email", "");
        level = pref.getString("level", "");

        if (session.isLoggedIn()) {
            if (level.equals("1")){
                Intent i = new Intent(activity, DashboardAdminActivity.class);
                activity.startActivity(i);
                activity.finish();
            } else {
                Intent i = new Intent(activity, DashboarduserActivity.class);
                activity.startActivity(i);
                activity.finish();
            }
        }
    }

    public void storeRegIdSharePreferences(Context context, String _id, String username, String nama, String email, String level, SharedPreferences pref){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("_id", _id);
        editor.putString("username", username);
        editor.putString("nama", nama);
        editor.putString("email", email);
        editor.putString("level", level);
        editor.commit();

    }
}

