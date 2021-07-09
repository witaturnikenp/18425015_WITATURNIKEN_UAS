package com.myapplication.Server;

public class BaseUrl {

    public static String Url = "http://192.168.43.197:3000/";
    public static String Login = Url + "user/login";
    public static String Register = Url + "user/register";


    public static String DataBarang = Url + "barang/getAll";
    public static String InputBarang = Url + "barang/input";
    public static String EditBarang = Url + "barang/updateBarang/";
    public static String HapusBarang = Url + "barang/deleteBarang/";
    public static String EditStok = Url + "barang/updateStok/";

    public static String InputTransaksi = Url + "transaksi/transaksi";
    public static String DataTransaksi = Url + "transaksi/getTransaksiById/";
    public static String GetTransaksi = Url + "transaksi/getAll";
    public static String EditTransaksi = Url + "transaksi/confirm/";


}
