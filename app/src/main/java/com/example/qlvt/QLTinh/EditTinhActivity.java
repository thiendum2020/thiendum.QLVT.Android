package com.example.qlvt.QLTinh;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qlvt.R;
import com.google.android.material.snackbar.Snackbar;

public class EditTinhActivity extends AppCompatActivity {
    static String MA_TINH = "MaTinh", TEN_TINH = "TenTinh";
    boolean isUpdate;
    int secs = 1;
    EditText editMa, editTen;
    Button btnSave;
    Tinh tinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qltinh_edit);

        setControl();
        setEvent();
    }

    public void setControl() {
        editMa = findViewById(R.id.ma_tinh);
        editTen = findViewById(R.id.ten_tinh);
        btnSave = findViewById(R.id.btnSave);

    }

    public void setEvent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            editMa.setText(bundle.getString(MA_TINH));
            editTen.setText(bundle.getString(TEN_TINH));


        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editMa.length() > 0 && editTen.length() > 0) {

                    tinh = getTinh();
                    Log.e("update", "" + tinh.getMaTinh());
                    update(tinh);
                    isUpdate = true;
                    Snackbar.make(v, "Sửa thành công!", Snackbar.LENGTH_SHORT).setAction(null, null).show();
                    com.example.qlvt.QLTinh.Utils.delay(secs, new com.example.qlvt.QLTinh.Utils.DelayCallback() {
                        @Override
                        public void afterDelay() {
                            Intent intent = new Intent(EditTinhActivity.this, QuanLyTinh.class);
                            startActivity(intent);
                        }
                    });
                    //Toast.makeText(UpdateDeleteActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditTinhActivity.this, "Có thông tin chưa nhập! Kiểm tra lại", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public Tinh getTinh() {
        Tinh tinh = new Tinh();
        tinh.setMaTinh(editMa.getText().toString());
        tinh.setTenTinh(editTen.getText().toString());
        return tinh;
    }

    public void update(Tinh tinh) {
        TinhDatabase db = new TinhDatabase(this);
        db.update(tinh);
    }


    public void openAlertDialog(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Xác nhận lưu?")
                .setMessage("Nhưng thay đổi bạn đã nhập chưa được lưu. Bạn có muốn lưu lại không?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Tinh tinh = getTinh();
                        update(tinh);
                        Snackbar.make(view, "Lưu thành công!", Snackbar.LENGTH_SHORT).setAction(null, null).show();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
}
