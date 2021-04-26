package com.example.qlvt;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.qlvt.QLPhanCong.PhanCong;
import com.example.qlvt.QLTaiXe.TaiXe;
import com.example.qlvt.QLTinh.Tinh;
import com.example.qlvt.QLTuyen.Tuyen;
import com.example.qlvt.QLXe.Xe;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Libs {

    public int checkAgenda(PhanCong phanCong, ArrayList<PhanCong> data) {
        ArrayList<PhanCong> tempNgay = new ArrayList<>();
        ArrayList<PhanCong> tempXe = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            if (phanCong.getNgay().equalsIgnoreCase(data.get(i).getNgay())) {
                tempNgay.add(data.get(i));
            }
        }

        for (int i = 0; i < tempNgay.size(); i++) {
            if (phanCong.getMaXe().equalsIgnoreCase(tempNgay.get(i).getMaXe())) {
                tempXe.add(tempNgay.get(i));
            }
        }
        tempNgay.clear();

        for (int i = 0; i < tempXe.size(); i++) {
            Log.e("zzz", "Xe " + tempXe.get(i).getMaXe());
            if (phanCong.getXuatPhat().equalsIgnoreCase(tempXe.get(i).getXuatPhat()) && phanCong.getNoiDen().equalsIgnoreCase(tempXe.get(i).getNoiDen())) {
                return 1;
            }
        }

        return 0;
    }


    public int checkPrimaryKeyPhanCong(String soPhieu, ArrayList<PhanCong> data) {

        for (int i = 0; i < data.size(); i++) {
            if(soPhieu.equalsIgnoreCase(data.get(i).getSoPhieu()))
                return 1;
        }
        return 0;
    }

    public int checkPrimaryKeyTaiXe(String maTX, ArrayList<TaiXe> data) {

        for (int i = 0; i < data.size(); i++) {
            if(maTX.equalsIgnoreCase(data.get(i).getMaTaiXe()))
                return 1;
        }
        return 0;
    }

    public int checkPrimaryKeyTinh(String maTinh, ArrayList<Tinh> data) {

        for (int i = 0; i < data.size(); i++) {
            if(maTinh.equalsIgnoreCase(data.get(i).getMaTinh()))
                return 1;
        }
        return 0;
    }

    public int checkPrimaryKeyTuyen(String maTuyen, ArrayList<Tuyen> data) {

        for (int i = 0; i < data.size(); i++) {
            if(maTuyen.equalsIgnoreCase(data.get(i).getMaTuyen()))
                return 1;
        }
        return 0;
    }

    public int checkPrimaryKeyXe(String maXe, ArrayList<Xe> data) {

        for (int i = 0; i < data.size(); i++) {
            if(maXe.equalsIgnoreCase(data.get(i).getMaXe()))
                return 1;
        }
        return 0;
    }
}
