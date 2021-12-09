package org.izv.clashroyale.model.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.izv.clashroyale.model.entity.Card;
import org.izv.clashroyale.model.entity.CardType;
import org.izv.clashroyale.model.entity.Type;

import java.util.List;

@Dao
public interface CardDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    List<Long> insertCard(Card... cards);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insert(Card card);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    List<Long> insertType(Type... types);

    @Update
    int updateCard(Card... cards);

    @Update
    int updateType(Type... types);

    @Delete
    int deleteCards(Card... cards);

    @Delete
    int deleteType(Type... types);

    @Query("delete from cardtype")
    int deleteAllTypes();

    @Query("delete from Card")
    int deleteAllCard();

    @Query("select * from Card order by name asc")
    LiveData<List<Card>> getCards();

    @Query("select p.*, pt.id type_id, pt.name type_name from Card p join cardtype pt on p.idtype = pt.id order by name asc")
    LiveData<List<CardType>> getAllCard();

    @Query("select * from cardtype order by name asc")
    LiveData<List<Type>> getTypes();

    @Query("select * from Card where id = :id")
    LiveData<Card> getCard(long id);

    @Query("select * from cardtype where id = :id")
    LiveData<Type> getType(long id);

    @Query("select id from cardtype where name = :name")
    long getIdType(String name);

    @Query("select * from cardtype where name = :name")
    Type getType(String name);
}