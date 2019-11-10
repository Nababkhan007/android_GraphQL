package com.example.graphqlpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String name;
    private String authorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getBooks();
    }

    public void getBooks() {
        MyApolloClient.getMyApolloClient().query(
                AllBooksQuery.builder().build()).enqueue(new ApolloCall.Callback<AllBooksQuery.Data>() {
            @Override
            public void onResponse(@NotNull final Response<AllBooksQuery.Data> response) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        List<AllBooksQuery.AllBook> allBookList = response.data().allBooks();
                        name = allBookList.get(2).name;
                        authorName = allBookList.get(2).authorName;

                        Toast.makeText(MainActivity.this,
                                "" + name + " " + authorName,
                                Toast.LENGTH_LONG).show();

                    }
                });
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Toast.makeText(MainActivity.this,
                        ""+e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
