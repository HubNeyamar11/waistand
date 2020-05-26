/*
* SubActivity : 사실상 메인 화면
* Toolbar : 상단 액션바 대신 툴바 씀
* ViewPager : 화면 옆으로 휙휙 넘길 수 있게 함 -> 프래그먼트 3개 사용
* ViewPagerAdapter : 뷰페이저와 프래그먼트를 연결
* TabLayout : 하단부 탭 메뉴
*
* */
package com.example.waistand;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.waistand.R;
import com.example.waistand.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class SubActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("웨이스탠드");
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setSupportActionBar(toolbar);

        ViewPager vp = findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

        TabLayout tab = findViewById(R.id.tab);
        tab.setupWithViewPager(vp);

        ArrayList<Integer> images = new ArrayList<>();
        images.add(R.drawable.seat);
        images.add(R.drawable.graph);
        images.add(R.drawable.calendar_monthly);

        for(int i=0; i<3; i++)
            tab.getTabAt(i).setIcon(images.get(i));
    }
}
