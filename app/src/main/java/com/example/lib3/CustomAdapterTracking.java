package com.example.lib3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterTracking extends RecyclerView.Adapter<CustomAdapterTracking.MyViewHolder> {
    private Context context;
    private Activity activity;
    private ArrayList<String> bookIds, bookTitles, readerNames, bookCounts, borrowDates, returnDates;

    // Интерфейс для обработки кликов
    public interface OnItemClickListener {
        void onItemClick(String bookId, String bookTitle, String readerName, String bookCount, String borrowDate, String returnDate);
    }

    private OnItemClickListener listener;

    public CustomAdapterTracking(Activity activity, Context context, ArrayList<String> bookIds,
                                 ArrayList<String> bookTitles, ArrayList<String> readerNames,
                                 ArrayList<String> bookCounts, ArrayList<String> borrowDates,
                                 ArrayList<String> returnDates, OnItemClickListener listener) {
        this.activity = activity;
        this.context = context;
        this.bookIds = bookIds;
        this.bookTitles = bookTitles;
        this.readerNames = readerNames;
        this.bookCounts = bookCounts;
        this.borrowDates = borrowDates;
        this.returnDates = returnDates;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row_tracking, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return bookIds.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
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

            // Добавляем обработчик клика
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int clickedPosition = getAdapterPosition();
                    if (clickedPosition != RecyclerView.NO_POSITION && listener != null) {
                        // Получаем данные о выбранной книге
                        String bookId = bookIds.get(clickedPosition);
                        String bookTitle = bookTitles.get(clickedPosition);
                        String readerName = readerNames.get(clickedPosition);
                        String bookCount = bookCounts.get(clickedPosition);
                        String borrowDate = borrowDates.get(clickedPosition);
                        String returnDate = returnDates.get(clickedPosition);

                        // Вызываем метод обработки клика из интерфейса
                        listener.onItemClick(bookId, bookTitle, readerName, bookCount, borrowDate, returnDate);
                    }
                }
            });
        }

        void bind(int position) {
            bookIdTxt.setText(bookIds.get(position));
            bookTitleTxt.setText(bookTitles.get(position));
            readerNameTxt.setText(readerNames.get(position));
            bookCountTxt.setText(bookCounts.get(position));
            borrowDateTxt.setText(borrowDates.get(position));
            returnDateTxt.setText(returnDates.get(position));
        }
    }

    public void updateData(ArrayList<String> bookIds, ArrayList<String> bookTitles,
                           ArrayList<String> readerNames, ArrayList<String> bookCounts,
                           ArrayList<String> borrowDates, ArrayList<String> returnDates) {
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

    public void removeItem(int position) {
        bookIds.remove(position);
        bookTitles.remove(position);
        readerNames.remove(position);
        bookCounts.remove(position);
        borrowDates.remove(position);
        returnDates.remove(position);
        notifyItemRemoved(position);
    }
}

