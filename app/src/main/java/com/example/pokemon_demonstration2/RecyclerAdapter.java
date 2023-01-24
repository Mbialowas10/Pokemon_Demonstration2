package com.example.pokemon_demonstration2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PokemonViewHolder>{

    private ArrayList<String> pokemonNameList;
    private ArrayList<String> pokemonDetailsList;
    private ArrayList<String> imageList;
    private Context context;

    public RecyclerAdapter(ArrayList<String> pokemonNameList, ArrayList<String> pokemonDetailsList, ArrayList<String> imageList, Context context) {
        this.pokemonNameList = pokemonNameList;
        this.pokemonDetailsList = pokemonDetailsList;
        this.imageList = imageList;
        this.context = context;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        holder.text_name.setText(pokemonNameList.get(position));
        holder.text_details.setText(pokemonDetailsList.get(position));

        Glide.with(context)
                .load(imageList.get(position))
                .into(holder.image_view);

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 0)
                {
                    Toast.makeText(context, "You selected "+ holder.text_name.getText(), Toast.LENGTH_SHORT).show();
                }else if (position == 1 )
                {
                    Toast.makeText(context, "You selected "+ holder.text_name.getText(), Toast.LENGTH_SHORT).show();
                }else if (position == 2)
                {
                    Toast.makeText(context, "You selected "+ holder.text_name.getText(), Toast.LENGTH_SHORT).show();
                }else if (position == 3 )
                {
                    Toast.makeText(context, "You selected "+ holder.text_name.getText(), Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(context, "You selected no Pokemon please try again", Toast.LENGTH_SHORT).show();
                }
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


        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);

            text_name = itemView.findViewById(R.id.text_name);
            text_details = itemView.findViewById(R.id.text_details);
            image_view = itemView.findViewById(R.id.image_view);
            card_view  = itemView.findViewById(R.id.card_view);

        }
    }

}
