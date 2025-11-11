package com.healthdata.crawler.impl;


import com.healthdata.crawler.core.NewsCrawler;
import com.healthdata.crawler.core.NewsItem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HeNanNewsCrawler implements NewsCrawler {
    private final String pageUrl ="https://wsjkw.henan.gov.cn/zwdt/jkyw/";

    @Override
    public List<NewsItem> fetchNewsByDate(LocalDate date) {
        List<NewsItem> result =new ArrayList<>();
//        try{
//            Document doc = Jsoup.connect(pageUrl)
//                    .userAgent("Mozilla/5.0")
//                    .ignoreHttpErrors(true)
//                    .followRedirects(true)
//                    .timeout(10000)
//                    .get();
//            Elements blocks = doc.select("ul.list-group.listmain > li");
//            String  targetStr =date.toString();
//
//            for (Element block : blocks) {
//                Element aTag = block.selectFirst("a");
//                String title = aTag.text().replace("·", "").trim();
//                String href = aTag.absUrl("href");
//
//                // 从 <li> 自身的纯文本中提取日期，比如：[ 2025-07-03 ]
//                String ownText = block.ownText(); // 可能是 "[ 2025-07-03 ]"
//                String dateStr = "";
//
//                // 用正则提取日期字符串
//                Matcher matcher = Pattern.compile("\\[\\s*(\\d{4}-\\d{2}-\\d{2})\\s*\\]").matcher(ownText);
//                if (matcher.find()) {
//                    dateStr = matcher.group(1); // 提取 "2025-07-03"
//                }
//
//                if (dateStr.equals(date.toString())) {
//                    result.add(new NewsItem(title, href, date));
//                }
//            }

            Document doc;
            try {
                URL url = new URL(pageUrl);
                URLConnection connection = url.openConnection();
                connection.setRequestProperty("User-Agent", "Mozilla/5.0");
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine).append("\n");
                }
                in.close();

                doc = Jsoup.parse(response.toString(), pageUrl); // 解析 HTML 字符串
        } catch (IOException e) {
            System.err.println("❌ 河南省爬虫失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public String getProvinceName() {
        return "河南省";
    }

}
