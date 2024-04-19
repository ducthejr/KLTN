package com.example.khoaluantn;


public class Heo {
    private String Giong;
    private String Id;
    private String GioiTinh;
    private String NgayTuoi;
    private String CanNang;

    public Heo(String giong, String id, String gioiTinh, String ngayTuoi, String canNang) {
        Giong = giong;
        Id = id;
        GioiTinh = gioiTinh;
        NgayTuoi = ngayTuoi;
        CanNang = canNang;
    }

    public String getGiong() {
        return Giong;
    }

    public void setGiong(String giong) {
        Giong = giong;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }

    public String getNgayTuoi() {
        return NgayTuoi;
    }

    public void setNgayTuoi(String ngayTuoi) {
        NgayTuoi = ngayTuoi;
    }

    public String getCanNang() {
        return CanNang;
    }

    public void setCanNang(String canNang) {
        CanNang = canNang;
    }
}
