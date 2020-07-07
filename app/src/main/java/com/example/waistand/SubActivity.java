/*
* SubActivity : 사실상 메인 화면
* Toolbar : 상단 액션바 대신 툴바 씀
* ViewPager : 화면 옆으로 휙휙 넘길 수 있게 함 -> 프래그먼트 3개 사용
* ViewPagerAdapter : 뷰페이저와 프래그먼트를 연결
* TabLayout : 하단부 탭 메뉴
*
* */
package com.example.waistand;

import android.content.Context;

import android.os.Bundle;
import android.view.View;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;


public class SubActivity extends AppCompatActivity {

    public int getarr[] = new int[28];
    public static Context context_sub;
    //private final static String FRAGMENT_TAG = "FRAGMENTB_TAG";

    private CustomDialog customDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customDialog = new CustomDialog(this,positiveListener,negativeListener);
        customDialog.show();





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
        images.add(R.drawable.gamecontroller);
        images.add(R.drawable.calendar_monthly);

        context_sub = this;
        for(int i=0; i<3; i++)
            tab.getTabAt(i).setIcon(images.get(i));


    }

    //팝업 창 버튼 눌렀을 때
    private View.OnClickListener positiveListener = new View.OnClickListener() {
        public void onClick(View v) {
            //Toast.makeText(getApplicationContext(), "확인버튼이 눌렸습니다.",Toast.LENGTH_SHORT).show();
            //customDialog.dismiss();
            customDialog.hide();
        }
    };

    private View.OnClickListener negativeListener = new View.OnClickListener() {
        public void onClick(View v) {
            //Toast.makeText(getApplicationContext(), "취소버튼이 눌렸습니다.",Toast.LENGTH_SHORT).show();
            customDialog.dismiss();
        }
    };




    public void setVal(){

    }
    /*public void getArray(int getarr[]){


    }*/

}
