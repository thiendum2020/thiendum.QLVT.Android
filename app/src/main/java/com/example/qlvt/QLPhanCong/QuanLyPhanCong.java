package com.example.qlvt.QLPhanCong;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.qlvt.MainActivity;
import com.example.qlvt.QLTaiXe.QuanLyTaiXe;
import com.example.qlvt.QLTinh.QuanLyTinh;
import com.example.qlvt.QLXe.QuanLyXe;
import com.example.qlvt.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

class Utils {

    public interface DelayCallback {
        void afterDelay();
    }

    public static void delay(int secs, final DelayCallback delayCallback) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                delayCallback.afterDelay();
            }
        }, secs * 1000); // afterDelay will be executed after (secs*1000) milliseconds.
    }
}

public class QuanLyPhanCong extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static String SO_PHIEU = "soPhieu", MA_XE = "maXa", MA_TUYEN = "maTuyen", NGAY = "ngay", XUAT_PHAT = "xuatPhat", NOI_DEN = "noiDen";
    SwipeMenuListView list_DSPC;
    ArrayList<PhanCong> data = new ArrayList<>();
    PhanCongAdapter adapter = null;
    int secs = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlphancong_main);

        setControl();
        setEvent();
        loadData();
        adapter.notifyDataSetChanged();
    }

    public void setControl() {
        list_DSPC = findViewById(R.id.list_DSPC);
        Toolbar toolbar_xe = findViewById(R.id.toolbar_xe);
        setSupportActionBar(toolbar_xe);
        DrawerLayout drawer = findViewById(R.id.draw_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void setEvent() {
        //init();

        loadData();


        adapter = new PhanCongAdapter(this, R.layout.qlphancong_list, data);
        list_DSPC.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_baseline_delete_24);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        list_DSPC.setMenuCreator(creator);

        list_DSPC.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        PhanCong click = data.get(position);
                        new AlertDialog.Builder(QuanLyPhanCong.this)
                                .setTitle("Xác nhận xóa?")
                                .setMessage("Bạn có chắc chắn xóa phiếu phân công này không?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        delete(click.getSoPhieu());
                                        Toast.makeText(QuanLyPhanCong.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                                        com.example.qlvt.QLPhanCong.Utils.delay(secs, new com.example.qlvt.QLPhanCong.Utils.DelayCallback() {
                                            @Override
                                            public void afterDelay() {
                                                Intent intent = new Intent(QuanLyPhanCong.this, QuanLyPhanCong.class);
                                                startActivity(intent);
                                            }
                                        });
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();
                        break;


                }
                return false;
            }
        });

        list_DSPC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhanCong phanCong = data.get(position);

                Intent intent = new Intent(QuanLyPhanCong.this, EditPhanCongActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString(SO_PHIEU, phanCong.getSoPhieu());
                bundle.putString(MA_XE, phanCong.getMaXe());
                bundle.putString(MA_TUYEN, phanCong.getMaTuyen());
                bundle.putString(NGAY, phanCong.getNgay());
                bundle.putString(XUAT_PHAT, phanCong.getXuatPhat());
                bundle.putString(NOI_DEN, phanCong.getNoiDen());

                intent.putExtras(bundle);

                startActivity(intent);
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add) {
            Intent intent = new Intent(QuanLyPhanCong.this, InsertPhanCongActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadData() {
        PhanCongDatabase db = new PhanCongDatabase(this);
        data.clear();
        db.getPhanCong(data);
        //adapter.notifyDataSetChanged();
    }

    public void openAlertDialog(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Opps...")
                .setMessage("Chức năng này chưa thêm! Vui lòng thử lại sau!")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("xxx", "OnResume");
        loadData();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Log.e("vlxx", "delra");
        if (id == R.id.dashboard) {
            Intent cartIntent = new Intent(QuanLyPhanCong.this, MainActivity.class);
            startActivity(cartIntent);


        }
        if (id == R.id.qlphancong) {
            Intent cartIntent = new Intent(QuanLyPhanCong.this, QuanLyPhanCong.class);
            startActivity(cartIntent);


        } else if (id == R.id.qltaixe) {
            Log.e("vlxx", "delra");
            Intent cartIntent = new Intent(QuanLyPhanCong.this, QuanLyTaiXe.class);
            startActivity(cartIntent);


        } else if (id == R.id.qltuyen) {
            Intent cartIntent = new Intent(QuanLyPhanCong.this, QuanLyTaiXe.class);
            startActivity(cartIntent);

        } else if (id == R.id.qlxe) {
            Intent cartIntent = new Intent(QuanLyPhanCong.this, QuanLyXe.class);
            startActivity(cartIntent);

        } else if (id == R.id.qltinh) {
            Intent cartIntent = new Intent(QuanLyPhanCong.this, QuanLyTinh.class);
            startActivity(cartIntent);

        }

        DrawerLayout drawerLayout = findViewById(R.id.draw_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    public void delete(String maPC) {
        PhanCongDatabase db = new PhanCongDatabase(this);
        db.delete(maPC);
    }
}