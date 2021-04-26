package com.example.qlvt.QLTuyen;

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

public class EditTuyenActivity extends AppCompatActivity {
    static String MA_TUYEN = "MaTuyen", TEN_TUYEN = "TenTuyen", GIA_VE = "GiaVe";
    boolean isUpdate;
    int secs = 1;
    EditText editMa, editTen, editGiaVe;
    Button btnSave;
    Tuyen tuyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qltuyen_edit);

        setControl();
        setEvent();
    }

    public void setControl() {
        editMa = findViewById(R.id.ma_tuyen);
        editTen = findViewById(R.id.ten_tuyen);
        editGiaVe = findViewById(R.id.gia_ve);
        btnSave = findViewById(R.id.btnSave);

    }

    public void setEvent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            editMa.setText(bundle.getString(MA_TUYEN));
            editTen.setText(bundle.getString(TEN_TUYEN));
            editGiaVe.setText(bundle.getString(GIA_VE));


        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editMa.length() > 0 && editTen.length() > 0 && editGiaVe.length() > 0) {

                    tuyen = getTuyen();
                    Log.e("update", "" + tuyen.getMaTuyen());
                    update(tuyen);
                    isUpdate = true;
                    Snackbar.make(v, "Sửa thành công!", Snackbar.LENGTH_SHORT).setAction(null, null).show();
                    com.example.qlvt.QLTuyen.Utils.delay(secs, new com.example.qlvt.QLTuyen.Utils.DelayCallback() {
                        @Override
                        public void afterDelay() {
                            Intent intent = new Intent(EditTuyenActivity.this, QuanLyTuyen.class);
                            startActivity(intent);
                        }
                    });
                } else {
                    Toast.makeText(EditTuyenActivity.this, "Có thông tin chưa nhập! Kiểm tra lại", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public Tuyen getTuyen() {
        Tuyen tuyen = new Tuyen();
        tuyen.setMaTuyen(editMa.getText().toString());
        tuyen.setTenTuyen(editTen.getText().toString());
        tuyen.setGiaVe(editGiaVe.getText().toString());
        return tuyen;
    }

    public void update(Tuyen tuyen) {
        TuyenDatabase db = new TuyenDatabase(this);
        db.update(tuyen);
    }

    public void openAlertDialog(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Xác nhận lưu?")
                .setMessage("Nhưng thay đổi bạn đã nhập chưa được lưu. Bạn có muốn lưu lại không?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Tuyen tuyen = getTuyen();
                        update(tuyen);
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
