package com.example.pokemon_demonstration2;

import static com.android.volley.Request.Method.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.util.Log;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;





public class MainActivity extends AppCompatActivity {

    // 1. CREATE JSON URL
    //private static String JSON_URL = "https://run.mocky.io/v3/1600974f-18ef-4ded-9058-32c118fff9c5";
    private String url = "https://pokeapi.co/api/v2/pokemon?limit=1000&offset=1";

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

        //fetch data
        getData();

        // todo - implement the adapter
        adapter = new RecyclerAdapter(pokemonNameList,imageList, MainActivity.this);
        recycler_view.setAdapter(adapter);
    }
    /*
    public String[] fetchImage(String url){
        //final String[] str_image = {""};
        final String[] str_img = {""};

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(GET, url,null, new Response.Listener<JSONObject>() {
           @Override
           public void onResponse(JSONObject response) {
               //Log.i("Image", response.toString());
               try {
                   JSONObject pokemonDetails = response.getJSONObject("sprites");
                   //Log.i("PokemonDetails", pokemonDetails.getString("back_default"));
                   //str_image[0] = pokemonDetails.getString("back_default");
                   str_img[0] = pokemonDetails.getString("back_default");
                   Log.i("Logging", str_img[0]);

               } catch (JSONException e) {
                   e.printStackTrace();
               }

           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Log.i("API", "That didn't work!");
           }
       });
        queue.add(jsonObjectRequest);
        return str_img;



    }
    */
    public void getData(){


        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String qualified_url;

                final String[] pokemon_name = new String[1];
                String pokemon_url;
                String image[];

                ArrayList<String> result = null;

                final String[] sprite = new String[1];

                final JSONArray jsonArray;
                final JSONArray[] pokemonDetailsArray = {null};

                try {
                    Log.i("API", "Response is this => " + response.toString());
                    int pokemon_count = Integer.parseInt(response.getString("count"));
                    //Log.i("count", String.valueOf(pokemon_count));

                    for (int idx=1; idx < pokemon_count; idx++){
                        qualified_url = "https://pokeapi.co/api/v2/pokemon/" + idx + "/";
                        Log.i("qualified_url", qualified_url);



                    }

                    jsonArray = response.getJSONArray("results");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject pokemonObjs = jsonArray.getJSONObject(i);

                        pokemon_name[0] = pokemonObjs.getString("name");
                        pokemon_url = pokemonObjs.getString("url");
                        //Log.i("Objects", pokemon_name);
                        //Log.i("Objects", pokemon_url);
                        //image = fetchImage(pokemon_url);

                        //Log.i("LoggingInside", image[0]);



                    }
                    //pokemonNameList.add(jsonObject1.getString("name"));
                    //pokemonDetailsList.add(jsonObject1.getString("details"));
                    //imageList.add(jsonObject1.getString("image"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("API", "That didn't work!");
            }

        });
        queue.add(jsonObjectRequest);
    }


/*
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
*/


}