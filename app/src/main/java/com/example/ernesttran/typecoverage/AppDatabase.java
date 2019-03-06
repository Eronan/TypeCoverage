package com.example.ernesttran.typecoverage;

import android.arch.persistence.room.*;

@Database(entities = {Pokemon.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PokemonDAO pokemonDAO();
}
