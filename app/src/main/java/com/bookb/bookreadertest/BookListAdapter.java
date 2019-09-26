package com.bookb.bookreadertest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class BookListAdapter extends ArrayAdapter {
    private Context aContext;
    private int layout;
    private List<BookItem> bList;

    public BookListAdapter(@NonNull Context context, int resource, List<BookItem>list) {
        super(context, resource, list);
        aContext = context;
        layout = resource;
        bList = list;
    }

    public BookListAdapter(MainActivity context, int list_item, List<Integer> bookList) {
        super(context, list_item, bookList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listTask = convertView;
        if (convertView == null){
            listTask = LayoutInflater.from(aContext).inflate(this.layout, parent, false);
            BookItem bItem = bList.get(position);
            TextView nameView = (TextView) listTask.findViewById(R.id.name_book);
            ImageView picView = (ImageView) listTask.findViewById(R.id.flag);
            picView.setImageResource(bItem.getBookPicture());
            nameView.setText(bItem.getBookName());
        }
        return listTask;
    }
}
