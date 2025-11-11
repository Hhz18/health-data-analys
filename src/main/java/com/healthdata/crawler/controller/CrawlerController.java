package com.healthdata.crawler.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthdata.crawler.core.NewsItem;
import com.healthdata.crawler.impl.BeijingNewsCrawler;
import com.healthdata.crawler.service.NewsAggregationService;
import jakarta.servlet.http.HttpServletResponse;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/crawler")
public class CrawlerController {
    @Autowired
    private  NewsAggregationService newsAggregationService;
    public CrawlerController(NewsAggregationService newsAggregationService){
        this.newsAggregationService=newsAggregationService;
    }

//    获取各省份近三天的新闻
    @GetMapping("/all")
    public Map<String,List<NewsItem>> getAllProvinceNews(@RequestParam(value = "days",defaultValue = "3") int days){
        return newsAggregationService.fetchAllProvincesNews(days);
    }


//    异步操作
    @GetMapping("/latest")
    public CompletableFuture<List<NewsItem>> getLatestNews(@RequestParam("date")String date){
//        异步处理
        return newsAggregationService.fetchNewsForProvince(new BeijingNewsCrawler(),3);
    }
    @GetMapping("/{days}")
    public CompletableFuture<Map<String ,List<NewsItem>>> getDaysNews(@PathVariable int days){
        return newsAggregationService.fetchDaysNews(days);
    }
//    真正的异步并发(最后有两种调用方法)
    //1.直接调用   http://localhost:8080/api/crawler/fetchDaysNews/3
    @GetMapping("/fetchDaysNews/{days}")
    public CompletableFuture<Map<String ,List<NewsItem>>> fetchDaysNews(@PathVariable int days){
        return newsAggregationService.fetchDaysNewsPlus(days);
    }

    //2.异步调用   http://localhost:8080/api/crawler/fetchDaysNewsWithResult?days=3
    @GetMapping("/fetchDaysNewsWithResult")
    public void fetchDaysNewsWithResult(@RequestParam int days, HttpServletResponse httpServletResponse){
        CompletableFuture<Map<String,List<NewsItem>>> futureResult =newsAggregationService.fetchDaysNewsPlus(days);

        futureResult.thenAccept(result ->{
            try{
                //在异步任务完成后返回结果
                httpServletResponse.setContentType("application/json");
                httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(result));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    //主界面轮询展示
    @GetMapping("/homepage")
    public ResponseEntity<List<NewsItem>> getHomnepageNews(){
        List<NewsItem>newsItemList =newsAggregationService.getChachedNews(3); //默认抓取三天的
        return ResponseEntity.ok(newsItemList);
    }
}
