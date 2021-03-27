package com.example.android_assignment;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao
{
    @Query("SELECT * FROM data")
    List<Data> getAll();

    @Insert
    void insert(Data data);

    @Delete
    void delete(Data data);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Data... datas);

}
