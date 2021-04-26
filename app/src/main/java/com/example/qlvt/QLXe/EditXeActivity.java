package com.example.qlvt.QLXe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qlvt.QLPhanCong.PhanCong;
import com.example.qlvt.QLTaiXe.TaiXe;
import com.example.qlvt.QLTaiXe.TaiXeDatabase;
import com.example.qlvt.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class EditXeActivity extends AppCompatActivity {
    static final String MA_XE = "maXe", TEN_XE = "tenTX", NAM_SX = "namSX", MA_TX = "maTX", IMG = "IMG";
    boolean isUpdate;
    //int idTaiXe;
    EditText editMaXe, editTenXe, editNamSX, editMaTX;
    Spinner spnTaiXe;
    Xe xe;
    String et_maTaiXe;
    int secs = 1, indexMaTX = 0;
    ArrayList<String> spnTaiXes = new ArrayList<>();
    ArrayList<Xe> data = new ArrayList<>();
    Button btnSave;
    ImageView btn_choose_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlxe_edit);

        setControl();
        setEvent();
    }

    public void setControl() {
        editMaXe = findViewById(R.id.ma_xe);
        editTenXe = findViewById(R.id.ten_xe);
        editNamSX = findViewById(R.id.nam_sx);
        spnTaiXe = findViewById(R.id.spin_maTX);

        btn_choose_image = findViewById(R.id.btn_choose_image);
        btnSave = findViewById(R.id.btnSave);
    }

    public void setEvent() {
        loadData();

        TaiXeDatabase dbX = new TaiXeDatabase(this);
        spnTaiXes.clear();
        dbX.getMaTaiXe(spnTaiXes);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            editMaXe.setText(bundle.getString(MA_XE));
            editTenXe.setText(bundle.getString(TEN_XE));
            setSpinerTaiXe(bundle.getString(MA_TX));
            editNamSX.setText(bundle.getString(NAM_SX));

            Bitmap bitmap = BitmapFactory.decodeByteArray(bundle.getByteArray(IMG), 0, bundle.getByteArray(IMG).length);
            btn_choose_image.setImageBitmap(bitmap);

        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spnTaiXes);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnTaiXe.setAdapter(adapter);
        spnTaiXe.setSelection(indexMaTX);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editMaXe.length() > 0 && editTenXe.length() > 0 && editNamSX.length() > 0 && editMaTX.length() > 0) {

                    xe = getXe();
                    Log.e("update", "" + xe.getMaXe());
                    update(xe);
                    isUpdate = true;
                    Snackbar.make(v, "Sửa thành công!", Snackbar.LENGTH_SHORT).setAction(null, null).show();
                    Utils.delay(secs, new Utils.DelayCallback() {
                        @Override
                        public void afterDelay() {
                            Intent intent = new Intent(EditXeActivity.this, QuanLyXe.class);
                            startActivity(intent);
                        }
                    });

                } else {
                    Snackbar.make(v, "Chưa nhập thông tin!", Snackbar.LENGTH_SHORT).setAction(null, null).show();
                }
            }
        });

    }

    public Xe getXe() {
        Xe xe = new Xe();
        xe.setMaXe(editMaXe.getText().toString());
        xe.setTenXe(editTenXe.getText().toString());
        xe.setNamSX(editNamSX.getText().toString());
        xe.setMaTX(et_maTaiXe);
        Bitmap bitmap = ((BitmapDrawable) btn_choose_image.getDrawable()).getBitmap();
        ByteArrayOutputStream imgT = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, imgT);
        byte[] imageInByte = imgT.toByteArray();
        xe.setImgXe(imageInByte);
        return xe;
    }

    public void update(Xe xe) {
        XeDatabase db = new XeDatabase(this);
        db.update(xe);
    }

    public void openGallerie(View objectView) {
        Intent intentImg = new Intent(Intent.ACTION_GET_CONTENT);
        intentImg.setType("image/*");
        startActivityForResult(intentImg, 100);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap decodeStream = BitmapFactory.decodeStream(inputStream);
                btn_choose_image.setImageBitmap(decodeStream);

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

    public void setSpinerTaiXe(String maTaiXe) {
        for (int i = 0; i < spnTaiXes.size(); i++) {
            if (maTaiXe.equalsIgnoreCase(spnTaiXes.get(i)))
                indexMaTX = i;
        }
    }
}
