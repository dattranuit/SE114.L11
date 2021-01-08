package com.dattranuit.uitapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class LichthiAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<Lichthi> lichthiList;

    public LichthiAdapter(Context context, int layout, List<Lichthi> lichthiList) {
        this.context = context;
        this.layout = layout;
        this.lichthiList = lichthiList;
    }

    @Override
    public int getCount() {
        return (lichthiList != null)? lichthiList.size(): 0 ;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(layout, null);

        TextView tvNgaythi = convertView.findViewById(R.id.ngaythi);
        TextView tvContent = convertView.findViewById(R.id.contentlichthi);

        final Lichthi lichthi = lichthiList.get(position);
        tvNgaythi.setText("Ngày thi: " + lichthi.getNgayThi());
        tvContent.setText("Ca thi: " + lichthi.getCaThi() + " | Môn thi: " + lichthi.getMaMH() + " | Phòng thi: " + lichthi.getPhongThi());

        return convertView;
    }
}
