package kr.co.song1126.myproject_01;

public class MyBookItems {

    String title;
    String imgUrl;
    String kategorie;
    String bookName;
    String date;
    String views;
    String line1, line2, line3;
    String views01;
    String delete;

    public MyBookItems(String title, String imgUrl, String kategorie, String bookName, String date, String views, String line1, String line2, String line3, String views01, String delete) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.kategorie = kategorie;
        this.bookName = bookName;
        this.date = date;
        this.views = views;
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.views01 = views01;
        this.delete = delete;
    }

    public MyBookItems(String title, String imgUrl, String kategorie, String bookName, String date, String views) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.kategorie = kategorie;
        this.bookName = bookName;
        this.date = date;
        this.views = views;
    }

    public MyBookItems() {
    }
}
