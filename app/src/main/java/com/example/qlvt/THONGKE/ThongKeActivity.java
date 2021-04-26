package com.example.qlvt.THONGKE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qlvt.MainActivity;
import com.example.qlvt.QLPhanCong.QuanLyPhanCong;
import com.example.qlvt.R;

public class ThongKeActivity extends AppCompatActivity {
    Button btn_xe, btn_tuyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongke_main);
        setControl();
        setEvent();
    }

    private void setControl() {
        btn_xe = findViewById(R.id.thongke_xe);
        btn_tuyen = findViewById(R.id.thongke_tuyen);

    }

    private void setEvent() {
        btn_xe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThongKeActivity.this, ThongKeXeActivity.class);
                startActivity(intent);
            }
        });
        btn_tuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThongKeActivity.this, ThongKeTuyenActivity.class);
                startActivity(intent);
            }
        });
    }
}
