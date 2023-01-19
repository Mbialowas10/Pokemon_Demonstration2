package com.example.pokemon_demonstration2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //data members
    private RecyclerView recycler_view;
    private ArrayList<String> pokemonNameList = new ArrayList<>();
    private ArrayList<String> pokemonDetailsList = new ArrayList<>();
    private ArrayList<Integer> imageList    = new ArrayList<>();
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        // populating arrays lists
        pokemonNameList.add("Bulbasaur");
        pokemonNameList.add("Charizard");
        pokemonNameList.add("Charmander");
        pokemonNameList.add("Squirtle");

        // add pokemon description
        pokemonDetailsList.add("A dinosaur");
        pokemonDetailsList.add("A type of dragon");
        pokemonDetailsList.add("A cute dragon");
        pokemonDetailsList.add("A friendly turtle, not related to leonardo from ninja turles :)");

        imageList.add(R.drawable.bulbasaur);
        imageList.add(R.drawable.charizard);
        imageList.add(R.drawable.charmander);
        imageList.add(R.drawable.squirtle);

        // todo - implement the adapter
        adapter = new RecyclerAdapter(pokemonNameList,pokemonDetailsList,imageList, MainActivity.this);
        recycler_view.setAdapter(adapter);







    }
}