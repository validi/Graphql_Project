package com.aurora.testgraphql.servies;

import android.app.Application;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.cache.http.ApolloHttpCache;
import com.apollographql.apollo.cache.http.DiskLruHttpCacheStore;

import java.io.File;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class GraphqlInstance {

    public static ApolloClient apolloClient;
    public static String BaseUrl ="https://api.github.com/graphql";



    public static ApolloClient getServise(Application application) {
        if (apolloClient == null) {
            //Directory where cached responses will be stored
            File file = new File(application.getApplicationContext().getFilesDir(), "apolloCache");

            //Size in bytes of the cache
            long size = 1024*1024;

            //Create the http response cache store
            DiskLruHttpCacheStore cacheStore = new DiskLruHttpCacheStore(file, size);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();
                            // Request customization: add request headers
                            Request.Builder requestBuilder = original.newBuilder()
                                    .header("Authorization", "Bearer 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"); // <-- this is the important line

                            Request request = requestBuilder.build();
                            return chain.proceed(request);
                        }
                    })
                    .build();
            apolloClient = ApolloClient.builder()
                    .serverUrl(BaseUrl)
                    .httpCache(new ApolloHttpCache(cacheStore))
                    .okHttpClient(okHttpClient)
//                    .normalizedCache(cacheFactory)

                    .build();
        }
        return apolloClient;
    }

}
