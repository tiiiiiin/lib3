package com.example.lib3;

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

public class CustomAdapterTracking extends RecyclerView.Adapter<CustomAdapterTracking.MyViewHolder> {
    private Context context;
    Activity activity;
    private ArrayList<String> bookIds, bookTitles, readerNames, bookCounts, borrowDates, returnDates;

    CustomAdapterTracking(Activity activity, Context context, ArrayList<String> bookIds,
                          ArrayList<String> bookTitles, ArrayList<String> readerNames,
                          ArrayList<String> bookCounts, ArrayList<String> borrowDates,
                          ArrayList<String> returnDates) {
        this.activity = activity;
        this.context = context;
        this.bookIds = bookIds;
        this.bookTitles = bookTitles;
        this.readerNames = readerNames;
        this.bookCounts = bookCounts;
        this.borrowDates = borrowDates;
        this.returnDates = returnDates;
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
        holder.bookIdTxt.setText(bookIds.get(position));
        holder.bookTitleTxt.setText(bookTitles.get(position));
        holder.readerNameTxt.setText(readerNames.get(position));
        holder.bookCountTxt.setText(bookCounts.get(position));
        holder.borrowDateTxt.setText(borrowDates.get(position));
        holder.returnDateTxt.setText(returnDates.get(position));

        holder.mainLayoutIssuedBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    // Передаем данные в активити для редактирования
                    Intent intent = new Intent(context, UpdateBookActivity.class);
                    intent.putExtra("id", bookIds.get(clickedPosition));
                    intent.putExtra("title", bookTitles.get(clickedPosition));
                    intent.putExtra("reader", readerNames.get(clickedPosition));
                    intent.putExtra("count", bookCounts.get(clickedPosition));
                    intent.putExtra("borrowDate", borrowDates.get(clickedPosition));
                    intent.putExtra("returnDate", returnDates.get(clickedPosition));
                    activity.startActivityForResult(intent, 1);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookIds.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView bookIdTxt, bookTitleTxt, readerNameTxt, bookCountTxt, borrowDateTxt, returnDateTxt;
        LinearLayout mainLayoutIssuedBooks;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bookIdTxt = itemView.findViewById(R.id.book_id_txt);
            bookTitleTxt = itemView.findViewById(R.id.book_title_txt);
            readerNameTxt = itemView.findViewById(R.id.reader_name_txt);
            bookCountTxt = itemView.findViewById(R.id.book_count_txt);
            borrowDateTxt = itemView.findViewById(R.id.borrow_date_txt);
            returnDateTxt = itemView.findViewById(R.id.return_date_txt);
            mainLayoutIssuedBooks = itemView.findViewById(R.id.mainLayoutIssuedBooks);
        }
    }

    public void updateData(ArrayList<String> bookIds, ArrayList<String> bookTitles, ArrayList<String> readerNames,
                           ArrayList<String> bookCounts, ArrayList<String> borrowDates, ArrayList<String> returnDates) {
        this.bookIds.clear();
        this.bookTitles.clear();
        this.readerNames.clear();
        this.bookCounts.clear();
        this.borrowDates.clear();
        this.returnDates.clear();

        this.bookIds.addAll(bookIds);
        this.bookTitles.addAll(bookTitles);
        this.readerNames.addAll(readerNames);
        this.bookCounts.addAll(bookCounts);
        this.borrowDates.addAll(borrowDates);
        this.returnDates.addAll(returnDates);

        notifyDataSetChanged();
    }
}

