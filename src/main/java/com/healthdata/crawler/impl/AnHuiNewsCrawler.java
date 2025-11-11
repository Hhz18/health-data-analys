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

public class AnHuiNewsCrawler implements NewsCrawler {

    private final String baseUrl = "https://wjw.ah.gov.cn";
    private final String pageUrl = baseUrl + "/index.html";

    // 网页中的日期格式是"MM-dd"（没有年份）
    private final DateTimeFormatter webpageDateFormatter = DateTimeFormatter.ofPattern("MM-dd");
    // 用于匹配当前年份
    private final DateTimeFormatter currentYearFormatter = DateTimeFormatter.ofPattern("yyyy");

    @Override
    public List<NewsItem> fetchNewsByDate(LocalDate date) {
        List<NewsItem> result = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(pageUrl)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();

            // 选择所有tab下的新闻条目（或者指定特定tab，如atab1）
            Elements newsItems = doc.select("div.ls-newslist ul.con li");

            // 获取当前年份（因为网页日期只有月日）
            String currentYear = currentYearFormatter.format(LocalDate.now());
            // 目标日期格式化为网页中的格式（MM-dd）
            String targetDateStr = webpageDateFormatter.format(date);
            // 完整的日期字符串（用于比较）
            String fullTargetDateStr = currentYear + "-" + targetDateStr;

            for (Element item : newsItems) {
                Element link = item.selectFirst("a.left");
                Element dateSpan = item.selectFirst("span.right.date");

                if (link != null && dateSpan != null) {
                    String title = link.attr("title"); // 使用title属性获取完整标题
                    if (title == null || title.isEmpty()) {
                        title = link.text().trim(); // 如果title属性为空，使用文本内容
                    }
                    String dateStr = dateSpan.text().trim();
                    String href = link.attr("href");

                    // 构建完整URL
                    if (!href.startsWith("http")) {
                        href = href.startsWith("/") ? baseUrl + href : baseUrl + "/" + href;
                    }

                    // 比较日期（只比较月日部分）
                    if (dateStr.equals(targetDateStr)) {
                        // 使用完整日期创建NewsItem
                        LocalDate newsDate = LocalDate.parse(fullTargetDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        result.add(new NewsItem(title, href, newsDate));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("❌ 安徽省爬虫失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public String getProvinceName() {
        return "安徽省";
    }
}