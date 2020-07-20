package kr.co.song1126.myproject_01;

public class RecyclerViewitems {

    String num;
    String title;
    String imgUrl;
    String kategorie;
    String bookName;
    String date;
    String views;



    public RecyclerViewitems() {
    }

    public RecyclerViewitems(String num, String title, String imgUrl, String kategorie, String bookName, String date, String views) {
        this.num = num;
        this.title = title;
        this.imgUrl = imgUrl;
        this.kategorie = kategorie;
        this.bookName = bookName;
        this.date = date;
        this.views = views;
    }
}
