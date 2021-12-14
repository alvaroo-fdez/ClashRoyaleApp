package org.aff.tattoosDB.model.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import org.aff.tattoosDB.model.entity.Tattoo;
import org.aff.tattoosDB.model.entity.Type;

@Database(entities = {Tattoo.class, Type.class}, version = 1, exportSchema = false)
public abstract class TattooDatabase extends RoomDatabase {

    public abstract TattooDao getDao();

    private static volatile TattooDatabase INSTANCE;

    /**/
    public static TattooDatabase getDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    TattooDatabase.class, "tattoos").build();
        }

        return INSTANCE;
    }
}
