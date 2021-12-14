package org.aff.tattoosDB.view.viewholder;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import org.aff.tattoosDB.R;
import org.aff.tattoosDB.model.entity.Tattoo;

public class TattooViewHolder extends RecyclerView.ViewHolder {

    public TextView tvUbi, tvColor, tvDuracion, tvHoras, tvType, tvDate;
    public ImageView ivTattoo;
    private MaterialCardView card;
    public Tattoo tattoo;


    public TattooViewHolder(@NonNull View itemView) {
        super(itemView);
        tvUbi = itemView.findViewById(R.id.tvUbicacion);
        tvColor = itemView.findViewById(R.id.tvColor);
        tvDuracion = itemView.findViewById(R.id.tvDuracion);
        tvHoras = itemView.findViewById(R.id.tvHoras);
        tvType = itemView.findViewById(R.id.tvType);
        ivTattoo = itemView.findViewById(R.id.ivTattoo);
        card = itemView.findViewById(R.id.card);
        tvDate = itemView.findViewById(R.id.tvDate);

        itemView.setOnClickListener((View v)->{
            Bundle bundle = new Bundle();
            bundle.putParcelable("tattoo", tattoo);
            Navigation.findNavController(itemView).navigate(R.id.action_FirstFragment_to_editTattooFragment, bundle);
        });

    }

}
