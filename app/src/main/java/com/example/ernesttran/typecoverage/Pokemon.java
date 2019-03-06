package com.example.ernesttran.typecoverage;

import java.io.Serializable;
import android.arch.persistence.room.*;

@Entity
public class Pokemon implements Serializable {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "Species")
    public String speciesName;

    @ColumnInfo(name = "Type1")
    public int type1;

    @ColumnInfo(name = "Type2")
    public int type2;

    @ColumnInfo(name = "Generation")
    public int generation;
}
