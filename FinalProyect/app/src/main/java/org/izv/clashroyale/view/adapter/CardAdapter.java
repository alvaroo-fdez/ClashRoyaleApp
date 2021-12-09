package org.izv.clashroyale.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.izv.card.R;
import org.izv.clashroyale.model.entity.Card;
import org.izv.clashroyale.model.entity.CardType;
import org.izv.clashroyale.model.entity.Type;
import org.izv.clashroyale.view.adapter.viewholder.CardViewHolder;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardViewHolder> {

    private List<CardType> cardList;
    private Context context;

    public CardAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        /*view.setOnClickListener(v -> {
            Log.v("xyzyx", "onclick create ");
            Card p = (Card) view.getTag();
            Log.v("xyzyx", p.name);
        });*/
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        CardType cardType = cardList.get(position);
        Card card = cardType.card;
        holder.card = card;
        Type type = cardType.type;
        holder.tvUrl.setText(card.url);
        holder.tvElixir.setText(card.elixir + " " + context.getString(R.string.elixir));
        holder.tvLevel.setText(card.level + " " + context.getString(R.string.level));
        holder.tvType.setText(type.name);
        holder.tvName.setText(card.name);
        Glide.with(context).load(card.url).into(holder.ivCard);
    }

    @Override
    public int getItemCount() {
        if(cardList == null) {
            return 0;
        }
        return cardList.size();
    }

    public void setCardList(List<CardType> cardList) {
        /*if(this.cardList == null) {
            this.cardList = cardList;
        }*/
        this.cardList = cardList;
        notifyDataSetChanged();
    }
}
