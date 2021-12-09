package org.izv.clashroyale.view.adapter.viewholder;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.izv.card.R;
import org.izv.clashroyale.model.entity.Card;
import org.izv.clashroyale.view.activity.EditCard;

public class CardViewHolder extends RecyclerView.ViewHolder {

    public TextView tvName, tvType, tvLevel, tvElixir, tvUrl;
    public ImageButton btDelete;
    public ImageView ivCard;
    public Card card;

    public CardViewHolder(@NonNull View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tvName);
        tvType = itemView.findViewById(R.id.tvType);
        tvLevel = itemView.findViewById(R.id.tvLevel);
        tvElixir = itemView.findViewById(R.id.tvElixir);
        tvUrl = itemView.findViewById(R.id.tvUrl);
        ivCard = itemView.findViewById(R.id.ivCard);
        itemView.setOnClickListener(v -> {
            Log.v("xyzyx", "onclick" + card.name);
            Intent intent = new Intent(itemView.getContext(), EditCard.class);
            intent.putExtra("card", card);
            itemView.getContext().startActivity(intent);
        });
    }
}
