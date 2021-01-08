package com.dattranuit.uitapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ThongbaoAdapter extends BaseAdapter {

    Context context;
    int layout;
    List<ThongBao> thongBaoList;

    public ThongbaoAdapter(Context context, int layout, List<ThongBao> thongBaoList) {
        this.context = context;
        this.layout = layout;
        this.thongBaoList = thongBaoList;
    }

    @Override
    public int getCount() {
        return thongBaoList.size();
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

        TextView tvTitle = convertView.findViewById(R.id.title_thongbao);
        TextView tvContent = convertView.findViewById(R.id.content_thongbao);
        TextView tvPostTime = convertView.findViewById(R.id.post_time);

        final ThongBao thongBao = thongBaoList.get(position);
        tvTitle.setText(thongBao.getTitle());
        tvContent.setText("Vào thứ " + thongBao.getThu() + "| Từ tiết " + thongBao.getTietBD() + " đến tiết " + thongBao.getTietKT() + " | Phòng :" + thongBao.getPhong());
        tvPostTime.setText(thongBao.getCreateTime());

        return convertView;
    }
}
