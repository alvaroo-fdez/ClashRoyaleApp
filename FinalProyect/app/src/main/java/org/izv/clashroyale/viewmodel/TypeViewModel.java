package org.izv.clashroyale.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.izv.clashroyale.model.entity.Type;
import org.izv.clashroyale.model.repository.CardRepository;

import java.util.List;

public class TypeViewModel extends AndroidViewModel {

    private CardRepository repository;

    public TypeViewModel(@NonNull Application application) {
        super(application);
        repository = new CardRepository(application);
    }

    public void insertType(Type... types) {
        repository.insertType(types);
    }

    public void updateType(Type... types) {
        repository.updateType(types);
    }

    public void deleteType(Type... types) {
        repository.deleteType(types);
    }

    public LiveData<List<Type>> getTypes() {
        return repository.getTypes();
    }

    public LiveData<Type> getType(long id) {
        return repository.getType(id);
    }
}
