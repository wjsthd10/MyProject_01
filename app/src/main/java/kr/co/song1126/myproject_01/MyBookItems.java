package kr.co.song1126.myproject_01;

public class MyBookItems {

    String title;
    String imgUrl;
    String kategorie;
    String bookName;
    String date;
    String views;

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
