package kr.co.song1126.myproject_01;

public class EventViewItems {

    String eventUrl;        //이벤트주소
    String eventImgUrl;     //이벤트이미지 주소
    String eventSite;       //이벤트 플랫폼
    String eventDT;         //이벤트 날짜

    public EventViewItems() {
    }

    public EventViewItems(String eventUrl, String eventImgUrl, String eventSite, String eventDT) {
        this.eventUrl = eventUrl;
        this.eventImgUrl = eventImgUrl;
        this.eventSite = eventSite;
        this.eventDT = eventDT;
    }
}

// eventUrl	eventImgUrl	eventSite	eventDT