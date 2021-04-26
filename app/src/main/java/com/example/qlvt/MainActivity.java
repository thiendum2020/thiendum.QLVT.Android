package com.example.qlvt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.example.qlvt.QLPhanCong.QuanLyPhanCong;
import com.example.qlvt.QLTaiXe.QuanLyTaiXe;
import com.example.qlvt.QLTinh.QuanLyTinh;
import com.example.qlvt.QLTuyen.QuanLyTuyen;
import com.example.qlvt.QLXe.QuanLyXe;
import com.example.qlvt.THONGKE.ThongKeActivity;
import com.example.qlvt.THONGKE.ThongKeTuyenActivity;

public class MainActivity extends AppCompatActivity {
    LinearLayout layout_menu_phancong, layout_menu_taixe, layout_menu_tuyen, layout_menu_xe, layout_menu_tinh, layout_menu_thongke_xe, layout_menu_thongke_tuyen;
    private static int SPLASH_SCREEN = 2000;

    //Animations
    Animation topAnimation, bottomAnimation, middleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setControl();
        setEvent();

    }

    private void setControl() {

        layout_menu_phancong = findViewById(R.id.layout_menu_phancong);
        layout_menu_taixe = findViewById(R.id.layout_menu_taixe);
        layout_menu_tuyen = findViewById(R.id.layout_menu_tuyen);
        layout_menu_xe = findViewById(R.id.layout_menu_xe);
        layout_menu_tinh = findViewById(R.id.layout_menu_tinh);
        layout_menu_thongke_xe = findViewById(R.id.layout_menu_thongke_xe);
        layout_menu_thongke_tuyen = findViewById(R.id.layout_menu_thongke_tuyen);

        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation);

        layout_menu_phancong.setAnimation(middleAnimation);
        layout_menu_taixe.setAnimation(middleAnimation);
        layout_menu_tuyen.setAnimation(middleAnimation);
        layout_menu_xe.setAnimation(middleAnimation);
        layout_menu_tinh.setAnimation(middleAnimation);
        layout_menu_thongke_xe.setAnimation(middleAnimation);
        layout_menu_thongke_tuyen.setAnimation(middleAnimation);

    }

    private void setEvent() {

        layout_menu_phancong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuanLyPhanCong.class);
                startActivity(intent);
            }
        });
        layout_menu_taixe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, QuanLyTaiXe.class);
                startActivity(intent);
            }
        });
        layout_menu_tuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, QuanLyTuyen.class);
                startActivity(intent);
            }
        });

        layout_menu_xe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, QuanLyXe.class);
                startActivity(intent);
            }
        });
        layout_menu_tinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, QuanLyTinh.class);
                startActivity(intent);
            }
        });
        layout_menu_thongke_xe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ThongKeActivity.class);
                startActivity(intent);
            }
        });
    }
}