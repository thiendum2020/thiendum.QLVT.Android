package com.example.qlvt.QLPhanCong;

import android.content.Intent;
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


public class EditPhanCongActivity extends AppCompatActivity {
    static String SO_PHIEU = "soPhieu", MA_XE = "maXa", MA_TUYEN = "maTuyen", NGAY = "ngay", XUAT_PHAT = "xuatPhat", NOI_DEN = "noiDen";
    boolean isSave = false;
    int secs = 1, indexMaxe = 0, indexTuyen = 0;
    EditText et_soPhieu, et_ngay, et_xuatPhat, et_noiDen;
    Button btn_Luu;
    Spinner spnXe, spnTuyen;
    String et_maXe, et_maTuyen;
    ArrayList<String> spnXes = new ArrayList<>();
    ArrayList<String> spnTuyens = new ArrayList<>();
    ArrayList<PhanCong> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlphancong_edit);

        setControl();
        setEvent();
    }

    public void setControl() {
        et_soPhieu = findViewById(R.id.et_soPhieu);
        spnXe = findViewById(R.id.spin_maXe);
        spnTuyen = findViewById(R.id.spin_maTuyen);
        et_ngay = findViewById(R.id.et_ngay);
        et_xuatPhat = findViewById(R.id.et_xuatPhat);
        et_noiDen = findViewById(R.id.et_noiDen);
        btn_Luu = findViewById(R.id.btn_Luu);

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

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            et_soPhieu.setText(bundle.getString(SO_PHIEU));
            setSpinerXe(bundle.getString(MA_XE));
            setSpinerTuyen(bundle.getString(MA_TUYEN));
            et_ngay.setText(bundle.getString(NGAY));
            et_xuatPhat.setText(bundle.getString(XUAT_PHAT));
            et_noiDen.setText(bundle.getString(NOI_DEN));
        }

        ArrayAdapter adapterX = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spnXes);
        adapterX.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnXe.setAdapter(adapterX);
        spnXe.setSelection(indexMaxe);

        ArrayAdapter adapterT = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spnTuyens);
        adapterT.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnTuyen.setAdapter(adapterT);
        spnTuyen.setSelection(indexTuyen);

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



        btn_Luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_soPhieu.length() > 0 && et_maXe.length() > 0 && et_maTuyen.length() > 0 && et_ngay.length() > 0 && et_xuatPhat.length() > 0 && et_noiDen.length() > 0) {
                    PhanCong phanCong = getPhanCong();
                    if (et_xuatPhat.getText().toString().equalsIgnoreCase(et_noiDen.getText().toString())) {
                        Snackbar.make(v, "Điếm đến và xuất phát không được trùng!", Snackbar.LENGTH_SHORT).setAction(null, null).show();
                    } else if (lib.checkAgenda(phanCong, data) == 0) {
                        update(phanCong);
                        isSave = true;
                        Snackbar.make(v, "Sửa thành công!", Snackbar.LENGTH_SHORT).setAction(null, null).show();
                    } else
                        Snackbar.make(v, "Trùng lịch xe! Vui lòng xem lại", Snackbar.LENGTH_SHORT).setAction(null, null).show();
                    //Toast.makeText(InsertActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
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

    public void loadData() {
        PhanCongDatabase db = new PhanCongDatabase(this);
        data.clear();
        db.getPhanCong(data);
    }

    public void update(PhanCong phanCong) {
        PhanCongDatabase db = new PhanCongDatabase(this);
        db.update(phanCong);
    }

    public void delete(String soPhieu) {
        PhanCongDatabase db = new PhanCongDatabase(this);
        db.delete(soPhieu);
    }

    public void setSpinerXe(String maXe) {
        for (int i = 0; i < spnXes.size(); i++) {
            if (maXe.equalsIgnoreCase(spnXes.get(i)))
                indexMaxe = i;
        }
    }

    public void setSpinerTuyen(String maTuyen) {
        for (int i = 0; i < spnTuyens.size(); i++) {
            if (maTuyen.equalsIgnoreCase(spnTuyens.get(i)))
                indexTuyen = i;
        }
    }
}
