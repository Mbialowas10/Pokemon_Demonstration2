package com.example.pokemon_demonstration2;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PokemonViewHolder>{

    private ArrayList<String> pokemonNameList;
    private ArrayList<String> pokemonDetailsList;
    private ArrayList<String> imageList;
    private Context context;
    private Button sign_out;
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference db_ref;




    public RecyclerAdapter(ArrayList<String> pokemonNameList, ArrayList<String> imageList, Context context) {
        this.pokemonNameList = pokemonNameList;
        this.pokemonDetailsList = pokemonDetailsList;
        this.imageList = imageList;
        this.context = context;

        this.auth = FirebaseAuth.getInstance();
        this.db = FirebaseDatabase.getInstance();
        this.db_ref = db.getReference("likes");
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent, false);
        view.setClickable(true);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.text_name.setText(pokemonNameList.get(position));
        //holder.text_details.setText(pokemonDetailsList.get(position));
        holder.btn_remove.setVisibility(View.INVISIBLE);

        Glide.with(context)
                .load(imageList.get(position))
                .into(holder.image_view);

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            ProgressDialog pd;
            @Override
            public void onClick(View view) {
                //String character = pokemonNameList.get(position).toString();

                Toast.makeText(view.getContext(), "You have selected " + pokemonNameList.get(position), Toast.LENGTH_SHORT).show();
                //Log.i("Character", String.valueOf(view.getContext()));
                //Log.i("Character", character);

            }
        });
        holder.btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser currentUser = auth.getCurrentUser();


                if (currentUser != null ) {
                    Toast.makeText(view.getContext(), "Liked  " + pokemonNameList.get(position) + " by " + currentUser.getEmail(),Toast.LENGTH_LONG).show();
                    //Toast.makeText(view.getContext(), "By  " + currentUser, Toast.LENGTH_LONG).show();

                    //String key = db_ref.child(currentUser.getEmail()).push().getKey();
                    //Map<String, String> list = new HashMap<>();
                    //list.put("userEmail",currentUser.getEmail());
                    //list.put(key, pokemonNameList.get(position) );
                    List<String> list = new ArrayList<>();
                    list.add(pokemonNameList.get(position));

                    db_ref.child("user").push().setValue(currentUser.getEmail());
                    db_ref.child("user").push().setValue(list);
                    //DatabaseReference pokemonRef = db_ref.child("user").push().setValue(list);
                    holder.btn_like.setVisibility(View.INVISIBLE);
                    holder.btn_remove.setVisibility(View.VISIBLE);

                    //db_ref.setValue(list);
                    //db_ref.updateChildren(list);

                }

                //Log.i("UserMB", String.valueOf(currentUser));
            }

        });

        holder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pushId = db_ref.getKey();
                DatabaseReference childRef = db_ref.child(pushId);
                childRef.removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if (error != null ){
                            Toast.makeText(view.getContext(), "Something wrong vlaue could not be removed", Toast.LENGTH_LONG);
                        }else{
                            // data removed
                            Toast.makeText(view.getContext(),"Data removed from database", Toast.LENGTH_LONG);
                            holder.btn_remove.setVisibility(View.INVISIBLE);
                            holder.btn_like.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return pokemonNameList.size();
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder{
        private TextView text_name,text_details;
        private ImageView image_view;
        private CardView card_view;
        private Button btn_like, btn_remove;


        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);

            text_name = itemView.findViewById(R.id.text_name);
            text_details = itemView.findViewById(R.id.text_details);
            image_view = itemView.findViewById(R.id.image_view);
            card_view  = itemView.findViewById(R.id.card_view);
            btn_like = itemView.findViewById(R.id.btn_like);
            btn_remove = itemView.findViewById(R.id.btn_remove);
        }
    }

}
