package com.example.barang_hilang.Model;

public class model_lihat_data {
    private String nis;
    private String nama;
    private String rombel;
    private String rayon;
    private String item_name;
    private String item_size;
    private String merek;
    private String tempat;

    public model_lihat_data(String nis, String nama, String rombel, String rayon, String item_name, String item_size, String merek, String tempat) {
        this.nis = nis;
        this.nama = nama;
        this.rombel = rombel;
        this.rayon = rayon;
        this.item_name = item_name;
        this.item_size = item_size;
        this.merek = merek;
        this.tempat = tempat;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getRombel() {
        return rombel;
    }

    public void setRombel(String rombel) {
        this.rombel = rombel;
    }

    public String getRayon() {
        return rayon;
    }

    public void setRayon(String rayon) {
        this.rayon = rayon;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_size() {
        return item_size;
    }

    public void setItem_size(String item_size) {
        this.item_size = item_size;
    }

    public String getMerek() {
        return merek;
    }

    public void setMerek(String merek) {
        this.merek = merek;
    }

    public String getTempat() {
        return tempat;
    }

    public void setTempat(String tempat) {
        this.tempat = tempat;
    }
}
