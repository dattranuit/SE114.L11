package com.dattranuit.uitapp;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class BangdiemFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bangdiem, container, false);

        WebView webView = view.findViewById(R.id.webview_bangdiem);
        String data = "";
        Bundle bundle = getArguments();
        if(bundle != null){
            data = bundle.getString("DataBangDiem");
        }
        else {
            data = "<h3> Không có dữ liệu <h3>";
        }

        webView.loadData(data, "text/html", "UTF-8");

        return view;
    }
}
