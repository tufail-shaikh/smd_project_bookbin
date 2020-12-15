package com.tufailshaikh.bookstore1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class wishListHelper extends SQLiteOpenHelper {

    String CRETE_TABLE="CREATE TABLE "+
            MyWishListContract.WishList.TABLE_NAME+ "("+
            MyWishListContract.WishList._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            MyWishListContract.WishList.userEmail+ " TEXT , "+
            MyWishListContract.WishList.BookTitle+ " TEXT , "+
            MyWishListContract.WishList.BookAuthor+ " TEXT , "+
            MyWishListContract.WishList.BookDescription+ " TEXT , "+
            MyWishListContract.WishList.BookPrice+ " TEXT , "+
            MyWishListContract.WishList.BookUri+ " TEXT  );";

    String DELETE_TABLE=" DROP TABLE IF EXISTS "+ MyWishListContract.WishList.TABLE_NAME;
    public wishListHelper(@Nullable Context context) {
        super(context, MyWishListContract.DB_NAME, null, MyWishListContract.DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CRETE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE);
        onCreate(db);
    }
}
