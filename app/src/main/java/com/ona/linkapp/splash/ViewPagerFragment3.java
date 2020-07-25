package com.ona.linkapp.splash;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ona.linkapp.R;


public class ViewPagerFragment3 extends Fragment {

    public ViewPagerFragment3() {
        // Required empty public constructor
    }


    public static ViewPagerFragment3 newInstance() {
        return new ViewPagerFragment3();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_pager3, container, false);
    }
}