package com.dattranuit.uitapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CanhanFragment extends Fragment {
    TextView tensv;
    TextView mssv;
    TextView mail;
    TextView birth;
    TextView khoa;
    TextView lop;
    TextView hedt;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_canhan, container, false);

        tensv = view.findViewById(R.id.hovaten);
        mssv = view.findViewById(R.id.MSSV);
        mail = view.findViewById(R.id.mail);
        birth = view.findViewById(R.id.birthday);
        khoa = view.findViewById(R.id.department);
        lop = view.findViewById(R.id.class_name);
        hedt = view.findViewById(R.id.hedt);


        Bundle bundle = getArguments();
        if(bundle != null){
            Log.d("Frag", bundle.getString("TenSV"));
            tensv.setText(bundle.getString("TenSV"));
            mssv.setText(bundle.getString("MSSV") + " | Sinh viên");
            mail.setText(bundle.getString("MSSV") + "@gm.uit.edu.vn");
            birth.setText(bundle.getString("NgSinh"));
            khoa.setText(bundle.getString("Khoa"));
            lop.setText(bundle.getString("LopSH"));
            hedt.setText(bundle.getString("HeDT"));
        }
        return view;
    }
}
