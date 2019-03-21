package com.example.ernesttran.typecoverage;

import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.sqlite.*;
import android.database.Cursor;
import android.transition.Visibility;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.io.IOException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    //<editor-fold desc="Widget Variables">
    //Activity Widgets
    EditText edit_Generation;
    ImageButton editGeneration_Up;
    ImageButton editGeneration_Down;
    CheckBox checkBox_Images;
    CheckBox checkBox_Abilities;
    CheckBox checkBox_Moves;

    //Pokemon Widgets
    ///Pokemon 1
    ImageButton pokemon1_ExpandButton;
    ImageView pokemon1_Image;
    AutoCompleteTextView pokemon1_EditText;
    LinearLayout pokemon1_MoveLayout;
    AutoCompleteTextView pokemon1_Ability;
    AutoCompleteTextView pokemon1_Move1;
    AutoCompleteTextView pokemon1_Move2;
    AutoCompleteTextView pokemon1_Move3;
    AutoCompleteTextView pokemon1_Move4;
    ///Pokemon 2
    ImageButton pokemon2_ExpandButton;
    ImageView pokemon2_Image;
    AutoCompleteTextView pokemon2_EditText;
    LinearLayout pokemon2_MoveLayout;
    AutoCompleteTextView pokemon2_Ability;
    AutoCompleteTextView pokemon2_Move1;
    AutoCompleteTextView pokemon2_Move2;
    AutoCompleteTextView pokemon2_Move3;
    AutoCompleteTextView pokemon2_Move4;
    ///Pokemon 3
    ImageButton pokemon3_ExpandButton;
    ImageView pokemon3_Image;
    AutoCompleteTextView pokemon3_EditText;
    LinearLayout pokemon3_MoveLayout;
    AutoCompleteTextView pokemon3_Ability;
    AutoCompleteTextView pokemon3_Move1;
    AutoCompleteTextView pokemon3_Move2;
    AutoCompleteTextView pokemon3_Move3;
    AutoCompleteTextView pokemon3_Move4;
    ///Pokemon 4
    ImageButton pokemon4_ExpandButton;
    ImageView pokemon4_Image;
    AutoCompleteTextView pokemon4_EditText;
    LinearLayout pokemon4_MoveLayout;
    AutoCompleteTextView pokemon4_Ability;
    AutoCompleteTextView pokemon4_Move1;
    AutoCompleteTextView pokemon4_Move2;
    AutoCompleteTextView pokemon4_Move3;
    AutoCompleteTextView pokemon4_Move4;
    ///Pokemon 5
    ImageButton pokemon5_ExpandButton;
    ImageView pokemon5_Image;
    AutoCompleteTextView pokemon5_EditText;
    LinearLayout pokemon5_MoveLayout;
    AutoCompleteTextView pokemon5_Ability;
    AutoCompleteTextView pokemon5_Move1;
    AutoCompleteTextView pokemon5_Move2;
    AutoCompleteTextView pokemon5_Move3;
    AutoCompleteTextView pokemon5_Move4;
    ///Pokemon 6
    ImageButton pokemon6_ExpandButton;
    ImageView pokemon6_Image;
    AutoCompleteTextView pokemon6_EditText;
    LinearLayout pokemon6_MoveLayout;
    AutoCompleteTextView pokemon6_Ability;
    AutoCompleteTextView pokemon6_Move1;
    AutoCompleteTextView pokemon6_Move2;
    AutoCompleteTextView pokemon6_Move3;
    AutoCompleteTextView pokemon6_Move4;
    //</editor-fold>
    //<editor-fold desc="Data Variables">
    //Data
    SQLiteDatabase allData;
    PKMNDatabaseHelper mDBHelper;
    HashMap<String, Integer> PokemonList = new HashMap<String, Integer>();
    HashMap<String, Integer> MoveList = new HashMap<String, Integer>();
    HashMap<String, Integer> AbilityList = new HashMap<String, Integer>();
    String[] SpeciesNames;
    String[] MoveNames;
    String[] AbilityNames;
    ArrayAdapter<String> PokemonAdapter;
    ArrayAdapter<String> DefaultMoveAdapter;
    ArrayAdapter<String> DefaultAbilityAdapter;
    //</editor-fold>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //<editor-fold desc="Database Loading">
        //Database Loading
        mDBHelper = new PKMNDatabaseHelper(this);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            allData = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
        //</editor-fold>

        //<editor-fold desc="Get Names for Pokemon">
        Cursor speciesResults = allData.rawQuery("SELECT ID, Species FROM Pokemon",null);
        int length = speciesResults.getCount();
        SpeciesNames = new String[length];

        //Start on first Row
        if (speciesResults.moveToFirst()) {
            for (int i = 0; i < length; i++) {
                //Get Information from Cursor
                Integer currID = speciesResults.getInt(0);
                String currName = speciesResults.getString(1);
                PokemonList.put(currName, currID);
                SpeciesNames[i] = currName;
                //Move to next Row
                speciesResults.moveToNext();
            }
        }

        //Build Array Adapter
        PokemonAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, SpeciesNames);
        //</editor-fold>

        //<editor-fold desc="Get Names for Moves">
        Cursor moveResults = allData.rawQuery("SELECT ID, Move FROM Moves", null);
        length = moveResults.getCount();
        MoveNames = new String[length];

        //Move to First Row
        if (moveResults.moveToFirst()) {
           //Load Data into HashMap
            for (int i = 0; i < length; i++) {
                Integer currID = moveResults.getInt(0);
                String currName = moveResults.getString(1);
                MoveList.put(currName, currID);
                MoveNames[i] = currName;
            }
        }
        DefaultMoveAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, MoveNames);
        //</editor-fold>

        //<editor-fold desc="Get Names for Abilities">
        Cursor abilityResults = allData.rawQuery("SELECT ID, Name FROM Abilities", null);
        length = abilityResults.getCount();
        AbilityNames = new String[length];

        //Move to First Row
        if (abilityResults.moveToFirst()) {
            //Load Data into HashMap
            for (int i = 0; i < length; i++) {
                Integer currID = abilityResults.getInt(0);
                String currName = abilityResults.getString(1);
                AbilityList.put(currName, currID);
                AbilityNames[i] = currName;
            }
        }
        DefaultAbilityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, AbilityNames);
        //</editor-fold>

        //<editor-fold desc="Initialize Generation Widgets">
        ///EditText
        edit_Generation = findViewById(R.id.editText_Generation);
        ///UpButton
        editGeneration_Up = findViewById(R.id.button_UpGeneration);
        ///DownButton
        editGeneration_Down = findViewById(R.id.button_DownGeneration);
        //</editor-fold>

        //<editor-fold desc="Initialize CheckBox Widgets">
        ///Images
        checkBox_Images = findViewById(R.id.checkBox_Images);
        ///Abilities
        checkBox_Abilities = findViewById(R.id.checkBox_Abilities);
        ///Moves
        checkBox_Moves = findViewById(R.id.checkBox_Moves);
        //</editor-fold>


        //<editor-fold desc="Initialize all Pokemon Widgets">
        //<editor-fold desc="Initialize Pokemon 1 Widgets">
        ///Expand Moves
        pokemon1_ExpandButton = findViewById(R.id.button_ExpandMoves1);
        pokemon1_ExpandButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String species = pokemon1_EditText.getText().toString();
                if (!PokemonList.containsKey(species) || pokemon1_MoveLayout.getVisibility() == View.VISIBLE) {
                    pokemon1_MoveLayout.setVisibility(View.GONE);
                } else {
                    pokemon1_MoveLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        ///Image
        pokemon1_Image = findViewById(R.id.imageView_party1);
        ///Species Name
        pokemon1_EditText = findViewById(R.id.textView_party1);
        pokemon1_EditText.setAdapter(PokemonAdapter);
        ///Move Group
        pokemon1_MoveLayout = findViewById(R.id.linearLayout_Moveset1);
        //Ability
        pokemon1_Ability = findViewById(R.id.textView_Ability1);
        ///Move 1
        pokemon1_Move1 = findViewById(R.id.textView_Move1_1);
        ///Move 2
        pokemon1_Move2 = findViewById(R.id.textView_Move1_2);
        ///Move 3
        pokemon1_Move3 = findViewById(R.id.textView_Move1_3);
        ///Move 4
        pokemon1_Move4 = findViewById(R.id.textView_Move1_4);
        //</editor-fold>

        //<editor-fold desc="Initialize Pokemon 2 Widgets">
        ///Expand Moves
        pokemon2_ExpandButton = findViewById(R.id.button_ExpandMoves2);
        pokemon2_ExpandButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String species = pokemon2_EditText.getText().toString();
                if (!PokemonList.containsKey(species) || pokemon2_MoveLayout.getVisibility() == View.VISIBLE) {
                    pokemon2_MoveLayout.setVisibility(View.GONE);
                } else {
                    pokemon2_MoveLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        ///Image
        pokemon2_Image = findViewById(R.id.imageView_party2);
        ///Species Name
        pokemon2_EditText = findViewById(R.id.textView_party2);
        pokemon2_EditText.setAdapter(PokemonAdapter);
        ///Move Group
        pokemon2_MoveLayout = findViewById(R.id.linearLayout_Moveset2);
        //Ability
        pokemon2_Ability = findViewById(R.id.textView_Ability2);
        ///Move 1
        pokemon2_Move1 = findViewById(R.id.textView_Move2_1);
        ///Move 2
        pokemon2_Move2 = findViewById(R.id.textView_Move2_2);
        ///Move 3
        pokemon2_Move3 = findViewById(R.id.textView_Move2_3);
        ///Move 4
        pokemon2_Move4 = findViewById(R.id.textView_Move2_4);
        //</editor-fold>

        //<editor-fold desc="Initialize Pokemon 3 Widgets">
        ///Expand Moves
        pokemon3_ExpandButton = findViewById(R.id.button_ExpandMoves3);
        pokemon3_ExpandButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String species = pokemon3_EditText.getText().toString();
                if (!PokemonList.containsKey(species) || pokemon3_MoveLayout.getVisibility() == View.VISIBLE) {
                    pokemon3_MoveLayout.setVisibility(View.GONE);
                } else {
                    pokemon3_MoveLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        ///Image
        pokemon3_Image = findViewById(R.id.imageView_party3);
        ///Species Name
        pokemon3_EditText = findViewById(R.id.textView_party3);
        pokemon3_EditText.setAdapter(PokemonAdapter);
        ///Move Group
        pokemon3_MoveLayout = findViewById(R.id.linearLayout_Moveset3);
        //Ability
        pokemon3_Ability = findViewById(R.id.textView_Ability3);
        ///Move 1
        pokemon3_Move1 = findViewById(R.id.textView_Move3_1);
        ///Move 2
        pokemon3_Move2 = findViewById(R.id.textView_Move3_2);
        ///Move 3
        pokemon3_Move3 = findViewById(R.id.textView_Move3_3);
        ///Move 4
        pokemon3_Move4 = findViewById(R.id.textView_Move3_4);
        //</editor-fold>

        //<editor-fold desc="Initialize Pokemon 4 Widgets">
        ///Expand Moves
        pokemon4_ExpandButton = findViewById(R.id.button_ExpandMoves4);
        pokemon1_ExpandButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String species = pokemon1_EditText.getText().toString();
                if (!PokemonList.containsKey(species) || pokemon1_MoveLayout.getVisibility() == View.VISIBLE) {
                    pokemon1_MoveLayout.setVisibility(View.GONE);
                } else {
                    pokemon1_MoveLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        ///Image
        pokemon4_Image = findViewById(R.id.imageView_party4);
        ///Species Name
        pokemon4_EditText = findViewById(R.id.textView_party4);
        pokemon4_EditText.setAdapter(PokemonAdapter);
        ///Move Group
        pokemon4_MoveLayout = findViewById(R.id.linearLayout_Moveset4);
        //Ability
        pokemon4_Ability = findViewById(R.id.textView_Ability4);
        ///Move 1
        pokemon4_Move1 = findViewById(R.id.textView_Move4_1);
        ///Move 2
        pokemon4_Move2 = findViewById(R.id.textView_Move4_2);
        ///Move 3
        pokemon4_Move3 = findViewById(R.id.textView_Move4_3);
        ///Move 4
        pokemon4_Move4 = findViewById(R.id.textView_Move4_4);
        //</editor-fold>

        //<editor-fold desc="Initialize Pokemon 5 Widgets">
        ///Expand Moves
        pokemon5_ExpandButton = findViewById(R.id.button_ExpandMoves5);
        pokemon5_ExpandButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String species = pokemon5_EditText.getText().toString();
                if (!PokemonList.containsKey(species) || pokemon5_MoveLayout.getVisibility() == View.VISIBLE) {
                    pokemon5_MoveLayout.setVisibility(View.GONE);
                } else {
                    pokemon5_MoveLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        ///Image
        pokemon5_Image = findViewById(R.id.imageView_party5);
        ///Species Name
        pokemon5_EditText = findViewById(R.id.textView_party5);
        pokemon5_EditText.setAdapter(PokemonAdapter);
        ///Move Group
        pokemon5_MoveLayout = findViewById(R.id.linearLayout_Moveset5);
        //Ability
        pokemon5_Ability = findViewById(R.id.textView_Ability5);
        ///Move 1
        pokemon5_Move1 = findViewById(R.id.textView_Move5_1);
        ///Move 2
        pokemon5_Move2 = findViewById(R.id.textView_Move5_2);
        ///Move 3
        pokemon5_Move3 = findViewById(R.id.textView_Move5_3);
        ///Move 4
        pokemon5_Move4 = findViewById(R.id.textView_Move5_4);
        //</editor-fold>

        //<editor-fold desc="Initialize Pokemon 6 Widgets">
        ///Expand Moves
        pokemon6_ExpandButton = findViewById(R.id.button_ExpandMoves6);
        pokemon6_ExpandButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String species = pokemon6_EditText.getText().toString();
                if (!PokemonList.containsKey(species) || pokemon6_MoveLayout.getVisibility() == View.VISIBLE) {
                    pokemon6_MoveLayout.setVisibility(View.GONE);
                } else {
                    pokemon6_MoveLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        ///Image
        pokemon6_Image = findViewById(R.id.imageView_party6);
        ///Species Name
        pokemon6_EditText = findViewById(R.id.textView_party6);
        pokemon6_EditText.setAdapter(PokemonAdapter);
        ///Move Group
        pokemon6_MoveLayout = findViewById(R.id.linearLayout_Moveset6);
        //Ability
        pokemon6_Ability = findViewById(R.id.textView_Ability6);
        ///Move 1
        pokemon6_Move1 = findViewById(R.id.textView_Move6_1);
        ///Move 2
        pokemon6_Move2 = findViewById(R.id.textView_Move6_2);
        ///Move 3
        pokemon6_Move3 = findViewById(R.id.textView_Move6_3);
        ///Move 4
        pokemon6_Move4 = findViewById(R.id.textView_Move6_4);
        //</editor-fold>
        //</editor-fold>
    }

    public void updatePokemon1() {
        //Get Species Name
        String speciesName = pokemon1_EditText.getText().toString();
        Integer speciesNo = PokemonList.get(speciesName);
        String generationCalc = edit_Generation.getText().toString();
        //Update Abilities
        Cursor abilityResults = allData.rawQuery("SELECT Name FROM Abilities" +
                        " WHERE ID IN (SELECT AbilityID FROM (SELECT AbilityID, Slot, MAX(Generation) FROM Pkmn_Abilities"+
                        " WHERE SpeciesID=? AND Generation<=? GROUP BY SpeciesID, Slot ORDER BY Slot ASC))",
                new String[] {speciesNo.toString(), generationCalc});
        int length = abilityResults.getCount();
        String[] availableAbilities = new String[length];
        if (abilityResults.moveToFirst()) {
            for (int i = 0; i < length; i++) {
                availableAbilities[i] = abilityResults.getString(0);
                abilityResults.moveToNext();
            }
        }
        //Update Moves
        Cursor moveResults = allData.rawQuery("SELECT Move FROM Moves "+
                        "WHERE ID IN (SELECT Move FROM Pkmn_Learnsets" +
                            "WHERE Generation=? AND Species=?))",
                new String[] {generationCalc, speciesNo.toString()});
        length = moveResults.getCount();
        String[] availableMoves = new String[length];
        if (moveResults.moveToFirst()) {
            for (int i = 0; i < length; i++) {
                availableMoves[i] = moveResults.getString(0);
                moveResults.moveToNext();
            }
        }
    }
}
