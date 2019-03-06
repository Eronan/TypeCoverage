package com.example.ernesttran.typecoverage;

import android.arch.persistence.room.*;
import java.util.List;

@Dao
public interface PokemonDAO {
    @Query("SELECT * FROM Pokemon")
    List<Pokemon> getAll();

    @Query("SELECT * FROM Pokemon WHERE id IN (:Ids)")
    List<Pokemon> loadAllByIds(int[] Ids);

    @Query("SELECT * FROM Pokemon WHERE Species LIKE :speciesName")
    Pokemon findByName(String speciesName);

    @Insert
    void insertAll(Pokemon... pokemons);

    @Delete
    void delete(Pokemon pokemon);
}
