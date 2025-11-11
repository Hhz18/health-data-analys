package com.healthdata.crawler.core;

import java.time.LocalDate;
import java.util.List;

public interface NewsCrawler {
    List<NewsItem>fetchNewsByDate(LocalDate date);
    String getProvinceName();
}
