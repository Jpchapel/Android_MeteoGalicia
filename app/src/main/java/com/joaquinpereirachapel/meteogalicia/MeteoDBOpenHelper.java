package com.joaquinpereirachapel.meteogalicia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MeteoDBOpenHelper extends SQLiteOpenHelper {
    public MeteoDBOpenHelper(@Nullable Context context) {
        super(context, "meteo.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE estacion (_id integer not null primary key,nome text not null," +
                "concello text not null,idprovincia integer not null);");

        db.execSQL("CREATE TABLE provincia (_id integer not null primary key, nome text not null);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
