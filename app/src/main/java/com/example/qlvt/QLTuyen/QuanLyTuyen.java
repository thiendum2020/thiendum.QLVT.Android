package com.example.qlvt.QLTuyen;

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
import com.example.qlvt.QLXe.QuanLyXe;
import com.example.qlvt.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

class Utils {
    public interface DelayCallback {
        void afterDelay();
    }

    public static void delay(int secs, final com.example.qlvt.QLTuyen.Utils.DelayCallback delayCallback) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                delayCallback.afterDelay();
            }
        }, secs * 1000); // afterDelay will be executed after (secs*1000) milliseconds.
    }
}

public class QuanLyTuyen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static String MA_TUYEN = "MaTuyen", TEN_TUYEN = "TenTuyen", GIA_VE = "GiaVe";
    private static final int DB_VERSION = 1;
    SwipeMenuListView list_DSTuyen;
    ArrayList<Tuyen> data = new ArrayList<>();
    TuyenAdapter adapter = null;
    int secs = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qltuyen_main);

        setControl();
        setEvent();
        loadData();
        adapter.notifyDataSetChanged();
    }

    public void setControl() {
        list_DSTuyen = findViewById(R.id.list_DSTuyen);

        DrawerLayout drawer = findViewById(R.id.draw_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void setEvent() {


        adapter = new TuyenAdapter(this, R.layout.qltuyen_list, data);
        list_DSTuyen.setAdapter(adapter);
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

        list_DSTuyen.setMenuCreator(creator);

        list_DSTuyen.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Tuyen click = data.get(position);
                        new AlertDialog.Builder(QuanLyTuyen.this)
                                .setTitle("Xác nhận xóa?")
                                .setMessage("Bạn có chắc chắn xóa tuyến này không?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        delete(click.getMaTuyen());
                                        Toast.makeText(QuanLyTuyen.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                                        com.example.qlvt.QLTuyen.Utils.delay(secs, new com.example.qlvt.QLTuyen.Utils.DelayCallback() {
                                            @Override
                                            public void afterDelay() {
                                                Intent intent = new Intent(QuanLyTuyen.this, QuanLyTuyen.class);
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

        list_DSTuyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tuyen tuyen = data.get(position);

                Intent intent = new Intent(QuanLyTuyen.this, EditTuyenActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString(MA_TUYEN, tuyen.getMaTuyen());
                bundle.putString(TEN_TUYEN, tuyen.getTenTuyen());
                bundle.putString(GIA_VE, tuyen.getGiaVe());

                intent.putExtras(bundle);

                startActivity(intent);
            }

        });


    }

    public void loadData() {
        TuyenDatabase db = new TuyenDatabase(this);
        data.clear();
        db.getTuyen(data);
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
            Intent intent = new Intent(QuanLyTuyen.this, InsertTuyenActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.dashboard) {
            Intent cartIntent = new Intent(QuanLyTuyen.this, MainActivity.class);
            startActivity(cartIntent);


        }
        if (id == R.id.qlphancong) {
            Intent cartIntent = new Intent(QuanLyTuyen.this, QuanLyPhanCong.class);
            startActivity(cartIntent);


        } else if (id == R.id.qltaixe) {
            Intent cartIntent = new Intent(QuanLyTuyen.this, QuanLyTaiXe.class);
            startActivity(cartIntent);


        } else if (id == R.id.qltuyen) {
            Intent cartIntent = new Intent(QuanLyTuyen.this, QuanLyTuyen.class);
            startActivity(cartIntent);


        } else if (id == R.id.qlxe) {
            Intent cartIntent = new Intent(QuanLyTuyen.this, QuanLyXe.class);
            startActivity(cartIntent);

        } else if (id == R.id.qltinh) {
            Intent cartIntent = new Intent(QuanLyTuyen.this, QuanLyTinh.class);
            startActivity(cartIntent);

        }

        DrawerLayout drawerLayout = findViewById(R.id.draw_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    public void delete(String maTuyen) {
        TuyenDatabase db = new TuyenDatabase(this);
        db.delete(maTuyen);
    }

}
