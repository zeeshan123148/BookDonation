package com.example.zeeshan.bookdonation.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.zeeshan.bookdonation.Adapter.RecyclerViewAdapter;
import com.example.zeeshan.bookdonation.Adapter.RecyclerViewAdapter;
import com.example.zeeshan.bookdonation.Client.UserClient;
import com.example.zeeshan.bookdonation.Models.Books;
import com.example.zeeshan.bookdonation.R;
import com.example.zeeshan.bookdonation.Retrofit.Retro;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewAllActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    Retrofit retrofit;
    UserClient userClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        retrofit = Retro.getRetorfit();

        userClient = retrofit.create(UserClient.class);

        Call<List<Books>> call = userClient.getBooks();

        call.enqueue(new Callback<List<Books>>() {
            @Override
            public void onResponse(Call<List<Books>> call, Response<List<Books>> response) {


                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "books available: " + response.body().size(), Toast.LENGTH_SHORT).show();
                    adapter = new RecyclerViewAdapter(ViewAllActivity.this, response.body());
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getApplicationContext(), "Response Failure " , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Books>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Try again when network is strong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
