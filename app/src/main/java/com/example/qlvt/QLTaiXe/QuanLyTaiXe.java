package com.example.qlvt.QLTaiXe;

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
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.qlvt.MainActivity;
import com.example.qlvt.QLPhanCong.QuanLyPhanCong;
import com.example.qlvt.QLTinh.QuanLyTinh;
import com.example.qlvt.QLTuyen.QuanLyTuyen;
import com.example.qlvt.QLXe.QuanLyXe;
import com.example.qlvt.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

class Utils {

    public interface DelayCallback {
        void afterDelay();
    }

    public static void delay(int secs, final com.example.qlvt.QLTaiXe.Utils.DelayCallback delayCallback) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                delayCallback.afterDelay();
            }
        }, secs * 1000); // afterDelay will be executed after (secs*1000) milliseconds.
    }
}

public class QuanLyTaiXe extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static String DB_NAME = "QLVT.db", MA_TAI_XE = "maTX", TEN_TAI_XE = "tenTX", NGAYSINH = "ngaySinh", DIACHI = "diaChi";
    private static final int DB_VERSION = 1;
    SwipeMenuListView list_DSTX;
    ArrayList<TaiXe> data = new ArrayList<>();
    TaiXeAdapter adapter = null;
    int secs = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qltaixe_main);


        setControl();
        setEvent();
        loadData();
        adapter.notifyDataSetChanged();

    }


    private void setControl() {
        list_DSTX = findViewById(R.id.list_DSTaiXe);
        DrawerLayout drawer = findViewById(R.id.draw_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setEvent() {
        loadData();

        adapter = new TaiXeAdapter(this, R.layout.qltaixe_list, data);
        list_DSTX.setAdapter(adapter);
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

        list_DSTX.setMenuCreator(creator);

        list_DSTX.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        TaiXe click = data.get(position);
                        new AlertDialog.Builder(QuanLyTaiXe.this)
                                .setTitle("Xác nhận xóa?")
                                .setMessage("Bạn có chắc chắn xóa tài xế này không?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        delete(click.getMaTaiXe());
                                        Toast.makeText(QuanLyTaiXe.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
//                                        com.example.qlvt.QLTaiXe.Utils.delay(secs, new com.example.qlvt.QLTaiXe.Utils.DelayCallback() {
//                                            @Override
//                                            public void afterDelay() {
//                                                Intent intent = new Intent(QuanLyTaiXe.this, QuanLyTaiXe.class);
//                                                startActivity(intent);
//                                            }
//                                        });
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

        list_DSTX.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TaiXe taiXe = data.get(position);

                Intent intent = new Intent(QuanLyTaiXe.this, EditTaiXeActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString(MA_TAI_XE, taiXe.getMaTaiXe());
                bundle.putString(TEN_TAI_XE, taiXe.getTenTaiXe());
                bundle.putString(NGAYSINH, taiXe.getNgaySinh());
                bundle.putString(DIACHI, taiXe.getDiaChi());


                intent.putExtras(bundle);

                startActivity(intent);
            }

        });

    }


    //load dữ liệu từ table taixe ra
    public void loadData() {
        TaiXeDatabase db = new TaiXeDatabase(this);
        data.clear();
        db.getTaiXe(data);
        //adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
            Intent intent = new Intent(QuanLyTaiXe.this, InsertTaiXeActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        if (id == R.id.dashboard) {
            Intent cartIntent = new Intent(QuanLyTaiXe.this, MainActivity.class);
            startActivity(cartIntent);


        }
        if (id == R.id.qlphancong) {
            Intent cartIntent = new Intent(QuanLyTaiXe.this, QuanLyPhanCong.class);
            startActivity(cartIntent);


        } else if (id == R.id.qltaixe) {
            Intent cartIntent = new Intent(QuanLyTaiXe.this, QuanLyTaiXe.class);
            startActivity(cartIntent);


        } else if (id == R.id.qltuyen) {
            Intent cartIntent = new Intent(QuanLyTaiXe.this, QuanLyTuyen.class);
            startActivity(cartIntent);

        } else if (id == R.id.qlxe) {
            Intent cartIntent = new Intent(QuanLyTaiXe.this, QuanLyXe.class);
            startActivity(cartIntent);

        } else if (id == R.id.qltinh) {
            Intent cartIntent = new Intent(QuanLyTaiXe.this, QuanLyTinh.class);
            startActivity(cartIntent);

        }

        DrawerLayout drawerLayout = findViewById(R.id.draw_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    public void delete(String maTX) {
        TaiXeDatabase db = new TaiXeDatabase(this);
        data.clear();
        db.delete(maTX);
        db.getTaiXe(data);
        adapter.notifyDataSetChanged();
    }
}