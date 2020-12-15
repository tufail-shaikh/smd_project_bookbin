package com.tufailshaikh.bookstore1;

import android.provider.BaseColumns;

public class MyWishListContract {
    public static String DB_NAME="SqliteWishList.db";
    public static int DB_VERSION=1;

    public static class WishList implements BaseColumns {

        public static String TABLE_NAME="wishList";
        public static String userEmail="userEmail";
        public static String BookTitle="title";
        public static String BookAuthor="author";
        public static String BookDescription="description";
        public static String BookPrice="price";
        public static String BookUri="uri";
        //public static String Uid="uid";

    }

}
