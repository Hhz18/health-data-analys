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

public class GanSuNewsCrawler implements NewsCrawler {
    private final String pageUrl ="https://wsjk.gansu.gov.cn/";

    @Override
    public List<NewsItem> fetchNewsByDate(LocalDate date) {
        List<NewsItem>result =new ArrayList<>();
        try{
            Document doc = Jsoup.connect(pageUrl)
                    .userAgent("Mozilla/5.0")
                    .header("Referer", "https://wsjk.gansu.gov.cn/")
                    .timeout(10000)
                    .get();
            Elements blocks =doc.select("div.bd >ul >li");

            String targetStr =String.format("%02d-%02d", date.getMonthValue(), date.getDayOfMonth());
            for (Element block:blocks){
                String title = block.select("a").text().trim();
                String dateStr = block.select("span.date").text().trim();
                String href = block.select("a").attr("abs:href");

                if (dateStr.equals(targetStr)){
                    result.add(new NewsItem(title,href,date));
                }
            }
        }catch (Exception e){
            System.err.println("❌ 甘肃省爬虫失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public String getProvinceName() {
        return "甘肃省";
    }
}




//
//public class GanSuNewsCrawler implements NewsCrawler {
//
//    @Override
//    public List<NewsItem> fetchNewsByDate(LocalDate date) {
//        List<NewsItem> result = new ArrayList<>();
//        try {
//            Process process = new ProcessBuilder("python", "gansu_spider.py")
//                    .redirectErrorStream(true)
//                    .start();
//
//            BufferedReader reader = new BufferedReader(
//                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
//
//            StringBuilder output = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                output.append(line);
//            }
//
//            int exitCode = process.waitFor();
//            if (exitCode != 0) {
//                System.err.println("❌ Python 脚本执行失败");
//                return result;
//            }
//
//            Gson gson = new Gson();
//            List<Map<String, String>> items = gson.fromJson(output.toString(),
//                    new TypeToken<List<Map<String, String>>>() {}.getType());
//
//            String target = String.format("%02d-%02d", date.getMonthValue(), date.getDayOfMonth());
//
//            for (Map<String, String> item : items) {
//                if (item.get("date").equals(target)) {
//                    result.add(new NewsItem(
//                            item.get("title"),
//                            item.get("link"),
//                            date
//                    ));
//                }
//            }
//
//        } catch (Exception e) {
//            System.err.println("❌ 甘肃省爬虫失败：" + e.getMessage());
//        }
//
//        return result;
//    }
//
//    @Override
//    public String getProvinceName() {
//        return "甘肃省";
//    }
//}
