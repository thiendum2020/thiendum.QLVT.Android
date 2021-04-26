package com.example.qlvt.QLXe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qlvt.Libs;
import com.example.qlvt.QLPhanCong.PhanCongDatabase;
import com.example.qlvt.QLTaiXe.TaiXe;
import com.example.qlvt.QLTaiXe.TaiXeDatabase;
import com.example.qlvt.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class InsertXeActivity extends AppCompatActivity {
    boolean isSave = false;
    int secs = 1;
    EditText et_maXe, et_tenXe, et_namSX;
    ArrayList<String> spnTaiXes = new ArrayList<>();
    Spinner spn_maTX;
    String et_maTX;
    Button btn_Them;
    ImageView btn_choose_image;
    ArrayList<Xe> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlxe_insert);

        setControl();
        setEvent();
    }

    public void setControl() {
        et_maXe = findViewById(R.id.et_maXe);
        et_tenXe = findViewById(R.id.et_tenXe);
        et_namSX = findViewById(R.id.et_namSX);
        spn_maTX = findViewById(R.id.spin_maTX);
        btn_choose_image = findViewById(R.id.btn_choose_image);
        btn_Them = findViewById(R.id.btn_Them);
//        btn_chooseImage = findViewById(R.id.btn_chooseImage);
    }

    public void setEvent() {
        loadData();
        Libs lib = new Libs();

        TaiXeDatabase dbX = new TaiXeDatabase(this);
        spnTaiXes.clear();
        dbX.getMaTaiXe(spnTaiXes);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.selected_spinner, spnTaiXes);
        adapter.setDropDownViewResource(R.layout.dropdown_spinner_color);
        spn_maTX.setAdapter(adapter);

        spn_maTX.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                et_maTX = spnTaiXes.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_maXe.length() > 0 || et_tenXe.length() > 0 || et_namSX.length() > 0 || et_maTX.length() > 0) {
                    Xe xe = getXe();
                    if(lib.checkPrimaryKeyXe(xe.getMaXe(), data) == 1) {
                        Snackbar.make(v, "Mã xe trùng!", Snackbar.LENGTH_SHORT).setAction(null, null).show();
                    }
                    else {
                        insert(xe);
                        isSave = true;
                        Snackbar.make(v, "Thêm thành công!", Snackbar.LENGTH_SHORT).setAction(null, null).show();
                        Utils.delay(secs, new Utils.DelayCallback() {
                            @Override
                            public void afterDelay() {
                                Intent intent = new Intent(InsertXeActivity.this, QuanLyXe.class);
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

    public Xe getXe() {
        Xe xe = new Xe();
        xe.setMaXe(et_maXe.getText().toString());
        xe.setTenXe(et_tenXe.getText().toString());
        xe.setNamSX(et_namSX.getText().toString());
        xe.setMaTX(et_maTX);
        Bitmap bitmap = ((BitmapDrawable) btn_choose_image.getDrawable()).getBitmap();
        ByteArrayOutputStream imgT = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, imgT);
        byte[] imageInByte = imgT.toByteArray();
        xe.setImgXe(imageInByte);
        return xe;
    }

    public void insert(Xe xe) {
        XeDatabase db = new XeDatabase(this);
        db.insert(xe);
    }
    public void openGallerie(View objectView){
            Intent intentImg = new Intent(Intent.ACTION_GET_CONTENT);
            intentImg.setType("image/*");
            startActivityForResult(intentImg, 100);
        }
        protected void onActivityResult(int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if(resultCode == RESULT_OK && requestCode == 100){
                Uri uri = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    Bitmap decodeStream = BitmapFactory.decodeStream(inputStream);
                    btn_choose_image.setImageBitmap(decodeStream);
                    Log.e("xxx",""+btn_choose_image);
                } catch (FileNotFoundException e) {
                    Log.e("ex", e.getMessage());
                }

            }
    }

    public void loadData() {
        XeDatabase db = new XeDatabase(this);
        data.clear();
        db.getXe(data);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("xxx", "OnDestroy");
        if (isSave == false) {
            Toast.makeText(InsertXeActivity.this, "Chưa lưu lại!", Toast.LENGTH_SHORT).show();

        }
    }
}