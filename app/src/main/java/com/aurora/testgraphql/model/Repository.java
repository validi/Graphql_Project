package com.aurora.testgraphql.model;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.cache.http.HttpCachePolicy;
import com.apollographql.apollo.rx2.Rx2Apollo;

import com.aurora.testgraphql.Utility;
import com.aurora.testgraphql.servies.GraphqlInstance;
import com.example.GetProfileQuery;
import com.example.GetUserRepositoryQuery;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class Repository {

    CompositeDisposable compositeDisposable = new CompositeDisposable();


    private Application application;

    public Repository(Application application) {
        this.application = application;
    }


    public MutableLiveData<GetProfileQuery.Data> getMutableLiveDataProfile(Context context) {

        MutableLiveData<GetProfileQuery.Data> tMutableLiveData = new MutableLiveData<>();

        GetProfileQuery query = GetProfileQuery.builder().build();

// Create an ApolloCall object
        ApolloCall<GetProfileQuery.Data> apolloCall = GraphqlInstance.getServise(application)
                .query(query)

                .httpCachePolicy(HttpCachePolicy.NETWORK_FIRST);
        ;

// RxJava2 Observable
        Observable<com.apollographql.apollo.api.Response<GetProfileQuery.Data>>
                observable2 = Rx2Apollo.from(apolloCall);
       Utility.showDialogPleaseWait(context);
        compositeDisposable.add(observable2
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<com.apollographql.apollo.api.Response<GetProfileQuery.Data>>() {
                    @Override
                    public void onNext(@NonNull com.apollographql.apollo.api.Response<GetProfileQuery.Data> response) {
                        if (!response.hasErrors()) {
                            tMutableLiveData.setValue(response.getData());
                        }
Utility.dismissDialogPleaseWait();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("apolloTest", "Error: " + e.getMessage());
                       Utility.dismissDialogPleaseWait();
                    }

                    @Override
                    public void onComplete() {
                        Log.i("apolloTest", "onComplete");
                       Utility.dismissDialogPleaseWait();
                    }
                })

        );

        return tMutableLiveData;

    }

    public MutableLiveData<GetUserRepositoryQuery.Data> getMutableLiveDataRepository(Context context) {

        MutableLiveData<GetUserRepositoryQuery.Data> tMutableLiveData = new MutableLiveData<>();

        GetUserRepositoryQuery query = GetUserRepositoryQuery.builder().build();

        // Create an ApolloCall object
        ApolloCall<GetUserRepositoryQuery.Data> apolloCall = GraphqlInstance.getServise(application)
                .query(query)
                .httpCachePolicy(HttpCachePolicy.NETWORK_FIRST);

        // RxJava2 Observable
        Observable<com.apollographql.apollo.api.Response<GetUserRepositoryQuery.Data>>
                observable2 = Rx2Apollo.from(apolloCall);
        Utility.showDialogPleaseWait(context);
        compositeDisposable.add(observable2
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<com.apollographql.apollo.api.Response<GetUserRepositoryQuery.Data>>() {
                    @Override
                    public void onNext(@NonNull com.apollographql.apollo.api.Response<GetUserRepositoryQuery.Data> response) {
                        if (!response.hasErrors()) {
                            tMutableLiveData.setValue(response.getData());
                        }
                        Utility.dismissDialogPleaseWait();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("apolloTest", "Error: " + e.getMessage());
                        Utility.dismissDialogPleaseWait();
                    }

                    @Override
                    public void onComplete() {
                        Log.i("apolloTest", "onComplete");
                        Utility.dismissDialogPleaseWait();
                    }
                })

        );

        return tMutableLiveData;

    }


    public void clear() {
        compositeDisposable.clear();
    }

    public boolean isConnected() {
//استفاده از کانکت منیجر برای مطمن شدن از دسترسی و عدم دسترسی به اینترنت
        ConnectivityManager cm = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();
        if (net != null && net.isAvailable() && net.isConnected()) {
            return true;
        } else {
            // showSnackbar("شما به اینترنت دسترسی ندارید");
            // Toast.makeText(application, "به اینترنت دسترسی ندارید", Toast.LENGTH_SHORT).show();
            return false;
        }
//برای استفاده از کانکت منیجر باید دسترسی اون رو در منی فست فعال کنیم.
    }

//    public void showSnackbar(String text) {
//
////        Snackbar.make(ProfileFragment.parentLayout, text, Snackbar.LENGTH_LONG)
////                .setAction("فهمیدم", new View.OnClickListener() {
////                    @Override
////                    public void onClick(View view) {
////
////                    }
////                })
////                // .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
////                .show();
//    }

//    public Dialog dialogLoading(Context context,String textLoading,CancelDialog cancelDialog) {
//
//        Dialog dialogLoading = new Dialog(context);
//        dialogLoading.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        dialogLoading.setContentView(R.layout.dialog_loading);
//        dialogLoading.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialogLoading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialogLoading.setCanceledOnTouchOutside(false);
//
//        TextView textView=dialogLoading.findViewById(R.id.txt);
//        Button btnCancel=dialogLoading.findViewById(R.id.btnCancel);
//
//        textView.setText(textLoading);
//        btnCancel.setVisibility(View.VISIBLE);
//
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cancelDialog.onClick();
//            }
//        });
//
//        dialogLoading.show();
//        return dialogLoading;
//
//    }
//
//    public Dialog dialogLoading(Context context) {
//
////        Dialog dialogLoading = new Dialog(context);
////        dialogLoading.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
////        dialogLoading.setContentView(R.layout.dialog_loading);
////        dialogLoading.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
////        dialogLoading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
////        dialogLoading.setCanceledOnTouchOutside(false);
////
////
////
////        dialogLoading.show();
////        return dialogLoading;
//
//    }

    interface CancelDialog {
        void onClick();
    }
}
