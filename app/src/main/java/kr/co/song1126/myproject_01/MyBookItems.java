package kr.co.song1126.myproject_01;

public class MyBookItems {

    String title;
    String imgUrl;
    String kategorie;
    String userName;
    String bookName;
    String date;
    String views;
    String authorname;
    String msg;
    String favorite;

    public MyBookItems(String title, String imgUrl, String kategorie, String userName, String bookName, String date, String views, String authorname, String msg, String favorite) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.kategorie = kategorie;
        this.userName = userName;
        this.bookName = bookName;
        this.date = date;
        this.views = views;
        this.authorname = authorname;
        this.msg = msg;
        this.favorite = favorite;
    }

    public MyBookItems() {
    }
}


//CREATE TABLE `wjsthd10`.`my_recommend` ( `num` INT NOT NULL AUTO_INCREMENT , `title` TEXT NOT NULL , `imgUrl` TEXT NULL , `kategorie` TEXT NOT NULL , `bookName` TEXT NOT NULL , `date` TEXT NOT NULL , `views` TEXT NOT NULL , `userName` TEXT NOT NULL , `userEmail` TEXT NOT NULL , `authorname` INT NOT NULL , PRIMARY KEY (`num`)) ENGINE = MyISAM;