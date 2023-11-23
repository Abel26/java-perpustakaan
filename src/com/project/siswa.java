package com.project;
public class siswa {
    private String NIS;
    private String nama;
    private String tanggalLahir;

    public siswa(String NIS, String nama, String tanggalLahir) {
        this.NIS = NIS;
        this.nama = nama;
        this.tanggalLahir = tanggalLahir;
    }

    public String getNIS() {
        return NIS;
    }

    public String getNama() {
        return nama;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }
}
