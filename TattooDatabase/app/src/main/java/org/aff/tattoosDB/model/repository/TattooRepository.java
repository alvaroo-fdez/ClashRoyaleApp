package org.aff.tattoosDB.model.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Delete;

import org.aff.tattoosDB.model.entity.Tattoo;
import org.aff.tattoosDB.model.entity.TattooType;
import org.aff.tattoosDB.model.entity.Type;
import org.aff.tattoosDB.model.room.TattooDao;
import org.aff.tattoosDB.model.room.TattooDatabase;

import java.util.List;

public class TattooRepository {

    private static final String INIT = "init";
    private TattooDao tattooDao;
    private long resultTypeInsert;
    private List<Long> resultTypesInsert;

    private long resultTattooInsert;
    private int resultTatooUpdate;
    private int resultTattooDelete;
    private List<Long> resultTattoosInsert;

    private int resultTypeUpdate;
    private int resultTypeDelete;

    private LiveData<List<Tattoo>> liveTattooList;
    private LiveData<List<TattooType>> liveTattooTypeList;
    private LiveData<List<Type>> liveTypeList;

    private LiveData<Tattoo> liveTattoo;
    private LiveData<Type> liveType;
    private SharedPreferences preferences;

    private MutableLiveData<Long> liveInsertTattoo;
    private MutableLiveData<Integer> liveUpdateTattoo;
    private MutableLiveData<Integer> liveDeleteTattoo;
    private MutableLiveData<Integer> liveUpdateType;
    private MutableLiveData<Integer> liveDeleteType;
    private MutableLiveData<Long> liveInsertType;

    public TattooRepository(Context context) {
        TattooDatabase db = TattooDatabase.getDatabase(context);
        tattooDao = db.getDao();
        liveInsertTattoo = new MutableLiveData<>();
        liveUpdateTattoo = new MutableLiveData<>();
        liveDeleteTattoo = new MutableLiveData<>();
        liveInsertType = new MutableLiveData<>();
        liveUpdateType = new MutableLiveData<>();
        liveDeleteType = new MutableLiveData<>();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if(!getInit()){
            preloadTypes();
            setInit();
        }
        
    }

    public MutableLiveData<Long> getLiveInsertType() {
        return liveInsertType;
    }

    public MutableLiveData<Integer> getLiveUpdateType() {
        return liveUpdateType;
    }

    public MutableLiveData<Integer> getLiveDeleteType() {
        return liveDeleteType;
    }

    public long insertType(Type type) {
        Runnable r  = () ->{
            resultTypeInsert = tattooDao.insertType(type);
            liveInsertType.postValue(resultTypeInsert);
        };
        new Thread(r).start();
        return this.resultTypeInsert;
    }

    public int updateType(Type type) {
        Runnable r = () ->{
            resultTypeUpdate = tattooDao.updateType(type);
            liveUpdateType.postValue(resultTypeUpdate);
        };
        new Thread(r).start();
        return resultTypeUpdate;
    }

    public int deleteType(Type type) {
        Runnable r = ()->{
            resultTypeDelete = tattooDao.deleteType(type);
            liveDeleteType.postValue(resultTypeDelete);
        };
        new Thread(r).start();;
        return resultTypeDelete;
    }

    public MutableLiveData<Long> getLiveInsertTattoo() {
        return liveInsertTattoo;
    }

    public MutableLiveData<Integer> getLiveUpdateTattoo() {
        return liveUpdateTattoo;
    }

    public MutableLiveData<Integer> getLiveDeleteTattoo(){return liveDeleteTattoo;}

    public long insertTattoo(Tattoo tattoo) {
        Runnable r = () ->{
            resultTattooInsert = tattooDao.insertTattoo(tattoo);
            liveInsertTattoo.postValue(resultTattooInsert);
        };
        new Thread(r).start();
        return resultTattooInsert;
    }



    public void insertTattooType(Tattoo tattoo, Type type){
        Runnable r = () ->{
            tattoo.idtype = (int) insertTypeGetId(type);
            if(tattoo.idtype > 0){
                tattooDao.insertTattoo(tattoo);
            }
        };
        new Thread(r).start();
    }


    public List<Long> insertTattoos(Tattoo... tattoos) {
        Runnable r = () ->{
            resultTattoosInsert = tattooDao.insertTattoos(tattoos);
        };
        new Thread(r).start();
        return resultTattoosInsert;
    }


    public List<Long> insertTypes(Type... types) {
        Runnable r = () ->{
            resultTypesInsert = tattooDao.insertTypes(types);
        };
        new Thread(r).start();
        return resultTypesInsert;
    }

    @Delete
    public int deleteTattoo(Tattoo tattoo) {
        Runnable r = () ->{
            resultTattooDelete = tattooDao.deleteTattoo(tattoo);
            liveDeleteTattoo.postValue(resultTattooDelete);
        };
        new Thread(r).start();
        return resultTattooDelete;
    }

    public LiveData<Tattoo> getTattoo(long id) {
        if(liveTattoo == null){
            liveTattoo = tattooDao.getTattoo(id);
        }
        return liveTattoo;
    }


    public LiveData<Type> getType(long id) {
        if(liveType == null){
            liveType = tattooDao.getType(id);
        }
        return liveType;
    }


    public LiveData<List<Tattoo>> getTattoos() {
        if(liveTattooList == null){
            liveTattooList = tattooDao.getTattoos();
        }
        return liveTattooList;
    }


    public LiveData<List<Type>> getTypes() {
        if(liveTypeList == null){
            liveTypeList = tattooDao.getTypes();
        }
        return liveTypeList;
    }


    public LiveData<List<TattooType>> getAllTattoos() {
        if(liveTattooTypeList == null){
            liveTattooTypeList = tattooDao.getAllTattoos();
        }
        return liveTattooTypeList;
    }


    public int updateTattoo(Tattoo tattoo) {
        Runnable r = () ->{
            resultTatooUpdate = tattooDao.updateTattoo(tattoo);
            try {
                liveUpdateTattoo.postValue(resultTatooUpdate);
            }catch (Exception e){}
        };
        new Thread(r).start();
        return resultTatooUpdate;
    }

    private long insertTypeGetId(Type type){
        List<Long> ids = tattooDao.insertTypes(type);
        if(ids.get(0) < 1){
            return tattooDao.getIdType(type.name);
        } else {
            return ids.get(0);
        }
    }



    private void preloadTypes(){
        String[] typeNames = new String[] {"Blackwork","Tradicional", "Neotradicional", "Puntillista", "Acuarela", "Realista", "Ornamental", "Tribal"};
        Type[] types = new Type[typeNames.length];
        Type type;
        int cont = 0;
        for (String s: typeNames) {
            type = new Type();
            type.name = s;
            types[cont] = type;
            cont++;
        }
        insertTypes(types);
    }

    public void setInit() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(INIT, true);
        editor.commit();
    }

    public boolean getInit() {
        return preferences.getBoolean(INIT, false);
    }

}
