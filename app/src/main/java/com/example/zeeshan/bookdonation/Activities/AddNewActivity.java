package com.example.zeeshan.bookdonation.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zeeshan.bookdonation.Client.UserClient;
import com.example.zeeshan.bookdonation.Models.Books;
import com.example.zeeshan.bookdonation.R;
import com.example.zeeshan.bookdonation.Retrofit.Retro;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddNewActivity extends AppCompatActivity {

    Button btnAdd;
    EditText etName,  etEmail, etPhone;
    Spinner spinner;
    String name, email, phone;
    Retrofit retrofit;
    UserClient userClient;
    ArrayAdapter<CharSequence> arrayAdapter;
    String semester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        etName = findViewById(R.id.etName);

        etEmail = findViewById(R.id.etMail);
        etPhone = findViewById(R.id.etPhone);

        spinner = findViewById(R.id.spinner);

        btnAdd = findViewById(R.id.btnUpdate);

        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.Semesters, R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);

        retrofit = Retro.getRetorfit();

        userClient = retrofit.create(UserClient.class);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if(position==0)
               {
                semester="semester 1";

               } else if (position==1)

               {
                   semester="semester 2";
               }else if (position==2)

               {
                   semester="semester 3";
               }else if (position==3)
               {
                    semester="semester 4";
               }
               else if (position==4)

               {semester="semester 5";

               }else if (position==5)

               {
                   semester="semester 6";
               }
                 else if (position==6)

               {
                   semester="semester 7";
               }else if (position==7)

               {
                   semester="semester 8";
               }else if (position==8)

               {
                   semester="other";
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
    }

    protected void add() {

        name = etName.getText().toString();

        email = etEmail.getText().toString();
        phone = etPhone.getText().toString();

        Call<Books> call = userClient.saveBooks(name ,  email,semester, phone);

        call.enqueue(new Callback<Books>() {


            @Override
            public void onResponse(Call<Books> call, Response<Books> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                    etName.setText("");
                    //etDateTime.setText("");
                    etEmail.setText("");
                    etPhone.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), "Response Failure", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Books> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Try again when network is strong", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
