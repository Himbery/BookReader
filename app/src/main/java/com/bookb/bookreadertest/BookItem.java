package com.bookb.bookreadertest;

import android.os.Parcel;
import android.os.Parcelable;

public class BookItem implements Parcelable {
    public String bookName;
    public int bookPicture;
    public String bookFileName;
    public String filePath;

    public BookItem(String bookName, int bookPicture, String bookFileName){
        this.bookName = bookName;
        this.bookPicture = bookPicture;
        this.bookFileName = bookFileName;
    }

    protected BookItem(Parcel in) {
        bookName = in.readString();
        bookPicture = in.readInt();
        bookFileName = in.readString();
        filePath = in.readString();
    }

    public static final Creator<BookItem> CREATOR = new Creator<BookItem>() {
        @Override
        public BookItem createFromParcel(Parcel in) {
            return new BookItem(in);
        }

        @Override
        public BookItem[] newArray(int size) {
            return new BookItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bookName);
        dest.writeInt(bookPicture);
        dest.writeString(bookFileName);
        dest.writeString(filePath);
    }

    public int getBookPicture() {
        return bookPicture;
    }
    public String getBookName() {
        return bookName;
    }
    public String getBookFileName() { return bookFileName; }
    public String getFilePath() {
        return filePath;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public void setBookPicture(int bookPicture) {
        this.bookPicture = bookPicture;
    }
    public void setBookFileName(String bookFileName) { this.bookFileName = bookFileName; }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
