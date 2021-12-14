package org.aff.tattoosDB.view.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.aff.tattoosDB.R;
import org.aff.tattoosDB.model.entity.Tattoo;
import org.aff.tattoosDB.model.entity.TattooType;
import org.aff.tattoosDB.model.entity.Type;
import org.aff.tattoosDB.view.viewholder.TattooViewHolder;

import java.util.List;

public class TattooAdapter extends RecyclerView.Adapter<TattooViewHolder> {

    private List<TattooType> tattooList;
    private Context context;

    public TattooAdapter(Context context){this.context = context;}

    @NonNull
    @Override
    public TattooViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tattoo, parent, false);
        return new TattooViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TattooViewHolder holder, int position) {
        TattooType tattooType = tattooList.get(position);
        Tattoo tattoo = tattooType.tattoo;
        Type type = tattooType.type;
        holder.tattoo = tattoo;

        holder.tvUbi.setText(tattoo.ubicacion.toUpperCase());
        holder.tvColor.setText(context.getString(R.string.color) + " " + tattoo.color);
        holder.tvDuracion.setText(context.getString(R.string.duracion) + " " + tattoo.duracion + " sesiones");
        holder.tvHoras.setText(context.getString(R.string.horas)  + " " + tattoo.horas + " horas");
        holder.tvType.setText(context.getString(R.string.type_card)  + " " +  type.name);
        holder.tvDate.setText(context.getString(R.string.fechap)  + " " +  tattoo.date);
        Glide.with(context).load(Uri.parse(tattoo.url)).into(holder.ivTattoo);
    }

    @Override
    public int getItemCount() {
        if(tattooList == null){
            return 0;
        }
        return tattooList.size();
    }

    public void setTattooList(List<TattooType> tattooTypeList){
        this.tattooList = tattooTypeList;
        notifyDataSetChanged();
    }


}
