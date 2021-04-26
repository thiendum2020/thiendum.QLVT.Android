package com.example.qlvt.QLPhanCong;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qlvt.Libs;
import com.example.qlvt.QLTuyen.TuyenDatabase;
import com.example.qlvt.QLXe.XeDatabase;
import com.example.qlvt.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class InsertPhanCongActivity extends AppCompatActivity {
    boolean isSave = false;
    int secs = 1;
    EditText et_soPhieu, et_ngay, et_xuatPhat, et_noiDen;
    ArrayList<String> spnXes = new ArrayList<>();
    ArrayList<String> spnTuyens = new ArrayList<>();
    ArrayList<PhanCong> data = new ArrayList<>();
    Spinner spnXe, spnTuyen;
    String et_maXe, et_maTuyen;
    Button btn_Them;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlphancong_insert);

        setControl();
        setEvent();
    }

    public void setControl() {
//        et_maXe = findViewById(R.id.et_maXe);
        et_soPhieu = findViewById(R.id.et_soPhieu);
//        et_maXe = findViewById(R.id.et_maXe);
//        et_maTuyen = findViewById(R.id.et_maTuyen);
        et_ngay = findViewById(R.id.et_ngay);
        et_xuatPhat = findViewById(R.id.et_xuatPhat);
        et_noiDen = findViewById(R.id.et_noiDen);
        btn_Them = findViewById(R.id.btn_Them);
        spnXe = findViewById(R.id.spin_maXe);
        spnTuyen = findViewById(R.id.spin_maTuyen);
    }


    public void setEvent() {
        loadData();
        Libs lib = new Libs();
        XeDatabase dbX = new XeDatabase(this);
        spnXes.clear();
        dbX.getMaXe(spnXes);

        TuyenDatabase dbT = new TuyenDatabase(this);
        spnTuyens.clear();
        dbT.getMaTuyen(spnTuyens);

        ArrayAdapter adapterX = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spnXes);
        ArrayAdapter adapterT = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spnTuyens);
        adapterX.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        adapterT.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnXe.setAdapter(adapterX);
        spnTuyen.setAdapter(adapterT);
        spnXe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                et_maXe = spnXes.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnTuyen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                et_maTuyen = spnTuyens.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_soPhieu.length() > 0 && et_maXe.length() > 0 && et_maTuyen.length() > 0 && et_ngay.length() > 0 && et_xuatPhat.length() > 0 && et_noiDen.length() > 0) {
                    PhanCong phanCong = getPhanCong();
                    if(et_xuatPhat.getText().toString().equalsIgnoreCase(et_noiDen.getText().toString())){
                        Snackbar.make(v, "Điếm đến và xuất phát không được trùng!", Snackbar.LENGTH_SHORT).setAction(null, null).show();
                    }
                    else if (lib.checkPrimaryKeyPhanCong(et_soPhieu.getText().toString(), data) == 1) {
                        Snackbar.make(v, "Số phiếu đã nhập đã có, nhập lại!", Snackbar.LENGTH_SHORT).setAction(null, null).show();
                    }
                    else if (lib.checkAgenda(phanCong, data) == 0) {
                        insert(phanCong);
                        isSave = true;
                        Snackbar.make(v, "Thêm thành công!", Snackbar.LENGTH_SHORT).setAction(null, null).show();
                    }
                    else
                        Snackbar.make(v, "Trùng lịch xe! Vui lòng xem lại", Snackbar.LENGTH_SHORT).setAction(null, null).show();
                }
                else {
                    Snackbar.make(v, "Chưa nhập thông tin!", Snackbar.LENGTH_SHORT).setAction(null, null).show();
                }
            }
        });

    }

    public PhanCong getPhanCong() {
        PhanCong phanCong = new PhanCong();
        phanCong.setSoPhieu(et_soPhieu.getText().toString());
        phanCong.setMaXe(et_maXe);
        phanCong.setMaTuyen(et_maTuyen);
        phanCong.setNgay(et_ngay.getText().toString());
        phanCong.setXuatPhat(et_xuatPhat.getText().toString());
        phanCong.setNoiDen(et_noiDen.getText().toString());

        return phanCong;
    }

    public void insert(PhanCong phanCong) {
        PhanCongDatabase db = new PhanCongDatabase(this);
        db.insert(phanCong);
    }

    public void loadData() {
        PhanCongDatabase db = new PhanCongDatabase(this);
        data.clear();
        db.getPhanCong(data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("xxx", "OnDestroy");
        if (isSave == false) {
            Toast.makeText(InsertPhanCongActivity.this, "Chưa lưu lại!", Toast.LENGTH_SHORT).show();

        }
    }
}