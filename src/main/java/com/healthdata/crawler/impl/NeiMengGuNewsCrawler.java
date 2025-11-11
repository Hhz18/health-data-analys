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

public class NeiMengGuNewsCrawler implements NewsCrawler {
    private final String baseUrl = "https://wjw.nmg.gov.cn";
    private final String pageUrl = baseUrl + "/xwzx/";

    // 网页中的日期格式是"/yyyy-MM-dd"
    private final DateTimeFormatter webpageDateFormatter = DateTimeFormatter.ofPattern("/yyyy-MM-dd");

    @Override
    public List<NewsItem> fetchNewsByDate(LocalDate date) {
        List<NewsItem> result = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(pageUrl)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();

            // 更精确的选择器
            Elements newsItems = doc.select("div#c_newstab_1 ul li");

            // 格式化目标日期以匹配网页格式
            String targetDateStr = webpageDateFormatter.format(date);

            for (Element item : newsItems) {
                Element link = item.selectFirst("a.p15.hui4");
                Element dateSpan = item.selectFirst("span.p14.hui8");

                if (link != null && dateSpan != null) {
                    String title = link.text().trim();
                    String dateStr = dateSpan.text().trim();
                    String href = link.attr("href");

                    // 确保URL是绝对路径
                    if (!href.startsWith("http")) {
                        href = baseUrl + (href.startsWith("/") ? href : "/" + href);
                    }

                    if (dateStr.equals(targetDateStr)) {
                        result.add(new NewsItem(title, href, date));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("❌ 内蒙古自治区爬虫失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public String getProvinceName() {
        return "内蒙古自治区";
    }
}