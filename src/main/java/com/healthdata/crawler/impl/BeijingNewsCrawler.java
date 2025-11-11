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


public class BeijingNewsCrawler implements NewsCrawler {
    private final String baseUrl ="http://wjw.beijing.gov.cn/xwzx_20031/xwfb/";


    @Override
    public List<NewsItem> fetchNewsByDate(LocalDate date) {
       List<NewsItem> result =new ArrayList<>();
       try{
           Document doc = Jsoup.connect(baseUrl)
                   .userAgent("Mozilla/5.0")
                   .timeout(10000)
                   .get();
           Elements blocks =doc.select("div.weinei_left_con_line");
           String targetStr =date.toString();

           for (Element block : blocks) {
               String title = block.select("span.pc-hide").text().trim();
               String dateStr = block.select("div.weinei_left_con_line_date").text().trim();

               if (!dateStr.equals(targetStr)) continue;

               String href = block.select("a").attr("href");
               String fullUrl = href.startsWith("http") ? href : baseUrl + href.replaceFirst("^\\./", "");

               result.add(new NewsItem(title, fullUrl, date));
           }
       }catch (Exception e) {
           System.err.println("❌ 北京爬虫失败：" + e.getMessage());
       }
       return result;
    }

    @Override
    public String getProvinceName() {
        return "北京市";
    }
}
