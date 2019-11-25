package com.hoang.lvhco.moneylover.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hoang.lvhco.moneylover.database.DB_Helper;
import com.hoang.lvhco.moneylover.model.KhoanThu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KhoanThuDao {
    private SQLiteDatabase db;
    private DB_Helper db_helper;
    public static final String TABLE_NAME= "KhoanThu";
    public static final String SQL_KHOAN_THU = "CREATE TABLE KhoanThu (Id INTEGER PRIMARY KEY AUTOINCREMENT,TenKhoanThu text, SoTienThu double ,NgayThu date)";
    public static final String TAG = "KhoanChiDao";

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    public KhoanThuDao(Context context){
        db_helper = new DB_Helper(context);
        db = db_helper.getWritableDatabase();

    }
    public int insertKhoanThu(KhoanThu khoanThu){
        ContentValues values = new ContentValues();
        values.put("TenKhoanThu",khoanThu.getTenKhoanThu());
        values.put("SoTienThu",khoanThu.getSoTienKhoanThu());
        values.put("NgayThu",sdf.format(khoanThu.getNgayThu()));
        try {
            if (db.insert(TABLE_NAME,null,values) == -1){
                return -1;
            }
        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
        return 1;
    }
    // getAll
    public List<KhoanThu> getAllKhoanThu() throws ParseException{
        List<KhoanThu> dsKhoanThu = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME,null,null,null,null,null,null);
        c.moveToFirst();
        while (c.isAfterLast() ==false){
            KhoanThu kt = new KhoanThu();
            kt.setId(c.getInt(0));
            kt.setTenKhoanThu(c.getString(1));
            kt.setSoTienKhoanThu(c.getDouble(2));
            kt.setNgayThu(sdf.parse(c.getString(3)));
            dsKhoanThu.add(kt);
            Log.d("//=====",kt.toString());
            c.moveToNext();

        } c.close();
        return dsKhoanThu;
    }


    // update
    public int updateKhoanThu(String TenKhoanThu, Double SoTienKhoanThu, Date NgayThu){
        ContentValues values = new ContentValues();
        values.put("TenKhoanThu",TenKhoanThu);
        values.put("SoTienThu",SoTienKhoanThu);
        values.put("NgayThu",sdf.format(NgayThu));
        int result = db.update(TABLE_NAME,values,"TenKhoanThu=?",new String[]{String.valueOf(TenKhoanThu)});
        if (result ==0){
            return -1;
        }
        return 1;
    }
    public int deleteKhoanThuByID(int Id){
        int result = db.delete(TABLE_NAME,"Id=?",new String[]{String.valueOf(Id)});
        if (result == 0){
            return -1;
        }
        return 1;
    }
public double getTongKhoanThu(){
        double thongke = 0;
        String sSQL = "SELECT SUM(KhoanThu.SoTienThu) FROM KhoanThu";
        Cursor c = db.rawQuery(sSQL,null);
        c.moveToFirst();
    while (c.isAfterLast() == false) {
        thongke = c.getDouble(0);
        c.moveToNext();
    }
    c.close();
    return thongke;
}

    public double getKhoanThuTheoThang̣(){
        double ThuThang = 0;
        String sSQL1 = "SELECT SUM(SoTienThu) FROM KhoanThu WHERE strftime('%m',NgayThu) IN('\"+number+\"') ";
        Cursor c = db.rawQuery(sSQL1,null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            ThuThang = c.getDouble(0);
            c.moveToNext();
        }
        c.close();
        return ThuThang;
    }
    public List<KhoanThu> getKhoanThu(String number) throws ParseException {
        String sql = "SELECT * FROM KhoanThu WHERE NgayThu LIKE '%"+number+"%'";
        return getPr(sql);
    }
    public List<KhoanThu> getKhoanThu2(String number,String year) throws ParseException {
        String sql = "SELECT * FROM KhoanThu WHERE NgayThu LIKE '%"+number+"-"+year+"%'";
        return getPr(sql);
    }


    public List<KhoanThu> getPr(String sql, String...selectionArgs) throws ParseException {
        List<KhoanThu> list = new ArrayList<>();
        Cursor c =db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            KhoanThu kt = new KhoanThu();
            kt.setId(c.getInt(0));
            kt.setTenKhoanThu(c.getString(1));
            kt.setSoTienKhoanThu(c.getDouble(2));
            kt.setNgayThu(sdf.parse(c.getString(3)));
            list.add(kt);
        }
        return list;
    }

    public double getKhoanThuTheoNam(){
        double ThuNam = 0;
        String sSQL1 = "SELECT SUM(KhoanThu.SoTienThu)FROM KhoanThu where strftime('%Y',KhoanThu.NgayThu) = strftime('%Y','now') ";
        Cursor c = db.rawQuery(sSQL1,null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            ThuNam = c.getDouble(3);
            c.moveToNext();
        }
        c.close();
        return ThuNam;
    }
    public int getKhoanthuthang(String month, String yeah) {
        int khoanthuthang = 0;
        String Sql2 = "SELECT SUM(SoTienThu) AS 'SoTienThu' FROM KHOANTHU where NgayThu LIKE'__-" + month + "-" + yeah + "' ";
        Cursor c = db.rawQuery(Sql2, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            khoanthuthang = c.getInt(0);
            c.moveToNext();
        }
        c.close();
        return khoanthuthang;
    }

}
