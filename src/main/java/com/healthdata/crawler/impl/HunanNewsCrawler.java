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

public class HunanNewsCrawler implements NewsCrawler {
//    https://wjw.hunan.gov.cn/wjw/xxgk/gzdt/zyxw_1/index.html
//    https://www.hunan.gov.cn/hnszf/hnyw/ywtj/
    private final String pageUrl = " https://wjw.hunan.gov.cn/wjw/xxgk/gzdt/zyxw_1/index.html";



    @Override
    public List<NewsItem> fetchNewsByDate(LocalDate date) {
        List<NewsItem> result =new ArrayList<>();
        try{
            Document doc = Jsoup.connect(pageUrl)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();
            Elements rows =doc.select("tbody.table_list_st tr");
            String targetStr =date.toString();

            for (Element row : rows) {
                Elements tds = row.select("td");
                if (tds.size() < 3) continue;

                String title = tds.get(1).text().trim();
                String link = tds.get(1).select("a").attr("abs:href");
                String dateStr = tds.get(2).text().trim();

                if (dateStr.equals(targetStr)) {
                    result.add(new NewsItem(title, link, date));
                }
            }
        }catch (Exception e){
            System.err.println("❌ 湖南爬虫失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public String getProvinceName() {
        return "湖南省";
    }
}
