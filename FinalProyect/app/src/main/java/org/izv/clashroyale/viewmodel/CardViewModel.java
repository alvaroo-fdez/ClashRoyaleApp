package org.izv.clashroyale.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.izv.clashroyale.model.entity.Card;
import org.izv.clashroyale.model.entity.CardType;
import org.izv.clashroyale.model.entity.Type;
import org.izv.clashroyale.model.repository.CardRepository;

import java.util.List;

public class CardViewModel extends AndroidViewModel {

    private CardRepository repository;

    public CardViewModel(@NonNull Application application) {
        super(application);
        repository = new CardRepository(application);
    }

    public void insertCard(Card... cards) {
        repository.insertCard(cards);
    }

    public void updateCard(Card... cards) {
        repository.updateCard(cards);
    }

    public void deleteCards(Card... cards) {
        repository.deleteCards(cards);
    }

    public LiveData<List<Card>> getCards() {
        return repository.getCards();
    }

    public LiveData<Card> getCard(long id) {
        return repository.getCard(id);
    }

    public void insertCard(Card card, Type type) {
        repository.insertCard(card, type);
    }

    public LiveData<List<CardType>> getAllCard() {
        return repository.getAllCard();
    }

    public MutableLiveData<Long> getInsertResult() {
        return repository.getInsertResult();
    }

    public MutableLiveData<List<Long>> getInsertResults() {
        return repository.getInsertResults();
    }


    public MutableLiveData<String> getKalosResult() {
        return repository.getKalosResult();
    }

    public String getUrl(String cardName) {
        return repository.getUrl(cardName);
    }
}
