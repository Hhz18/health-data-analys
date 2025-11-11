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

//陕西
public class ShanXiNewsCrawler implements NewsCrawler {
    private final String pageUrl ="https://sxwjw.shaanxi.gov.cn/sy/wjyw/";

    @Override
    public List<NewsItem> fetchNewsByDate(LocalDate date) {
        List<NewsItem> result =new ArrayList<>();
        try{
            Document doc = Jsoup.connect(pageUrl)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();
            Elements blocks = doc.select("div.rt.w-gl-w868.f-mr20 >ul>li"); //直接选择ul下所有 li（推荐）

            String  targetStr =date.toString();

            for (Element block:blocks){
                String title = block.select("a").text().trim();
                String dateStr = block.select("span").text().trim();
                String href = block.select("a").attr("abs:href");
                LocalDate localDate = LocalDate.parse(dateStr);
//                这里获取官网发的这一页的信息
                result.add(new NewsItem(title,href,localDate));
//                System.out.println(title);
//                System.out.println(dateStr);
//                System.out.println("targetStr:"+targetStr);
//                System.out.println(href);
//                if (dateStr.equals(targetStr)){
//                    result.add(new NewsItem(title,href,date));
//                }
            }
        } catch (IOException e) {
            System.err.println("❌ 陕西省爬虫失败：" + e.getMessage());
        }
        return result;
    }
    @Override
    public String getProvinceName() {
        return "陕西省";
    }
}
