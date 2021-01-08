package com.dattranuit.uitapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class DeadlineFragment extends Fragment {
    DeadlineAdapter adapter;
    ArrayList<Deadline> deadlineArrayList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deadline, container, false);
        ListView listView = view.findViewById(R.id.deadlineList);

        Bundle bundle = getArguments();
        if(bundle != null){
            deadlineArrayList = (ArrayList<Deadline>) bundle.getSerializable("DeadlineList");
        }
        else {
            deadlineArrayList = new ArrayList<>();
        }


        adapter = new DeadlineAdapter(getActivity(), R.layout.deadline_item, deadlineArrayList);
        listView.setAdapter(adapter);

        return view;
    }
}
