package org.izv.clashroyale.model.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.izv.clashroyale.model.api.CardList;
import org.izv.clashroyale.model.entity.Card;
import org.izv.clashroyale.model.entity.CardType;
import org.izv.clashroyale.model.entity.Type;
import org.izv.clashroyale.model.room.CardDao;
import org.izv.clashroyale.model.room.CardDatabase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class CardRepository {

    private static final String INIT = "init";

    private HashMap<String, String> cardMap;
    private CardDao dao;
    private LiveData<List<CardType>> allCard;
    private LiveData<List<Card>> liveCards;
    private LiveData<List<Type>> liveTypes;
    private LiveData<Card> liveCard;
    private LiveData<Type> liveType;
    private MutableLiveData<Long> liveInsertResult;
    private MutableLiveData<List<Long>> liveInsertResults;
    private MutableLiveData<String> liveGetKalosResult;
    private SharedPreferences preferences;
    private CardList cardList;

    public CardRepository(Context context) {
        CardDatabase db = CardDatabase.getDatabase(context);
        cardList = new CardList();
        cardMap = new HashMap<>();
        dao = db.getDao();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        liveInsertResult = new MutableLiveData<>();
        liveInsertResults = new MutableLiveData<>();
        liveGetKalosResult = new MutableLiveData<>();
        if(!getInit()) {
            typesPreload();
            setInit();
        }
    }

    public void insertCard(Card card, Type type) {
        Runnable r = () -> {
            card.idtype = insertTypeGetId(type);
            if(card.idtype > 0) {
                dao.insertCard(card);
            }
        };
        new Thread(r).start();
    }

    public MutableLiveData<Long> getInsertResult() {
        return liveInsertResult;
    }

    public MutableLiveData<String> getKalosResult() {
        return liveGetKalosResult;
    }

    public MutableLiveData<List<Long>> getInsertResults() {
        return liveInsertResults;
    }

    private long insertTypeGetId(Type type) {
        List<Long> ids = dao.insertType(type);
        if(ids.get(0) < 1) {
            return dao.getIdType(type.name);
        } else {
            return ids.get(0);
        }
    }

    public void insertCard(Card... cards) {
        Runnable r = () -> {
            List<Long> resultList = dao.insertCard(cards);
            liveInsertResult.postValue(resultList.get(0));
            liveInsertResults.postValue(resultList);
        };
        new Thread(r).start();
    }

    public void insertType(Type... types) {
        Runnable r = () -> {
            dao.insertType(types);
        };
        new Thread(r).start();
    }

    public void updateCard(Card... cards) {
        Runnable r = () -> {
            dao.updateCard(cards);
        };
        new Thread(r).start();
    }

    public void updateType(Type... types) {
        Runnable r = () -> {
            dao.updateType(types);
        };
        new Thread(r).start();
    }

    public void deleteCards(Card... cards) {
        Runnable r = () -> {
            dao.deleteCards(cards);
        };
        new Thread(r).start();
    }

    public void deleteType(Type... types) {
        Runnable r = () -> {
            dao.deleteType(types);
        };
        new Thread(r).start();
    }

    public LiveData<List<Card>> getCards() {
        if(liveCards == null) {
            liveCards = dao.getCards();
        }
        return liveCards;
    }

    public LiveData<List<Type>> getTypes() {
        if(liveTypes == null) {
            liveTypes = dao.getTypes();
        }
        return liveTypes;
    }

    public LiveData<Card> getCard(long id) {
        if(liveCard == null) {
            liveCard = dao.getCard(id);
        }
        return liveCard;
    }

    public LiveData<Type> getType(long id) {
        if(liveType == null) {
            liveType = dao.getType(id);
        }
        return liveType;
    }

    public LiveData<List<CardType>> getAllCard() {
        if(allCard == null) {
            allCard = dao.getAllCard();
        }
        return allCard;
    }

    public void typesPreload() {
        String[] typeNames = new String[] {"Common", "Rare", "Epic", "Legendary", "Champion"};
        Type[] types = new Type[typeNames.length];
        Type type;
        int cont = 0;
        for (String s: typeNames) {
            type = new Type();
            type.name = s;
            types[cont] = type;
            cont++;
        }
    }

    public void setInit() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(INIT, true);
        editor.commit();
    }

    public boolean getInit() {
        return preferences.getBoolean(INIT, false);
    }


    public String getUrl(String cardName) {
        String url = cardMap.get(cardName.toLowerCase());
        if(url == null) {
            url = "https://pm1.narvii.com/6217/2010da6602371ce547ddb34cc1660856c11248aa_hq.jpg";
        }
        return url;
    }

    private void populateHashMap(String string) {
        String name, url;
        try {
            JSONArray jsonArray = new JSONArray(string);
            JSONObject jsonObject;
            for (int i = 0, size = jsonArray.length(); i < size; i++) {
                jsonObject = jsonArray.getJSONObject(i);
                name = jsonObject.getString("name").toLowerCase();
                url = jsonObject.getString("ThumbnailImage");
                cardMap.put(name, url);
            }
        } catch (JSONException e) {
        }
    }
}