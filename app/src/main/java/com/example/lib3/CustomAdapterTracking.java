package com.example.lib3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapterTracking extends RecyclerView.Adapter<CustomAdapterTracking.MyViewHolder> {
    Context context;
    ArrayList<String> book_id, book_title, reader_name, borrow_date, return_date;
    OnItemClickListener listener;

    CustomAdapterTracking(Context context,
                          ArrayList<String> book_id, ArrayList<String> book_title, ArrayList<String> reader_name,
                          ArrayList<String> borrow_date, ArrayList<String> return_date){
        this.context = context;
        this.book_id = book_id;
        this.book_title = book_title;
        this.reader_name = reader_name;
        this.borrow_date = borrow_date;
        this.return_date = return_date;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_tracking, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.book_id_txt.setText(String.valueOf(book_id.get(position)));
        holder.book_title_txt.setText(String.valueOf(book_title.get(position)));
        holder.reader_name_txt.setText(String.valueOf(reader_name.get(position)));
        holder.borrow_date_txt.setText(String.valueOf(borrow_date.get(position)));
        holder.return_date_txt.setText(String.valueOf(return_date.get(position)));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return book_id.size(); // Возвращаем размер списка book_id
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView book_id_txt, book_title_txt, reader_name_txt, book_count_txt, borrow_date_txt, return_date_txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            book_id_txt = itemView.findViewById(R.id.book_id_txt);
            book_title_txt = itemView.findViewById(R.id.book_title_txt);
            reader_name_txt = itemView.findViewById(R.id.reader_name_txt);
            //book_count_txt = itemView.findViewById(R.id.book_count_txt);
            borrow_date_txt = itemView.findViewById(R.id.borrow_date_txt);
            return_date_txt = itemView.findViewById(R.id.return_date_txt);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Метод для установки слушателя кликов
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public void updateData(ArrayList<String> ids, ArrayList<String> book, ArrayList<String> reader, ArrayList<String> borrow, ArrayList<String> returnd) {
        this.book_id.clear();
        this.book_title.clear();
        this.reader_name.clear();
        this.borrow_date.clear();
        this.return_date.clear();

        this.book_id.addAll(ids);
        this.book_title.addAll(book);
        this.reader_name.addAll(reader);
        this.borrow_date.addAll(borrow);
        this.return_date.addAll(returnd);

        notifyDataSetChanged();
    }
}
