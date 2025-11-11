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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HuBeiNewsCrawler implements NewsCrawler {
    private final String pageUrl ="https://wjw.hubei.gov.cn/bmdt/ttxw/";

    @Override
    public List<NewsItem> fetchNewsByDate(LocalDate date) {
        List<NewsItem> result =new ArrayList<>();
        try{
            Document doc = Jsoup.connect(pageUrl)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();
            Elements blocks = doc.select("ul.list-b.hover-style1#share > li");  //选择 id="zfyw" 的 <ul> 下的所有 <li> 元素。

            String  targetStr =date.toString();

            for (Element block : blocks) {
                Element aTag = block.selectFirst("a");

                // 提取标题（推荐用 h4.text()，更干净）
                String title = aTag.selectFirst("h4").text().trim();

                // 提取链接
                String href = aTag.absUrl("href");

                // 提取时间文本
                String pText = aTag.selectFirst("p").text(); // eg. 时间：2025-07-04 来源：新华社

                // 用正则提取日期
                String dateStr = "";
                Matcher matcher = Pattern.compile("时间[:：]\\s*(\\d{4}-\\d{2}-\\d{2})").matcher(pText);
                if (matcher.find()) {
                    dateStr = matcher.group(1);
                }

                // 判断是否匹配目标日期
                if (dateStr.equals(date.toString())) {
                    result.add(new NewsItem(title, href, date));
                }
            }

        } catch (IOException e) {
            System.err.println("❌ 湖北省爬虫失败：" + e.getMessage());
        }
        return result;
    }
    @Override
    public String getProvinceName() {
        return "湖北省";
    }
}
