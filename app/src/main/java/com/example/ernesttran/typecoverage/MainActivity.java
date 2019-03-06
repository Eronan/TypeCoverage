package com.example.ernesttran.typecoverage;

import android.database.DatabaseErrorHandler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.sqlite.*;
import android.database.Cursor;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase allData;
    String[] PokemonList;
    String filesDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //File Directory
        filesDir = getFilesDir().getPath();

        //Download Database


        //Load Database
        allData = SQLiteDatabase.openDatabase(filesDir + "/PKMNData.db",null,SQLiteDatabase.OPEN_READONLY, null);

        //Load Pokemon Array
        Cursor speciesList = allData.rawQuery("SELECT Species FROM  Pokemon", null);
        PokemonList = new String[speciesList.getCount()];
        speciesList.moveToFirst();
        for (int i = 0; i < speciesList.getCount(); i++) {
            PokemonList[i] = speciesList.getString(0);
            speciesList.moveToNext();
        }
        if (!speciesList.isClosed()) speciesList.close();
    }
}
