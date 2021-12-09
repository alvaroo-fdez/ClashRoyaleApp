package org.izv.clashroyale.model.room;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import org.izv.clashroyale.model.entity.Card;
import org.izv.clashroyale.model.entity.Type;

@Database(entities = {Card.class, Type.class}, version = 1, exportSchema = false)
public abstract class CardDatabase extends RoomDatabase {

    public abstract CardDao getDao();

    private static volatile CardDatabase INSTANCE;

    /* versión simplificada */
    public static CardDatabase getDatabase(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CardDatabase.class, "card").build();
        }
        return INSTANCE;
    }

}