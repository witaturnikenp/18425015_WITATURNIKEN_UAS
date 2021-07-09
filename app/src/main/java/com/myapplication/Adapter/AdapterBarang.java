package com.myapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapplication.R;
import com.myapplication.Server.BaseUrl;
import com.myapplication.model.ModelBarang;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;



public class AdapterBarang extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelBarang> item;

    public AdapterBarang(Activity activity, ArrayList<ModelBarang> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

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
            convertView = inflater.inflate(R.layout.conten_barang, null);


        TextView namaBarang = (TextView) convertView.findViewById(R.id.txtNamaBarang);
        TextView hargaBarang     = (TextView) convertView.findViewById(R.id.txtHargaBarang);
        TextView kategori          = (TextView) convertView.findViewById(R.id.txtKategori);
        TextView stok         = (TextView) convertView.findViewById(R.id.txtStok);
        ImageView gambarBarang         = (ImageView) convertView.findViewById(R.id.gambarBarang);

        namaBarang.setText(item.get(position).getNamaBarang());
        hargaBarang.setText("Rp." + item.get(position).getHargaBarang());
        kategori.setText(item.get(position).getKategori());
        stok.setText(item.get(position).getStok());
        Picasso.get().load(BaseUrl.Url + "gambar/" + item.get(position).getImage())
                .resize(40, 40)
                .centerCrop()
                .into(gambarBarang);
        return convertView;
    }
}
