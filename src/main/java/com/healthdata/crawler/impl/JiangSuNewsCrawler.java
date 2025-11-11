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

public class JiangSuNewsCrawler implements NewsCrawler {
    private final String pageUrl ="https://wjw.jiangsu.gov.cn/col/col7290/index.html";

    @Override
    public List<NewsItem> fetchNewsByDate(LocalDate date) {
        List<NewsItem> result =new ArrayList<>();
        try{
            Document doc = Jsoup.connect(pageUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .header("Accept-Language", "zh-CN,zh;q=0.9")
                    .header("Referer", "https://wjw.jiangsu.gov.cn/")
                    .header("Connection", "keep-alive")
                    .ignoreHttpErrors(true)  // 避免403阻塞程序运行
                    .timeout(10000)
                    .get();

            // 正确选择器
            Elements blocks = doc.select("div#317895 div.default_pgContainer li");

//            Elements blocks = doc.select("ul.main-fr-box >div#317895 >div.default_pgContainer >li");
//            Elements blocks = doc.select("div#317895 >div.default_pgContainer >li"); //直接选择ul下所有 li（推荐）

            String  targetStr =date.toString();

            for (Element block:blocks){
                String title = block.select("a").text().trim();
                String dateStr = block.select("span.bt-right").text().trim();
                String href = block.select("a").attr("abs:href");

                System.out.println("标题："+title);
                System.out.println("发布时间："+dateStr);
                System.out.println("链接："+href);
                System.out.println("页面标题：" + doc.title());
                System.out.println("页面内容预览：" + doc.outerHtml().substring(0, 500));
                System.out.println("共找到条目数：" + blocks.size());

                if (dateStr.equals(targetStr)){
                    result.add(new NewsItem(title,href,date));
                }
            }
        } catch (IOException e) {
            System.err.println("❌ 江苏省爬虫失败：" + e.getMessage());
        }
        return result;
    }
    @Override
    public String getProvinceName() {
        return "江苏省";
    }
}
