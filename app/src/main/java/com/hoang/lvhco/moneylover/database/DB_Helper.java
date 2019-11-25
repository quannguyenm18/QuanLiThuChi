package com.hoang.lvhco.moneylover.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hoang.lvhco.moneylover.dao.KhoanChiDao;
import com.hoang.lvhco.moneylover.dao.KhoanThuDao;
import com.hoang.lvhco.moneylover.model.KhoanThu;

public class DB_Helper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "dbMoneyLover";
    public static final int VERSION = 1;

    public DB_Helper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(KhoanThuDao.SQL_KHOAN_THU);
        db.execSQL(KhoanChiDao.SQL_KHOAN_CHI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists "+KhoanThuDao.TABLE_NAME);
        db.execSQL("Drop table if exists "+KhoanChiDao.TABLE_NAME1);
        onCreate(db);
    }
}
