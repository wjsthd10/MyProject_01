package kr.co.song1126.myproject_01;

public class CommentItems {

    String recommend_num;
    String comment_id;
    String comment_date;
    String comment_msg;
    String favor;


    public CommentItems() {
    }

    public CommentItems(String recommend_num, String comment_id, String comment_date, String comment_msg, String favor) {
        this.recommend_num = recommend_num;
        this.comment_id = comment_id;
        this.comment_date = comment_date;
        this.comment_msg = comment_msg;
        this.favor = favor;
    }
}
