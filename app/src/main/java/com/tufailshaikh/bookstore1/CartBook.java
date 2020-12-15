package com.tufailshaikh.bookstore1;

public class CartBook {
    private String title,author,description, Uid;
    private int price;
    public int quantity;
    String bookCover;

    public CartBook(String title, String author, String description, int price, String bookCover, String Uid, int quantity) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
        this.bookCover = bookCover;
        this.Uid = Uid;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBookCover() {
        return bookCover;
    }

    public void setBookCover(String bookCover) {
        this.bookCover = bookCover;
    }

    public String getUid()
    {
        return Uid;
    }

    public void setUid(String uid)
    {
        Uid = uid;
    }
}
