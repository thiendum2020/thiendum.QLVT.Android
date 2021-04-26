package com.example.qlvt.QLTuyen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.qlvt.QLTinh.Tinh;
import com.example.qlvt.QLXe.Xe;

import java.util.ArrayList;

public class TuyenDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME = "QLVT.db";
    private static final int DB_VERSION = 1;

    // Define table Tuyen
    private static final String TB_TUYEN = "Tuyen";
    private static final String COL_TUYEN_MATUYEN = "MaTuyen";
    private static final String COL_TUYEN_TENTUYEN = "TenTuyen";
    private static final String COL_TUYEN_GIAVE = "GiaVe";

    public TuyenDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public TuyenDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_TUYEN);
        onCreate(db);
    }
    public void getTuyen(ArrayList<Tuyen> tuyens) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TB_TUYEN, new String[]{
                COL_TUYEN_MATUYEN, COL_TUYEN_TENTUYEN,COL_TUYEN_GIAVE
        }, null, null, null, null, "MaTuyen");

        if(cursor.moveToFirst()) {
            do {
                Tuyen tuyen = new Tuyen();
                tuyen.setMaTuyen(cursor.getString(cursor.getColumnIndex(COL_TUYEN_MATUYEN)));
                tuyen.setTenTuyen(cursor.getString(cursor.getColumnIndex(COL_TUYEN_TENTUYEN)));
                tuyen.setGiaVe(cursor.getString(cursor.getColumnIndex(COL_TUYEN_GIAVE)));
                tuyens.add(tuyen);
            } while (cursor.moveToNext());
        }
    }
    public void insert(Tuyen tuyen) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TUYEN_MATUYEN, tuyen.getMaTuyen());
        values.put(COL_TUYEN_TENTUYEN, tuyen.getTenTuyen());
        values.put(COL_TUYEN_GIAVE, tuyen.getGiaVe());
        db.insert(TB_TUYEN, null, values);
        db.close();
    }

    public void delete(String maTuyen) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TB_TUYEN, COL_TUYEN_MATUYEN + " = '" + maTuyen + "'", null);
        db.close();
    }

    public void update(Tuyen tuyen) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_TUYEN_MATUYEN, tuyen.getMaTuyen());
        values.put(COL_TUYEN_TENTUYEN, tuyen.getTenTuyen());
        values.put(COL_TUYEN_GIAVE, tuyen.getGiaVe());
        db.insert(TB_TUYEN, null, values);

        db.update(TB_TUYEN, values, COL_TUYEN_MATUYEN + " ='" + tuyen.getMaTuyen() + "'", null);
        db.close();
    }

    public void getMaTuyen(ArrayList<String> tuyens) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TB_TUYEN, new String[]{
                COL_TUYEN_MATUYEN
        }, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Tuyen tuyen = new Tuyen();

                tuyen.setMaTuyen(cursor.getString(cursor.getColumnIndex(COL_TUYEN_MATUYEN)));
                tuyens.add(tuyen.getMaTuyen());
            } while (cursor.moveToNext());
        }
    }
}
