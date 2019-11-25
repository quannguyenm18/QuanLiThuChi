package com.hoang.lvhco.moneylover.model;

import java.util.Date;

public class KhoanChi {
    int Id;
    String TenKhoanChi;
    Double SoTienKhoanChi;
    Date NgayChi;


    public KhoanChi(){

    }

    public KhoanChi(int id, String tenKhoanChi, Double soTienKhoanChi, Date ngayChi) {
        Id = id;
        TenKhoanChi = tenKhoanChi;
        SoTienKhoanChi = soTienKhoanChi;
        NgayChi = ngayChi;
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTenKhoanChi() {
        return TenKhoanChi;
    }

    public void setTenKhoanChi(String tenKhoanChi) {
        TenKhoanChi = tenKhoanChi;
    }

    public Double getSoTienKhoanChi() {
        return SoTienKhoanChi;
    }

    public void setSoTienKhoanChi(Double soTienKhoanChi) {
        SoTienKhoanChi = soTienKhoanChi;
    }

    public Date getNgayChi() {
        return NgayChi;
    }

    public void setNgayChi(Date ngayChi) {
        NgayChi = ngayChi;
    }

    @Override
    public String toString() {
        return "KhoanChi{" +
                "Id=" + Id +
                ", TenKhoanChi='" + TenKhoanChi + '\'' +
                ", SoTienKhoanChi=" + SoTienKhoanChi +
                ", NgayChi=" + NgayChi +
                '}';
    }
}