package com.aurora.testgraphql;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.GetRepositoriesQuery;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
        // First, create an `ApolloClient`
        // Replace the serverUrl with your GraphQL endpoint
        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl("https://api.github.com/graphql")
                .okHttpClient(okHttpClient)
                .build();


       //  Then enqueue your query
        apolloClient.query(new GetRepositoriesQuery(10)).enqueue(new ApolloCall.Callback<GetRepositoriesQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<GetRepositoriesQuery.Data> response) {
                Log.i("Test", response.toString());
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.i("Test", "Error" + e.getMessage());
            }
        });

    }


    public void run(String token) throws Exception {
        OkHttpClient client = new OkHttpClient();
        String url = "http://test.com";
        Request request = new Request.Builder()
                .url(url)
                //This adds the token to the header.
                .addHeader("Authorization", "Bearer " + token)
                .build();
        try (okhttp3.Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            System.out.println("Server: " + response.header("anykey"));

        }
    }
}