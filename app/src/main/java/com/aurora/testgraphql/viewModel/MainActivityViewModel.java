package com.aurora.testgraphql.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.aurora.testgraphql.model.Repository;

public class MainActivityViewModel extends AndroidViewModel {
    Context context;
    Repository repository;
    Application application;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
        this.application = application;
    }

}
