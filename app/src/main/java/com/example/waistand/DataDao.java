package com.example.waistand;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DataDao {

    //Data 에는 센서값이 여러개 들어 있을 거니까 List안에
    @Query("SELECT*FROM Data")
    List<Data> getAll();

    /*@Query("SElECT sensorData FROM Data")
    List<Data> getSensor();*/

    @Query("SELECT id,time FROM Data ")
    List<Data> getTime();

    @Insert
    void insert(Data data);

    @Update
    void upadate(Data data);

    @Delete
    void delete(Data data);

    @Query("DELETE FROM Data")
    void clearAll();
}
