package com.example.zeeshan.bookdonation.Client;


import com.example.zeeshan.bookdonation.Models.Books;
import com.example.zeeshan.bookdonation.Models.Login;
import com.example.zeeshan.bookdonation.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface UserClient {

    //for admin login
    @POST("login")
    Call<User> login(@Body Login login);

    @POST("logout")
    Call<Void> logout();


    //to retrieve all the books
    @GET("books")
    Call<List<Books>> getBooks();

    //to add a new event
    @POST("books")
    @FormUrlEncoded
    Call<Books> saveBooks(@Field("book_name") String book_name,
                          @Field("email") String email,
                          @Field("smester") String semester,
                          @Field("ph_no") String phone);


    //to update a current event
    @PUT("books/{books}")
    Call<Books> updateEvent(@Path("books") int id,
                            @Body Books event);

    //to delete an event
    @DELETE("books/{books}")
    Call<Void> deleteEvent(@Path("books") int id);


}


