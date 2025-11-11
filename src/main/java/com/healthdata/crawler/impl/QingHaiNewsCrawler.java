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

public class QingHaiNewsCrawler implements NewsCrawler {
    private final String pageUrl ="https://wsjkw.qinghai.gov.cn/zwgk/fdzdgknr/xwfb/";


    @Override
    public List<NewsItem> fetchNewsByDate(LocalDate date) {
        List<NewsItem> result =new ArrayList<>();
        try{
            Document doc = Jsoup.connect(pageUrl)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();

            Elements blocks =doc.select("div.zfxxgk_zdgkc > ul >li");
            //或者一步到位  Elements blocks = doc.select("div.zfxxgk_zdgkc li");

            String targetStr =date.toString();
            for (Element block:blocks){
                String title = block.select("a").text().trim();
                String dateStr = block.select("b").text().trim();
                String href = block.select("a").attr("abs:href");

                if (dateStr.equals(targetStr)){
                    result.add(new NewsItem(title,href,date));
                }
            }
        }catch (Exception e){
            System.err.println("❌ 青海省爬虫失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public String getProvinceName() {
        return "青海省";
    }
}
