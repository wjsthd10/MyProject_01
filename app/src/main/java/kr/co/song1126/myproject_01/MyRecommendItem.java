package kr.co.song1126.myproject_01;

public class MyRecommendItem {

    String num;
    String title;
    String imgUrl;
    String kategorie;
    String bookName;
    String date;
    String views;
    String userName;

    public MyRecommendItem(String num, String title, String imgUrl, String kategorie, String bookName, String date, String views, String userName) {
        this.num = num;
        this.title = title;
        this.imgUrl = imgUrl;
        this.kategorie = kategorie;
        this.bookName = bookName;
        this.date = date;
        this.views = views;
        this.userName = userName;
    }
}
