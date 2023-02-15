package com.example.pokemon_demonstration2;

import static com.android.volley.Request.Method.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.PrecomputedText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.transform.Result;


public class MainActivity extends AppCompatActivity {

    // 1. CREATE JSON URL
    //private static String JSON_URL = "https://run.mocky.io/v3/1600974f-18ef-4ded-9058-32c118fff9c5";
    //private String json_url = "https://pokeapi.co/api/v2/pokemon?limit=1000&offset=1";
    //private String json_url = "https://pokeapi.co/api/v2/pokemon";
    //data members
    private RecyclerView recycler_view;
    private ArrayList<String> pokemonNameList = new ArrayList<>();
    private ArrayList<String> pokemonDetailsList = new ArrayList<>();
    private ArrayList<String> imageList    = new ArrayList<>();
    private RecyclerAdapter adapter;
    private Button sign_out;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        // sign out button
        sign_out = findViewById(R.id.btn_signout);

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //auth = FirebaseAuth.getInstance();
                //auth.signOut();
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "User has signed out", Toast.LENGTH_LONG).show();
                Intent i = new Intent(MainActivity.this, LogIn.class);
                startActivity(i);
                finish();
            }
        });

        //fetch data
        new backgroundTask().execute();

        // todo - implement the adapter
        //adapter = new RecyclerAdapter(pokemonNameList,imageList, MainActivity.this);
        //recycler_view.setAdapter(adapter);
    }

    public class backgroundTask extends AsyncTask<Void,Void,String> {

        ProgressDialog pd;
        String pokemon_name, pokemon_url;
        JSONObject pokemon = null;


        protected  String fetchDataByURL(String json_url ){
            StringBuilder builder = null;
            try {
                URL url = new URL(json_url);
                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                InputStreamReader reader = new InputStreamReader(con.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line = "";
                builder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Log.e("MJB", builder.toString());

            return builder.toString();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setTitle("Please wait...");
            pd.setMessage("JSON File Downloading...");
            pd.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String json_url = "https://pokeapi.co/api/v2/pokemon?limit=100&offset=0";
            String results = fetchDataByURL(json_url);

            try {
                pokemon = new JSONObject(results);
                int count = Integer.parseInt(pokemon.getString("count"));
                JSONArray pokemon_array = new JSONArray(pokemon.getString("results"));



                for (int i=0; i< pokemon_array.length();i++){
                    JSONObject obj = pokemon_array.getJSONObject(i);
                    pokemon_name = obj.getString("name");
                    pokemon_url = obj.getString("url");
                    Log.i("Pokemon Name", pokemon_name);
                    Log.i("Pokemon URL", pokemon_url);

                    results =  fetchDataByURL(pokemon_url);
                    //Log.i("results", results);

                    JSONObject obj_details = new JSONObject(results); //main details page
                    String sprites = obj_details.getString("sprites");
                    JSONObject sprites_obj = new JSONObject(sprites);
                    String back_default = sprites_obj.getString("back_default");
                    //Log.i("Sprites",back_default);

                    pokemonNameList.add(pokemon_name);
                    imageList.add(back_default);
                    adapter = new RecyclerAdapter(pokemonNameList, imageList, MainActivity.this);
                    //recycler_view.setAdapter(adapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
           //Log.i("mjb",s);
            String results;

            //adapter = new RecyclerAdapter(pokemonNameList, pokemonDetailsList, imageList, MainActivity.this);
            recycler_view.setAdapter(adapter);
        }
    }

} // end MainActivity