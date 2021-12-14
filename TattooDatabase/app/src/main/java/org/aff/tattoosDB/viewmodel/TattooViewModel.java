package org.aff.tattoosDB.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.aff.tattoosDB.model.entity.Tattoo;
import org.aff.tattoosDB.model.entity.TattooType;
import org.aff.tattoosDB.model.entity.Type;
import org.aff.tattoosDB.model.repository.TattooRepository;

import java.util.List;

public class TattooViewModel extends AndroidViewModel {

    private TattooRepository tattooRepository;

    public TattooViewModel(@NonNull Application application) {
        super(application);
        tattooRepository = new TattooRepository(application);
    }

    public long insertTattoo(Tattoo tattoo) {
        return tattooRepository.insertTattoo(tattoo);
    }

    public long insertType(Type type) {
        return tattooRepository.insertType(type);
    }

    public List<Long> insertTattoos(Tattoo... tattoos) {
        return tattooRepository.insertTattoos(tattoos);
    }

    public List<Long> insertTypes(Type... types) {
        return tattooRepository.insertTypes(types);
    }

    public LiveData<Tattoo> getTattoo(long id) {
        return tattooRepository.getTattoo(id);
    }

    public LiveData<Type> getType(long id) {
        return tattooRepository.getType(id);
    }

    public LiveData<List<Tattoo>> getTattoos() {
        return tattooRepository.getTattoos();
    }

    public LiveData<List<Type>> getTypes() {
        return tattooRepository.getTypes();
    }

    public LiveData<List<TattooType>> getAllTattoos() {
        return tattooRepository.getAllTattoos();
    }

    public void insertTattooType(Tattoo tattoo, Type type) {
        tattooRepository.insertTattooType(tattoo, type);
    }

    public int deleteTattoo(Tattoo tattoo) {
        return tattooRepository.deleteTattoo(tattoo);
    }

    public MutableLiveData<Long> getLiveInsertTattoo() {
        return tattooRepository.getLiveInsertTattoo();
    }

    public int updateTattoo(Tattoo tattoo) {
        return tattooRepository.updateTattoo(tattoo);
    }

    public MutableLiveData<Integer> getLiveUpdateTattoo() {
        return tattooRepository.getLiveUpdateTattoo();
    }

    public MutableLiveData<Integer> getLiveDeleteTattoo() {
        return tattooRepository.getLiveDeleteTattoo();
    }

    public MutableLiveData<Long> getLiveInsertType() {
        return tattooRepository.getLiveInsertType();
    }

    public MutableLiveData<Integer> getLiveUpdateType() {
        return tattooRepository.getLiveUpdateType();
    }

    public MutableLiveData<Integer> getLiveDeleteType() {
        return tattooRepository.getLiveDeleteType();
    }

    public int updateType(Type type) {
        return tattooRepository.updateType(type);
    }

    public int deleteType(Type type) {
        return tattooRepository.deleteType(type);
    }
}
