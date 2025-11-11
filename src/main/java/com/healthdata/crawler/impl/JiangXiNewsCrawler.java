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

public class JiangXiNewsCrawler implements NewsCrawler {
    private final String baseUrl = "https://hc.jiangxi.gov.cn";
    private final String pageUrl = baseUrl + "/jxswsjkwyh/jkyw/index.html";

    @Override
    public List<NewsItem> fetchNewsByDate(LocalDate date) {
        List<NewsItem> result = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(pageUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .timeout(30000)
                    .get();

            // 更精确的选择器
            Elements newsItems = doc.select("div.default_pgContainer > ul > li");

            String targetDate = date.toString();

            for (Element item : newsItems) {
                Element link = item.selectFirst("a");
                Element dateSpan = item.selectFirst("span.bt-data-time");

                if (link != null && dateSpan != null) {
                    String itemDate = dateSpan.text().trim();
                    if (targetDate.equals(itemDate)) {
                        String title = link.attr("title").trim(); // 使用title属性可能更准确
                        String href = link.attr("href");

                        // 处理相对链接
                        if (!href.startsWith("http")) {
                            href = baseUrl + href;
                        }

                        result.add(new NewsItem(title, href, date));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("❌ 江西省爬虫失败：" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String getProvinceName() {
        return "江西省";
    }
}