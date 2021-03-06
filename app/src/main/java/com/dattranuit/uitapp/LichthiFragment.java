package com.dattranuit.uitapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class LichthiFragment extends Fragment {
    LichthiAdapter adapter;
    ArrayList<Lichthi> lichthiArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lichthi, container, false);
        ListView listView = view.findViewById(R.id.examList);

        Bundle bundle = getArguments();
        if(bundle != null){
            lichthiArrayList = (ArrayList<Lichthi>) bundle.getSerializable("ExamList");
        }
        else {
            lichthiArrayList = new ArrayList<>();
        }

        adapter = new LichthiAdapter(getActivity(), R.layout.exam_item, lichthiArrayList);
        listView.setAdapter(adapter);

        return view;
    }
}
