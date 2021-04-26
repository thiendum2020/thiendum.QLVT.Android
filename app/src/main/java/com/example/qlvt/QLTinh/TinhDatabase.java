package com.example.qlvt.QLTinh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


import java.util.ArrayList;

public class TinhDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME = "QLVT.db";
    private static final int DB_VERSION = 1;

    // Define table Tinh
    private static final String TB_TINH = "TINH";
    private static final String COL_TINH_MATINH = "MaTinh";
    private static final String COL_TINH_TENTINH = "TenTinh";

    public TinhDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public TinhDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_TINH);
        onCreate(db);
    }

    public void getTinh(ArrayList<Tinh> tinhs) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TB_TINH, new String[]{
                COL_TINH_MATINH, COL_TINH_TENTINH
        }, null, null, null, null, "MaTinh");

        if (cursor.moveToFirst()) {
            do {
                Tinh tinh = new Tinh();
                tinh.setMaTinh(cursor.getString(cursor.getColumnIndex(COL_TINH_MATINH)));
                tinh.setTenTinh(cursor.getString(cursor.getColumnIndex(COL_TINH_TENTINH)));
                tinhs.add(tinh);
            } while (cursor.moveToNext());
        }
    }

    public void insert(Tinh tinh) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TINH_MATINH, tinh.getMaTinh());
        values.put(COL_TINH_TENTINH, tinh.getTenTinh());
        db.insert(TB_TINH, null, values);
        db.close();
    }

    public void delete(String maTinh) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TB_TINH, COL_TINH_MATINH + " = '" + maTinh + "'", null);
        db.close();
    }

    public void update(Tinh tinh) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_TINH_MATINH, tinh.getMaTinh());
        values.put(COL_TINH_TENTINH, tinh.getTenTinh());

        db.update(TB_TINH, values, COL_TINH_MATINH + " ='" + tinh.getMaTinh() + "'", null);
        db.close();
    }
}

