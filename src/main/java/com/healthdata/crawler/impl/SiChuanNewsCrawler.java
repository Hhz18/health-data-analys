package com.healthdata.crawler.impl;


import com.healthdata.crawler.core.NewsCrawler;
import com.healthdata.crawler.core.NewsItem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SiChuanNewsCrawler implements NewsCrawler {
    private final String pageUrl ="https://wsjkw.sc.gov.cn/scwsjkw/gzdt/tygl.shtml";

    @Override
    public List<NewsItem> fetchNewsByDate(LocalDate date) {
    //        List<NewsItem> result =new ArrayList<>();
    //
    //        try{
    //            Document doc = Jsoup.connect(pageUrl)
    //                    .userAgent("Mozilla/5.0")
    //                    .timeout(10000)
    //                    .get();
    //            Elements blocks = doc.select("div.wy_contMain fontSt >ul >li");
    //            String  targetStr =date.toString();
    //            for (Element block:blocks){
    //                String title = block.select("a").text().trim();
    //                String dateStr = block.select("span").text().trim();
    //                String href = block.select("a").attr("abs:href");
    //
    //                if (dateStr.equals(targetStr)){
    //                    result.add(new NewsItem(title,href,date));
    //                }
    //            }
    //        } catch (IOException e) {
    //            System.err.println("❌ 四川爬虫失败：" + e.getMessage());
    //        }
        List<NewsItem> result = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(pageUrl)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();

            Elements blocks = doc.select("div.wy_contMain.fontSt > ul > li");
            String targetStr = date.toString(); // 2025-07-04

            for (Element block : blocks) {
                String dateStr = block.select("span").text().trim();
                Element aTag = block.selectFirst("a");

                if (aTag != null && dateStr.equals(targetStr)) {
                    String title = aTag.text().trim();
                    String href = aTag.absUrl("href");

                    result.add(new NewsItem(title, href, date));
                }
            }

        } catch (Exception e) {
            System.err.println("❌ 四川省爬虫失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public String getProvinceName() {
        return "四川省";
    }
}
