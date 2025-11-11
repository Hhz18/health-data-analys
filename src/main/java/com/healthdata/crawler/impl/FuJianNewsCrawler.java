package com.healthdata.crawler.impl;


import com.healthdata.crawler.core.NewsCrawler;
import com.healthdata.crawler.core.NewsItem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FuJianNewsCrawler implements NewsCrawler {
    private final String pageUrl = "https://wjw.fujian.gov.cn/xxgk/fgwj/";

    @Override
    public List<NewsItem> fetchNewsByDate(LocalDate date) {
        List<NewsItem> result = new ArrayList<>();
        WebDriver driver = null;

        try {
            // 使用Selenium获取动态加载的内容
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless"); // 无头模式
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");

            driver = new ChromeDriver(options);
            driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
            driver.get(pageUrl);

            // 等待页面加载完成
            Thread.sleep(3000);

            // 获取完整页面内容
            Document doc = Jsoup.parse(driver.getPageSource());

            // 调试：保存页面内容到文件
            // Files.write(Paths.get("fujian_page.html"), doc.html().getBytes());

            // 尝试多种选择器组合
            Elements blocks = doc.select("div.list-content ul li");
            if (blocks.isEmpty()) {
                blocks = doc.select("div.news-list li");
            }
            if (blocks.isEmpty()) {
                blocks = doc.select("ul.list li");
            }
            if (blocks.isEmpty()) {
                blocks = doc.select("div.gl_list li");
            }

            System.out.println("找到 " + blocks.size() + " 个新闻条目");

            // 日期格式化
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String targetStr = date.format(formatter);

            for (Element block : blocks) {
                try {
                    Element link = block.selectFirst("a");
                    Element dateElement = block.selectFirst("span.date");

                    if (dateElement == null) {
                        dateElement = block.selectFirst("em");
                    }
                    if (dateElement == null) {
                        dateElement = block.selectFirst("span");
                    }

                    if (link != null && dateElement != null) {
                        String title = link.text().trim();
                        String dateStr = dateElement.text().trim()
                                .replace("年", "-")
                                .replace("月", "-")
                                .replace("日", "")
                                .replace("/", "-");
                        String href = link.absUrl("href");

                        System.out.println("标题: " + title);
                        System.out.println("日期: " + dateStr);
                        System.out.println("链接: " + href);

                        if (dateStr.contains(targetStr)) {
                            result.add(new NewsItem(title, href, date));
                        }
                    }
                } catch (Exception e) {
                    System.err.println("解析条目时出错: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("❌ 福建省卫健委爬虫失败：" + e.getMessage());
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }

        return result;
    }

    @Override
    public String getProvinceName() {
        return "福建省卫健委";
    }
}