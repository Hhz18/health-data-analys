package com.healthdata.crawler.impl;


import com.healthdata.crawler.core.NewsCrawler;
import com.healthdata.crawler.core.NewsItem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ShanDongNewsCrawler implements NewsCrawler {

    private final String baseUrl = "http://wsjkw.shandong.gov.cn";
    private final String pageUrl = baseUrl + "/ywdt/wndt/";

    // 网页中的日期格式是"yyyy-MM-dd"（没有斜杠）
    private final DateTimeFormatter webpageDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public List<NewsItem> fetchNewsByDate(LocalDate date) {
        List<NewsItem> result = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(pageUrl)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();

            // 修正选择器 - 直接选择ul.news-list下的所有span.newstxt
            Elements newsItems = doc.select("ul.news-list span.newstxt");

            String targetDateStr = webpageDateFormatter.format(date);

            for (Element newsItem : newsItems) {
                Element link = newsItem.selectFirst("a");
                if (link != null) {
                    String title = link.text().replace("·", "").trim();
                    String href = link.attr("href");

                    // 获取对应的日期元素 - 下一个兄弟元素
                    Element dateSpan = newsItem.nextElementSibling();
                    if (dateSpan == null || !dateSpan.hasClass("date")) {
                        continue;
                    }
                    String dateStr = dateSpan.text().trim();

                    // 构建完整URL
                    if (!href.startsWith("http")) {
                        href = baseUrl + "/ywdt/wndt/" + (href.startsWith("/") ? href.substring(1) : href);
                    }


                    if (dateStr.equals(targetDateStr)) {
                        result.add(new NewsItem(title, href, date));
                    }else {
                        result.add(new NewsItem(title, href, date));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("❌ 山东省爬虫失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public String getProvinceName() {
        return "山东省";
    }
}