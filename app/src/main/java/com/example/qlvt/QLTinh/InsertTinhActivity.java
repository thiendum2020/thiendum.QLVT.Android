package com.example.qlvt.QLTinh;

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

public class InsertTinhActivity extends AppCompatActivity {
    boolean isSave = false;
    int secs = 1;
    EditText et_maTinh, et_tenTinh;
    Button btn_Them;
    ArrayList<Tinh> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qltinh_insert);

        setControl();
        setEvent();
    }

    public void setControl() {
        et_maTinh = findViewById(R.id.et_maTinh);
        et_tenTinh = findViewById(R.id.et_tenTinh);

        btn_Them = findViewById(R.id.btn_Them);
    }

    public void setEvent() {
        loadData();
        Libs lib = new Libs();
        btn_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_maTinh.length() > 0 && et_tenTinh.length() > 0) {
                    Tinh tinh = getTinh();
                    if(lib.checkPrimaryKeyTinh(tinh.getMaTinh(), data) == 1) {
                        Snackbar.make(v, "Mã tỉnh trùng, nhập lại!", Snackbar.LENGTH_SHORT).setAction(null, null).show();
                    }
                    else {
                        insert(tinh);
                        isSave = true;
                        Snackbar.make(v, "Thêm thành công!", Snackbar.LENGTH_SHORT).setAction(null, null).show();
                        com.example.qlvt.QLTinh.Utils.delay(secs, new com.example.qlvt.QLTinh.Utils.DelayCallback() {
                            @Override
                            public void afterDelay() {
                                Intent intent = new Intent(InsertTinhActivity.this, QuanLyTinh.class);
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


    public Tinh getTinh() {
        Tinh tinh = new Tinh();
        tinh.setMaTinh(et_maTinh.getText().toString());
        tinh.setTenTinh(et_tenTinh.getText().toString());

        return tinh;
    }

    public void insert(Tinh tinh) {
        TinhDatabase db = new TinhDatabase(this);
        db.insert(tinh);
    }

    public void loadData() {
        TinhDatabase db = new TinhDatabase(this);
        data.clear();
        db.getTinh(data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("xxx", "OnDestroy");
        if (isSave == false) {
            Toast.makeText(InsertTinhActivity.this, "Chưa lưu lại!", Toast.LENGTH_SHORT).show();

        }
    }
}
