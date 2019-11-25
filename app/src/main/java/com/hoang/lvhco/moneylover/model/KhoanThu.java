package com.hoang.lvhco.moneylover.model;

import java.util.Date;

public class KhoanThu {
    int Id;
    String TenKhoanThu;
    double SoTienKhoanThu;
    Date NgayThu;

    public KhoanThu() {
    }

    public KhoanThu( int id,String tenKhoanThu, double soTienKhoanThu, Date ngayThu) {
        Id = id;
        TenKhoanThu = tenKhoanThu;
        SoTienKhoanThu = soTienKhoanThu;
        NgayThu = ngayThu;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTenKhoanThu() {
        return TenKhoanThu;
    }

    public void setTenKhoanThu(String tenKhoanThu) {
        TenKhoanThu = tenKhoanThu;
    }

    public double getSoTienKhoanThu() {
        return SoTienKhoanThu;
    }

    public void setSoTienKhoanThu(double soTienKhoanThu) {
        SoTienKhoanThu = soTienKhoanThu;
    }

    public Date getNgayThu() {
        return NgayThu;
    }

    public void setNgayThu(Date ngayThu) {
        NgayThu = ngayThu;
    }

    @Override
    public String toString() {
        return "KhoanThu{" +
                "Id=" + Id +
                ", TenKhoanThu='" + TenKhoanThu + '\'' +
                ", SoTienKhoanThu=" + SoTienKhoanThu +
                ", NgayThu=" + NgayThu +
                '}';
    }
}