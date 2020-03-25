package com.trustedoffer.firestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.FragmentManager;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class NextActivity extends AppCompatActivity {
    //For Refresh Fragment
    public FragmentRefreshListener getFragmentRefreshListener() {
        return fragmentRefreshListener;
    }

    public void setFragmentRefreshListener(FragmentRefreshListener fragmentRefreshListener) {
        this.fragmentRefreshListener = fragmentRefreshListener;
    }

    private FragmentRefreshListener fragmentRefreshListener;
    private TabLayout tab;
    private ViewPager viewPager;
    private EditText etTabCount;
    private Button btTabCount;
    private FragmentManager fragmentManager;
    private ArrayList<String> tabTitle = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        idFinder();
        btTabCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //For Refresh
                if(getFragmentRefreshListener()!=null){
                    getFragmentRefreshListener().onRefresh();
                }
                //Creating Tab
                createTab();
            }
        });

    }


    private void createTab() {
        int tabNum=Integer.parseInt(etTabCount.getText().toString());
        //Creating Tab By Loop
        for (int k = 0; k <tabNum; k++) {
            tab.addTab(tab.newTab().setText("Tab"+(k+1)));
        }
        //Passing Tabs Using Adapter
        PlansPagerAdapter adapter = new PlansPagerAdapter
                (getSupportFragmentManager(), tab.getTabCount());
        //Setting Adapter
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
//If your tab layout has more than 2 tabs then tab will scroll other wise they will take whole width of the screen
        if (tab.getTabCount() == 2) {
            tab.setTabMode(TabLayout.MODE_FIXED);
        } else {
            tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }

    private void idFinder() {
        tab=findViewById((R.id.tabs));
        viewPager=findViewById(R.id.frameLayout);
        btTabCount=findViewById(R.id.btTestTabGenerate);
        etTabCount=findViewById(R.id.etTestProductPrice);
    }
    //Interface for Refresh
    public interface FragmentRefreshListener{
        void onRefresh();
    }
}
