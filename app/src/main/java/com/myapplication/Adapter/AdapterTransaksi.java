package com.myapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myapplication.R;
import com.myapplication.model.ModelTransaksi;


import java.util.ArrayList;
import java.util.List;

public class AdapterTransaksi extends BaseAdapter {


    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelTransaksi> item;

    public AdapterTransaksi(Activity activity, ArrayList<ModelTransaksi> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() { return item.size(); }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.conten_transaksi, null);


        TextView txtNamaPembeli = (TextView) convertView.findViewById(R.id.txtNamaPembeli);
        TextView txtNamaBarang     = (TextView) convertView.findViewById(R.id.txtBarang);
        TextView txtHargaBarang          = (TextView) convertView.findViewById(R.id.txtHarga);
        TextView txtJumlah         = (TextView) convertView.findViewById(R.id.txtJumlah);
        TextView txtStatus         = (TextView) convertView.findViewById(R.id.txtStatus);
        TextView txtTotal         = (TextView) convertView.findViewById(R.id.txtTotal);
        LinearLayout LStatus    = (LinearLayout) convertView.findViewById(R.id.LStatus);


        txtNamaPembeli.setText(item.get(position).getNamaUser());
        txtNamaBarang.setText(item.get(position).getNamaBarang());
        txtHargaBarang.setText("Rp." + item.get(position).getHarga());
        txtJumlah.setText("X        " + item.get(position).getJumlah());
        txtStatus.setText(item.get(position).getStatus());
        if (item.get(position).getStatus().equals("Transaksi Anda Berhasil")) {
            LStatus.setBackgroundResource(R.color.md_green_500);
        } else {
            LStatus.setBackgroundResource(R.color.md_red_700);
        }
        txtTotal.setText("=     Rp." + item.get(position).getTotal());

        return convertView;
    }
}