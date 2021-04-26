package com.example.qlvt.QLXe;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
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
import com.example.qlvt.QLPhanCong.QuanLyPhanCong;
import com.example.qlvt.QLTaiXe.QuanLyTaiXe;
import com.example.qlvt.QLTinh.QuanLyTinh;
import com.example.qlvt.QLTuyen.QuanLyTuyen;
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

public class QuanLyXe extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static String DB_NAME = "QLVT.db", MA_XE = "maXe", TEN_XE = "tenTX", NAM_SX = "namSX", MA_TX = "maTX", IMG = "IMG";
    private static final int DB_VERSION = 1;
    ImageView img_xe;
    SwipeMenuListView list_DSXe;
    ArrayList<Xe> data = new ArrayList<>();
    XeAdapter adapter = null;
    int secs = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlxe_main);


        setControl();
        setEvent();
        loadData();
        adapter.notifyDataSetChanged();

    }


    private void setControl() {
        list_DSXe = findViewById(R.id.list_DSXe);
        img_xe = findViewById(R.id.img_xe);
        DrawerLayout drawer = findViewById(R.id.draw_layout);
        Toolbar toolbar_xe = findViewById(R.id.toolbar_xe);
        setSupportActionBar(toolbar_xe);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void setEvent() {
        loadData();
        adapter = new XeAdapter(this, R.layout.qlxe_list, data);
        list_DSXe.setAdapter(adapter);
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

        list_DSXe.setMenuCreator(creator);

        list_DSXe.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Xe click = data.get(position);
                        new AlertDialog.Builder(QuanLyXe.this)
                                .setTitle("Xác nhận xóa?")
                                .setMessage("Bạn có chắc chắn xóa xe này không?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        delete(click.getMaXe());
                                        Toast.makeText(QuanLyXe.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
//                                        com.example.qlvt.QLXe.Utils.delay(secs, new com.example.qlvt.QLXe.Utils.DelayCallback() {
//                                            @Override
//                                            public void afterDelay() {
//                                                Intent intent = new Intent(QuanLyXe.this, QuanLyXe.class);
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


        list_DSXe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Xe xe = data.get(position);

                Intent intent = new Intent(QuanLyXe.this, EditXeActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString(MA_XE, xe.getMaXe());
                bundle.putString(TEN_XE, xe.getTenXe());
                bundle.putString(NAM_SX, xe.getNamSX());
                bundle.putString(MA_TX, xe.getMaTX());
                bundle.putByteArray(IMG, xe.getImgXe());

                intent.putExtras(bundle);

                startActivity(intent);
            }

        });


    }

    //load dữ liệu từ table xe ra
    public void loadData() {
        XeDatabase db = new XeDatabase(this);
        data.clear();
        db.getXe(data);
        //adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.add) {
            Intent intent = new Intent(QuanLyXe.this, InsertXeActivity.class);
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
        loadData();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.dashboard) {
            Intent cartIntent = new Intent(QuanLyXe.this, MainActivity.class);
            startActivity(cartIntent);
        }
        if (id == R.id.qlphancong) {
            Intent cartIntent = new Intent(QuanLyXe.this, QuanLyPhanCong.class);
            startActivity(cartIntent);

        } else if (id == R.id.qltaixe) {
            Intent cartIntent = new Intent(QuanLyXe.this, QuanLyTaiXe.class);
            startActivity(cartIntent);


        } else if (id == R.id.qltuyen) {
            Intent cartIntent = new Intent(QuanLyXe.this, QuanLyTuyen.class);
            startActivity(cartIntent);

        } else if (id == R.id.qlxe) {
            Intent cartIntent = new Intent(QuanLyXe.this, QuanLyXe.class);
            startActivity(cartIntent);

        } else if (id == R.id.qltinh) {
            Intent cartIntent = new Intent(QuanLyXe.this, QuanLyTinh.class);
            startActivity(cartIntent);

        }

        DrawerLayout drawerLayout = findViewById(R.id.draw_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    public void delete(String maXe) {
        XeDatabase db = new XeDatabase(this);
        data.clear();
        db.delete(maXe);
        db.getXe(data);
        adapter.notifyDataSetChanged();
    }

}
