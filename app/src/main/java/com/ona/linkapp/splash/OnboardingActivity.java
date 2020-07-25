package com.ona.linkapp.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.eftimoff.viewpagertransformers.CubeInTransformer;
import com.eftimoff.viewpagertransformers.CubeOutTransformer;
import com.eftimoff.viewpagertransformers.ParallaxPageTransformer;
import com.ona.linkapp.helpers.ZoumOutPageTransformer;

import android.os.Bundle;

import com.ona.linkapp.R;
import com.ona.linkapp.adapters.ViewPagerAdapter;
import com.pixelcan.inkpageindicator.InkPageIndicator;

import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager pager;
    private ViewPagerAdapter pagerAdapter;
    private InkPageIndicator inkPageIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        updateUi();
    }

    private void updateUi(){

        pager = (ViewPager) findViewById(R.id.viewPager);
         inkPageIndicator = (InkPageIndicator) findViewById(R.id.indicator);
        List<Fragment> fragmentList =  new ArrayList<>();

        fragmentList.add(ViewPagerFragment1.newInstance());
        fragmentList.add(ViewPagerFragment2.newInstance());
        fragmentList.add(ViewPagerFragment3.newInstance());
        fragmentList.add(ViewPagerFragment4.newInstance());

        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0, fragmentList);
            pager.setPageTransformer(true, new CubeInTransformer());
        pager.setAdapter(pagerAdapter);
        inkPageIndicator.setViewPager(pager);

    }
}