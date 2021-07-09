package com.myapplication.model;

public class ModelTransaksi {
    String _id, NamaUser, NamaBarang, Jumlah, Harga, Status, Total;

    public String get_Id() { return _id; }
    public void set_Id(String _id) { this._id = _id; }

    public String getNamaUser() {return NamaUser;}
    public void setNamaUser(String NamaUser) {this.NamaUser = NamaUser;}

    public String getNamaBarang() {return NamaBarang;}
    public void setNamaBarang(String NamaBarang) {this.NamaBarang = NamaBarang;}

    public String getJumlah() {return Jumlah;}
    public void setJumlah(String Jumlah) {this.Jumlah = Jumlah;}

    public String getHarga() {return Harga;}
    public void setHarga(String Harga) {this.Harga = Harga;}

    public String getStatus() {return Status;}
    public void setStatus(String Status) {this.Status = Status;}

    public String getTotal() {return Total;}
    public void setTotal(String Total) {this.Total = Total;}

}
