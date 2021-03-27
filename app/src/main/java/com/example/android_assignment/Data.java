package com.example.android_assignment;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

@Entity
class Data
{
   @PrimaryKey
   @NonNull
   @ColumnInfo(name = "Name")
   public  String name;
   @ColumnInfo(name = "Capital")
   public  String capital;
   @ColumnInfo(name = "Flag")
   public  String flag;
   @ColumnInfo(name = "Region")
   public  String region;
   @ColumnInfo(name = "SubRegion")
   public  String subregion;
   @ColumnInfo(name = "Population")
   public  int population;
   @ColumnInfo(name = "Border")
   @TypeConverters(Converters.class)
   public  List<String> borders;
   @ColumnInfo(name = "Language")
   @TypeConverters(Converter2.class)
   public  List<Language> languages;
}


