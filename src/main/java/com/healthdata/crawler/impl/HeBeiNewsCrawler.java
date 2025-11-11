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

public class HeBeiNewsCrawler implements NewsCrawler {
    private final String pageUrl ="http://wsjkw.hebei.gov.cn/zwyw/index.jhtml";

    @Override
    public List<NewsItem> fetchNewsByDate(LocalDate date) {
        List<NewsItem> result =new ArrayList<>();
        try{
            Document doc = Jsoup.connect(pageUrl)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();
            Elements blocks = doc.select("ul.er-list >li"); //直接选择ul下所有 li（推荐）

            String  targetStr =date.toString();

            for (Element block:blocks){
                String title = block.select("a").text().trim();
                String dateStr = block.select("span").text().trim();
                String href = block.select("a").attr("abs:href");

                if (dateStr.equals(targetStr)){
                    result.add(new NewsItem(title,href,date));
                }
            }
        } catch (IOException e) {
            System.err.println("❌ 河北省爬虫失败：" + e.getMessage());
        }
        return result;
    }
    @Override
    public String getProvinceName() {
        return "河北省";
    }
}
