package com.ona.linkapp.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.ona.linkapp.R;
import com.ona.linkapp.main.MainActivity;

public class ViewPagerFragment4 extends Fragment {

    private Button goButton;
    private Context context;

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
        View rootView =  inflater.inflate(R.layout.fragment_view_pager4, container, false);
        context = rootView.getContext();

        goButton = (Button) rootView.findViewById(R.id.go);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(context, MainActivity.class);
                context.startActivity(main);
            }
        });
    }
}