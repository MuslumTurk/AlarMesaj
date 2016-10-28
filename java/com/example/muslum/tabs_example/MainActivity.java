package com.example.muslum.tabs_example;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.call,
            R.drawable.contacts,
            R.drawable.message,
            R.drawable.call_back
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        if (servis_calisiyormu()) {
            stopService(new Intent(MainActivity.this, Servisim.class));


        } else {
            startService(new Intent(MainActivity.this, Servisim.class));

        }
    }

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("ARAMA");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.call, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("CAGRI GEÇMİŞİ");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.call_back, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("REHBER");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.contacts, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFor = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFor.setText("ALARMESAJ");
        tabFor.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.message, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFor);

        TextView tabFive = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFive.setText("HAKKIMIZDA");
        tabFive.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.about, 0, 0);
        tabLayout.getTabAt(4).setCustomView(tabFive);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OneFragment(), "ARAMA");
        adapter.addFrag(new Cagri_kaydi(), "CAGRI GEÇMİŞİ");
        adapter.addFrag(new TwoFragment(), "REHBER");
        adapter.addFrag(new ThreeFragment(), "ALARMESAJ");
        adapter.addFrag(new Hakkimizda(), "HAKKIMIZDA");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    public boolean servis_calisiyormu() {
        ActivityManager servis_manager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo bilgi : servis_manager.getRunningServices(Integer.MAX_VALUE)) {
            if (getApplication().getPackageName().equals(bilgi.service.getPackageName())) {
                return true;
            }
        }
        return false;
    }
}
