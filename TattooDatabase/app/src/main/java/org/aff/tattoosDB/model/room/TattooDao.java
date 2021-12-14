package org.aff.tattoosDB.model.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.aff.tattoosDB.model.entity.Tattoo;
import org.aff.tattoosDB.model.entity.TattooType;
import org.aff.tattoosDB.model.entity.Type;

import java.util.List;

@Dao
public interface TattooDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertTattoo(Tattoo tattoo);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertType(Type type);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    List<Long> insertTattoos(Tattoo... tattoos);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    List<Long> insertTypes(Type ...types);

    @Update
    int updateTattoo(Tattoo tattoo);

    @Delete
    int deleteTattoo(Tattoo tattoo);

    @Update
    int updateType(Type type);

    @Delete
    int deleteType(Type type);

    @Query("SELECT * FROM Tattoos WHERE id = :id ")
    LiveData<Tattoo> getTattoo(long id);

    @Query("SELECT * FROM tattootype WHERE id = :id")
    LiveData<Type> getType(long id);

    @Query("SELECT * FROM Tattoos ORDER BY date ASC")
    LiveData<List<Tattoo>> getTattoos();

    @Query("SELECT * FROM tattootype ORDER BY id ASC")
    LiveData<List<Type>> getTypes();

    @Query("select c.*, ct.id type_id, ct.name type_name " +
            "from Tattoos c join tattootype ct on c.idtype = ct.id order by " +
            "name asc")
    LiveData<List<TattooType>> getAllTattoos();

    @Query("select id from tattootype where name = :name")
    long getIdType(String name);


}
