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

public class ShanghaiNewsCrawler implements NewsCrawler {
    //上海卫健委链接
    private final String pageUrl ="http://wsjkw.sh.gov.cn/xwfb/index.html";

    @Override
    public List<NewsItem> fetchNewsByDate(LocalDate date) {
        List<NewsItem> result =new ArrayList<>();
        try{
            Document doc = Jsoup.connect(pageUrl)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();
//            Elements blocks =doc.select("ul.uli16 nowrapli list-date trout-region-list"); //适配每条新闻的选择器
            Elements blocks = doc.select("ul.uli16.nowrapli.list-date.trout-region-list > li"); //直接选择ul下所有 li（推荐）

            String  targetStr =date.toString();

            for (Element block:blocks){
//                String title = block.select("标题选择器").text().trim();
//                String dateStr = block.select("日期选择器").text().trim();
//                String href = block.select("a").attr("abs:href");
                String title = block.select("a").text().trim();
                String dateStr = block.select("span.time").text().trim();
                String href = block.select("a").attr("abs:href");

                if (dateStr.equals(targetStr)){
                    result.add(new NewsItem(title,href,date));
                }
            }
        } catch (IOException e) {
            System.err.println("❌ 上海爬虫失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public String getProvinceName() {
        return "上海市";
    }
}
