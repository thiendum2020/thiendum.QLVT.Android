package com.example.qlvt.QLTuyen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qlvt.Libs;
import com.example.qlvt.QLPhanCong.PhanCongDatabase;
import com.example.qlvt.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class InsertTuyenActivity extends AppCompatActivity {
    boolean isSave = false;
    int secs = 1;
    EditText et_maTuyen, et_tenTuyen, et_giaVe;
    Button btn_Them;
    ArrayList<Tuyen> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qltuyen_insert);

        setControl();
        setEvent();
    }

    public void setControl() {
        et_maTuyen = findViewById(R.id.et_maTuyen);
        et_tenTuyen = findViewById(R.id.et_tenTuyen);
        et_giaVe = findViewById(R.id.et_giaVe);

        btn_Them = findViewById(R.id.btn_Them);
    }

    public void setEvent() {
        loadData();
        Libs lib = new Libs();
        btn_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_maTuyen.length() > 0 && et_tenTuyen.length() > 0 && et_giaVe.length() > 0) {
                    Tuyen tuyen = getTuyen();
                    if(lib.checkPrimaryKeyTuyen(tuyen.getMaTuyen(), data) == 1) {
                        Snackbar.make(v, "Mã tuyến trùng!", Snackbar.LENGTH_SHORT).setAction(null, null).show();
                    }
                    else {
                        insert(tuyen);
                        isSave = true;
                        Snackbar.make(v, "Thêm thành công!", Snackbar.LENGTH_SHORT).setAction(null, null).show();
                        com.example.qlvt.QLTuyen.Utils.delay(secs, new com.example.qlvt.QLTuyen.Utils.DelayCallback() {
                            @Override
                            public void afterDelay() {
                                Intent intent = new Intent(InsertTuyenActivity.this, QuanLyTuyen.class);
                                startActivity(intent);
                            }
                        });
                    }
                    //Toast.makeText(InsertActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(v, "Chưa nhập thông tin!", Snackbar.LENGTH_SHORT).setAction(null, null).show();
                }
            }
        });

    }


    public Tuyen getTuyen() {
        Tuyen tuyen = new Tuyen();
        tuyen.setMaTuyen(et_maTuyen.getText().toString());
        tuyen.setTenTuyen(et_tenTuyen.getText().toString());
        tuyen.setGiaVe(et_giaVe.getText().toString());

        return tuyen;
    }

    public void insert(Tuyen tuyen) {
        TuyenDatabase db = new TuyenDatabase(this);
        db.insert(tuyen);
    }

    public void loadData() {
        TuyenDatabase db = new TuyenDatabase(this);
        data.clear();
        db.getTuyen(data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("xxx", "OnDestroy");
        if (isSave == false) {
            Toast.makeText(InsertTuyenActivity.this, "Chưa lưu lại!", Toast.LENGTH_SHORT).show();

        }
    }
}
