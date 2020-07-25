package com.ona.linkapp.splash;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ona.linkapp.R;


public class ViewPagerFragment1 extends Fragment {


    public ViewPagerFragment1() {
        // Required empty public constructor
    }


    public static ViewPagerFragment1 newInstance() {

        return new ViewPagerFragment1();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_pager1, container, false);
    }
}