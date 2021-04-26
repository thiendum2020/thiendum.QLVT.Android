package com.example.qlvt.QLTuyen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.qlvt.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TuyenAdapter extends ArrayAdapter<Tuyen> {
    Context context;
    int resource;
    ArrayList<Tuyen> data = null;

    public TuyenAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Tuyen> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    static class TuyenHolder {
        TextView tv_maTuyen, tv_tenTuyen, tv_giaVe;

        public TuyenHolder() {
        }
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        com.example.qlvt.QLTuyen.TuyenAdapter.TuyenHolder holder = null;
        if (row != null) {
            holder = (com.example.qlvt.QLTuyen.TuyenAdapter.TuyenHolder) row.getTag();
        } else {
            holder = new TuyenAdapter.TuyenHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.qltuyen_list, parent, false);

            holder.tv_maTuyen = row.findViewById(R.id.ma_tuyen);
            holder.tv_tenTuyen = row.findViewById(R.id.ten_tuyen);
            holder.tv_giaVe = row.findViewById(R.id.gia_ve);
            row.setTag(holder);
        }

        final Tuyen tuyen = data.get(position);
        holder.tv_maTuyen.setText(tuyen.getMaTuyen());
        holder.tv_tenTuyen.setText(tuyen.getTenTuyen());
        holder.tv_giaVe.setText(tuyen.getGiaVe());

        return row;
    }
}
