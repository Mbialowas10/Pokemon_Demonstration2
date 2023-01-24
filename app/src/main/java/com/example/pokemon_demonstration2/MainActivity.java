package com.example.pokemon_demonstration2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // 1. CREATE JSON URL
    private static String JSON_URL = "https://run.mocky.io/v3/1600974f-18ef-4ded-9058-32c118fff9c5";


    //data members
    private RecyclerView recycler_view;
    private ArrayList<String> pokemonNameList = new ArrayList<>();
    private ArrayList<String> pokemonDetailsList = new ArrayList<>();
    private ArrayList<String> imageList    = new ArrayList<>();
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        GetData getData = new GetData();
        getData.execute();

        // todo - implement the adapter
        adapter = new RecyclerAdapter(pokemonNameList,pokemonDetailsList,imageList, MainActivity.this);
        recycler_view.setAdapter(adapter);
    }

    public class GetData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isr.read();
                    }

                    return current;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e)   {
                e.printStackTrace();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("pokemons");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    pokemonNameList.add(jsonObject1.getString("name"));
                    pokemonDetailsList.add(jsonObject1.getString("details"));
                    imageList.add(jsonObject1.getString("image"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            adapter = new RecyclerAdapter(pokemonNameList, pokemonDetailsList, imageList, MainActivity.this);
            recycler_view.setAdapter(adapter);
        }
    }
}