package kr.co.song1126.myproject_01;

public class CommentItems {

    String comment_id;
    String comment_date;
    String comment_msg;
    String comment_reportTV;
    String comment_reportIV;
    String comment_notgood;
    String comment_notgood_tv;
    String comment_good;
    String comment_good_tv;
    String comment_line;

    public CommentItems() {
    }

    public CommentItems(String comment_id, String comment_date, String comment_msg, String comment_reportTV, String comment_reportIV, String comment_notgood, String comment_notgood_tv, String comment_good, String comment_good_tv, String comment_line) {
        this.comment_id = comment_id;
        this.comment_date = comment_date;
        this.comment_msg = comment_msg;
        this.comment_reportTV = comment_reportTV;
        this.comment_reportIV = comment_reportIV;
        this.comment_notgood = comment_notgood;
        this.comment_notgood_tv = comment_notgood_tv;
        this.comment_good = comment_good;
        this.comment_good_tv = comment_good_tv;
        this.comment_line = comment_line;
    }
}
