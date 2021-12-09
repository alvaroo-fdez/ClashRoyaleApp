package org.izv.clashroyale.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import org.izv.clashroyale.model.repository.CardRepository;

public class CommonViewModel extends ViewModel {

    private Context context;
    private CardRepository repository;

    public CommonViewModel() {
    }

    public void setContext(Context context) {
        this.context = context;
    }
}