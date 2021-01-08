package com.dattranuit.uitapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DeadlineAdapter extends BaseAdapter {

    Context context;
    int layout;
    List<Deadline> deadlineList;

    public  DeadlineAdapter(Context context, int layout, List<Deadline> deadline)
    {
        this.context = context;
        this.layout = layout;
        this.deadlineList = deadline;
    }

    @Override
    public int getCount() {
        return deadlineList.size();
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

       // TextView tvSubject = (TextView) convertView.findViewById(R.layout.fragment_deadline)
        TextView tvSubject = convertView.findViewById(R.id.subject);
        TextView tvContent = convertView.findViewById(R.id.content);
        TextView tvTime = convertView.findViewById(R.id.time);
        TextView tvState = convertView.findViewById(R.id.state);
        TextView tvSubmit = convertView.findViewById(R.id.submit);
        Button btnView = convertView.findViewById(R.id.btn_view);

        final Deadline deadline = deadlineList.get(position);
        tvSubject.setText(deadline.getSubject());
        tvContent.setText(deadline.getContext());
        tvTime.setText(deadline.getTimeDeadline());
        tvState.setText(deadline.getTimeRemain());
        tvSubmit.setText(deadline.getSubmitStatus());
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, deadline.getUrl(),Toast.LENGTH_SHORT).show();
//                String url = deadline.getUrl();
//                Uri uri = Uri.parse(url);
//                Intent intent = new Intent();
//                intent.setData(uri);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
