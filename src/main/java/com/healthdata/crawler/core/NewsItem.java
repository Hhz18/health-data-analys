package com.healthdata.crawler.core;

import java.time.LocalDate;

public class NewsItem {
    public String title;
    public String url;
    public LocalDate date;
    private String province;
    public NewsItem(String title, String url, LocalDate date){
        this.title =title;
        this.url =url;
        this.date =date;
    }


    public NewsItem() {
        // ✅ Jackson 反序列化必须要有无参构造函数！
    }

    public NewsItem(String title, String url, LocalDate date, String province) {
        this.title = title;
        this.url = url;
        this.date = date;
        this.province = province;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public LocalDate getDate() {
        return date;
    }
    @Override
    public String toString() {
        return "- " + title + "（" + url + "）";
    }
}
