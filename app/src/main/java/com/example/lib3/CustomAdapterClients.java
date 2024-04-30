package com.example.lib3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterClients extends RecyclerView.Adapter<CustomAdapterClients.MyViewHolder> {
    private Context context;
    Activity activity;
    private ArrayList client_id, name_client, date_of_birth, client_email;
    CustomAdapterClients(Activity activity, Context context,
                         ArrayList client_id,
                         ArrayList name_client,
                         ArrayList date_of_birth,
                         ArrayList client_email){
        this.activity = activity;
        this.context = context;
        this.client_id = client_id;
        this.name_client = name_client;
        this.date_of_birth = date_of_birth;
        this.client_email = client_email;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_clients, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.client_id_txt.setText(String.valueOf(client_id.get(position)));
        holder.name_client_txt.setText(String.valueOf(name_client.get(position)));
        holder.date_of_birth_txt.setText(String.valueOf(date_of_birth.get(position)));
        holder.email_client_txt.setText(String.valueOf(client_email.get(position)));

        holder.mainLayoutClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getAdapterPosition();
                if(clickedPosition != RecyclerView.NO_POSITION){
                    Intent intent = new Intent(context, UpdateClientActivity.class);
                    intent.putExtra("id",String.valueOf(client_id.get(clickedPosition)));
                    intent.putExtra("name",String.valueOf(name_client.get(clickedPosition)));
                    intent.putExtra("data",String.valueOf(date_of_birth.get(clickedPosition)));
                    intent.putExtra("email",String.valueOf(client_email.get(clickedPosition)));
                    activity.startActivityForResult(intent, 1);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return client_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView client_id_txt, name_client_txt, date_of_birth_txt, email_client_txt;
        LinearLayout mainLayoutClient;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            client_id_txt = itemView.findViewById(R.id.client_id_txt);
            name_client_txt = itemView.findViewById(R.id.name_client_txt);
            date_of_birth_txt = itemView.findViewById(R.id.date_of_birth_txt);
            email_client_txt = itemView.findViewById(R.id.email_client_txt);
            mainLayoutClient = itemView.findViewById(R.id.mainLayoutClient);
        }
    }
    public void updateData(ArrayList<String> ids, ArrayList<String> names, ArrayList<String> dates, ArrayList<String> emails) {
        this.client_id.clear();
        this.name_client.clear();
        this.date_of_birth.clear();
        this.client_email.clear();

        this.client_id.addAll(ids);
        this.name_client.addAll(names);
        this.date_of_birth.addAll(dates);
        this.client_email.addAll(emails);

        notifyDataSetChanged();
    }
}
