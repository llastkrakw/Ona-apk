package com.ona.linkapp.splash;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ona.linkapp.R;

public class ViewPagerFragment4 extends Fragment {


    public ViewPagerFragment4() {
        // Required empty public constructor
    }

    public static ViewPagerFragment4 newInstance() {
        return new ViewPagerFragment4();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_pager4, container, false);
    }
}