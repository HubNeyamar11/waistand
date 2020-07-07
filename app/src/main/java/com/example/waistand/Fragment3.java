package com.example.waistand;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3 extends Fragment {
    //MainActivity mainactivity;

    private PieChart pieChart;
    private LineChart lineChart;

    private int[] colorArray = new int[] {Color.BLUE, Color.RED};

    private ArrayList<Integer> jsonList_pie = new ArrayList<>();
    private ArrayList<String> labelList_pie = new ArrayList<>();

    private ArrayList<Integer> jsonList_line = new ArrayList<>();
    private ArrayList<String> labelList_line = new ArrayList<>();

    public Fragment3() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView2 = (ViewGroup) inflater.inflate(R.layout.fragment_3, container, false);
        pieChart = rootView2.findViewById(R.id.pieChart);
        lineChart = rootView2.findViewById(R.id.lineChart);

        init(); // 그래프 그리기 위한 데이터 셋팅
        PieChartGraph(pieChart, jsonList_pie, labelList_pie); // pie chart 그리기 (= 원형 그래프)
        LineChartGraph(lineChart, jsonList_line, labelList_line); // line chart 그리기 (= 꺾은선 그래프)



        return rootView2;
    }
    public void init(){
        //AppDatabase db2 = Room.databaseBuilder(getActivity(),AppDatabase.class,"sensorData-db").allowMainThreadQueries().build();
        // line chart 데이터세팅
        jsonList_line.add(15);
        jsonList_line.add(17);
        jsonList_line.add(30);
        jsonList_line.add(4);
        //jsonList_line.add(5);


        //jsonList_line.add(Integer.parseInt(db2.dataDao().getTime().toString()));

        /*labelList_line.add("1");
        labelList_line.add("2");
        labelList_line.add("3");
        labelList_line.add("4");*/



        // pie chart 데이터세팅
        jsonList_pie.add(60);
        jsonList_pie.add(40);

        labelList_pie.add("바른 자세");
        labelList_pie.add("나쁜 자세");
    }

    private void PieChartGraph(PieChart pieChart, ArrayList jsonList_pie, ArrayList labelList_pie){
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        for(int i=0; i<jsonList_pie.size(); i++){
            pieEntries.add(new PieEntry((Integer)jsonList_pie.get(i), (String)labelList_pie.get(i)));
        } // 파이차트 데이터셋에 쓰일 엔트리 데이터

        PieDataSet pieDataSet = new PieDataSet(pieEntries, ""); // 파이차트 데이터셋 객체
        pieDataSet.setColors(colorArray); // 바른자세: blue , 나쁜자세: red

        PieData pieData = new PieData(pieDataSet); //파이차트 데이터 세팅 완료
//        pieChart.setDrawEntryLabels(true);
//        pieChart.setUsePercentValues(true);
        pieChart.setCenterText("바른자세로 앉은 시간:\n00시간 24분");
        pieChart.setCenterTextSize(10);

        pieChart.setData(pieData); // 파이차트 완성

        pieChart.invalidate(); // 화면에 파이차트 띄움
    }

    private void LineChartGraph(LineChart lineChart, ArrayList jsonList, ArrayList labelList){
        // Draw Line Chart
        ArrayList<Entry> entries = new ArrayList<>();
        for(int i=0; i<jsonList.size(); i++){
            entries.add(new Entry((Integer)jsonList.get(i), i));
        }// 꺾은선 차트 데이터셋에 쓰일 엔트리데이터

        LineDataSet lineDataSet = new LineDataSet(entries, ""); // 꺾은선 차트 데이터셋 객체

        ArrayList<String> labels = new ArrayList<>();
        for(int i=0; i<labelList.size(); i++){
            labels.add((String)labelList.get(i)+i);
        } // 라벨 리스트

        Collections.sort(entries, new EntryXComparator());
        // 소팅 안하면 java.lang.NegativeArraySizeException: -2 에러남

        LineData lineData = new LineData(lineDataSet); // line chart 데이터 세팅 완료
        lineDataSet.setColors(ColorTemplate.COLORFUL_COLORS); // 차트 색상 세팅
//        dataSet.setDrawCircles(true); //선 둥글게 만들기
//        dataSet.setDrawFilled(true); //그래프 밑부분 색칠

        // X축 라벨 세팅 어떻게 하지?
        for(int i=0; i<labels.size(); i++){
            lineDataSet.setLabel(labels.get(i));
        }

        lineChart.setData(lineData); // line chart 완성
        lineChart.animateY(5000); // 화면에 5초 동안 그래프가 서서히 그려짐


    }


}
