package com.example.zeeshan.bookdonation.Adapter;

import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zeeshan.bookdonation.Activities.UpdateActivity;
import com.example.zeeshan.bookdonation.Client.UserClient;
import com.example.zeeshan.bookdonation.ManageEventsActivity;
import com.example.zeeshan.bookdonation.Models.Books;

import com.example.zeeshan.bookdonation.R;
import com.example.zeeshan.bookdonation.Retrofit.Retro;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ContactViewHolder> {


    private int delete;
    private Retrofit retrofit;
    private UserClient userClient;
    private Context context;
    private List<Books> books;

    public RecyclerViewAdapter(Context context, List<Books> books) {
        this.context = context;
        this.books = books;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, null);

        retrofit = Retro.getRetorfit();
        userClient = retrofit.create(UserClient.class);

        return new ContactViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ContactViewHolder holder, int position) {

        delete = position;
        final Books book = books.get(position);


        Picasso.with(context)
                .load(R.drawable.ic_launcher_background)
                .into(holder.circleImageView);

        holder.eventName.setText(book.getBookName());


        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                Gson gson = new Gson();
                String eventData = gson.toJson(book);
                intent.putExtra("eventData", eventData);
                context.startActivity(intent);
            }
        });

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true).setTitle("Alert").setMessage("Are you sure you want to delete this event?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Call<Void> call = userClient.deleteEvent(book.getId());
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    onRemove(delete);
                                    Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Response Failure", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(context, "Try again ", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });


    }


    @Override
    public int getItemCount() {
        return books.size();
    }

    void onRemove(int position) {
        books.remove(position);
        notifyDataSetChanged();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;
        TextView eventName;
        Button buttonDelete;
        View v;

        public ContactViewHolder(View itemView) {
            super(itemView);

            circleImageView = itemView.findViewById(R.id.circleImageView);
            eventName = itemView.findViewById(R.id.eventName);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            v = itemView.findViewById(R.id.cardView);


        }
    }
}
