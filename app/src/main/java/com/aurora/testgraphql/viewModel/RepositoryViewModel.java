package com.aurora.testgraphql.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.aurora.testgraphql.model.Repository;
import com.example.GetUserRepositoryQuery;

public class RepositoryViewModel extends AndroidViewModel {
    Context context;
    Repository repository;
    Application application;
    public RepositoryViewModel(@NonNull Application application) {
        super(application);
        this.repository=new Repository(application);
        this.application=application;
    }

    public LiveData<GetUserRepositoryQuery.Data> getRepository(){
        return repository.getMutableLiveDataRepository(context);
    }

    public void setContext(Context context) {
        this.context = context;
    }
}