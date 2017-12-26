package com.example.zeeshan.bookdonation.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zeeshan.bookdonation.Client.UserClient;
import com.example.zeeshan.bookdonation.Models.Books;
import com.example.zeeshan.bookdonation.R;
import com.example.zeeshan.bookdonation.Retrofit.Retro;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdateActivity extends AppCompatActivity {

    Books books;
    EditText etName, etEmail, etSemester, etPhone;
    Gson gson;
    Button btnUpdate;
    Retrofit retrofit;
    Call<Books> call;
    UserClient userClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etMail);
        etSemester = findViewById(R.id.spinner);
        etPhone = findViewById(R.id.etPhone);

        btnUpdate = findViewById(R.id.btnUpdate);

        retrofit = Retro.getRetorfit();
        userClient = retrofit.create(UserClient.class);


        Intent intent = getIntent();
        String eventData = intent.getStringExtra("eventData");
        gson = new Gson();
        books = gson.fromJson(eventData, Books.class);

        etName.setText(books.getBookName());
        etEmail.setText(books.getEmail());
        etSemester.setText(books.getSmester());
        etPhone.setText(books.getPhNo());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Books> call = userClient.saveBooks(etName.getText().toString(), etEmail.getText().toString(), etSemester.getText().toString(),etPhone.getText().toString());
                call.enqueue(new Callback<Books>() {
                    @Override
                    public void onResponse(Call<Books> call, Response<Books> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getApplicationContext(), "Response Failure", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Books> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Try again Network is not successful", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}
