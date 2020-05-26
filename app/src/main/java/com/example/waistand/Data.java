package com.example.waistand;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//데이터 모델 준비 끝
@Entity
public class Data {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String sensorData;
    private String time;

    //생성자는 데이터만
    public Data(String sensorData, String time) {
        this.sensorData = sensorData;
        this.time=time;
    }

    //게터 세터 제너레이트해서 만들어주기
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSensorData() {
        return sensorData;
    }

    public void setSensorData(String sensorData) {
        this.sensorData = sensorData;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    //toString 컨스트럭터에서 검색 -> toString을 재정의 (내용을 확인 하기 위해)
    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +", sensorData= " + sensorData + " , time= " + time + '\'' +
                '}';
    }



}
