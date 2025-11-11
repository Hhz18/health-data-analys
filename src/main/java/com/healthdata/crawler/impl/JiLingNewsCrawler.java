package com.healthdata.crawler.impl;


import com.healthdata.crawler.core.NewsCrawler;
import com.healthdata.crawler.core.NewsItem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JiLingNewsCrawler implements NewsCrawler {
    private final String pageUrl ="http://wsjkw.jl.gov.cn/xwzx/gzdt/wzdw/";

    @Override
    public List<NewsItem> fetchNewsByDate(LocalDate date) {
        List<NewsItem> result =new ArrayList<>();
        try{
            Document doc = Jsoup.connect(pageUrl)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();
            Elements blocks = doc.select("div.xx.e_zfxxgk > div.xxl.xl"); // 每条新闻块
            String  targetStr =date.toString();

            for (Element block:blocks){
                String title = block.selectFirst("a").text().trim();
                String href = block.selectFirst("a").absUrl("href");
                String dateStr = block.selectFirst("span").text().trim();

                if (dateStr.equals(targetStr)){
                    result.add(new NewsItem(title,href,date));
                }
            }
        } catch (IOException e) {
            System.err.println("❌ 吉林省爬虫失败：" + e.getMessage());
        }
        return result;
    }
    @Override
    public String getProvinceName() {
        return "吉林省";
    }
}
