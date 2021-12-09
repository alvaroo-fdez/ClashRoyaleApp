package org.izv.clashroyale.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.izv.card.R;
import org.izv.clashroyale.model.entity.CardType;
import org.izv.clashroyale.view.adapter.CardAdapter;
import org.izv.clashroyale.viewmodel.CardViewModel;

import java.util.List;

public class CardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        //lista de card
        RecyclerView rvCard = findViewById(R.id.rvCard);
        rvCard.setLayoutManager(new LinearLayoutManager(this));

        CardViewModel pvm = new ViewModelProvider(this).get(CardViewModel.class);
        CardAdapter cardAdapter = new CardAdapter(this);

        rvCard.setAdapter(cardAdapter);

        LiveData<List<CardType>> listaCardType = pvm.getAllCard();
        listaCardType.observe(this, cards -> {
            cardAdapter.setCardList(cards);
        });

        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateCardActivity.class);
            startActivity(intent);
        });

    }
}