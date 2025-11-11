package com.healthdata.crawler.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.healthdata.crawler.core.NewsCrawler;
import com.healthdata.crawler.core.NewsItem;
import com.healthdata.crawler.core.SSLUtil;
import com.healthdata.crawler.impl.*;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.N;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.net.CookiePolicy;  // 直接使用 JDK 的类
import java.util.concurrent.CompletableFuture;

@Service
public class NewsAggregationService {

    @Autowired
    private StringRedisTemplate  redisTemplate;


    private final String NEWS_CACHE_KEY = "homepage:news:list";
    private final Duration CACHE_DURATION = Duration.ofHours(1);  //每隔1小时自动爬取


    //提供一个获取新闻列表的统一入口（优先从redis存取）
    public List<NewsItem> getChachedNews(int days){
        //先从redis查
        String cachedJson =redisTemplate.opsForValue().get(NEWS_CACHE_KEY);
        if (cachedJson !=null){
            try{

                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                return Arrays.asList(mapper.readValue(cachedJson,NewsItem[].class));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        // 2. 没有缓存则异步爬取，并缓存
        Map<String, List<NewsItem>> newsMap = fetchAllProvincesNews(days); // 或 fetchDaysNewsPlus
        List<NewsItem> allNews = newsMap.values().stream().flatMap(List::stream).toList();

        try{
            ObjectMapper mapper =new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            String json =mapper.writeValueAsString(allNews);
            redisTemplate.opsForValue().set(NEWS_CACHE_KEY,json,CACHE_DURATION);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allNews;
    }




    //最简单的爬取
    public Map<String,List<NewsItem>> fetchAllProvincesNews (int days) {
        SSLUtil.disableSslVerification(); //信任所有证书
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);  // 接受所有 cookie（包括 HttpOnly 和 Secure）
        CookieHandler.setDefault(cookieManager);

        List<NewsCrawler> crawlers = Arrays.asList(
                new BeijingNewsCrawler(),
                new HunanNewsCrawler(),
                new ShanghaiNewsCrawler(),
                new QingHaiNewsCrawler(),
//              new GanSuNewsCrawler(),这个盘爬不了，反爬技术太强了
                new SiChuanNewsCrawler(),
                new HaiNanNewsCrawler(),
                new HeNanNewsCrawler(),
                new HuBeiNewsCrawler(),
                new HeBeiNewsCrawler(),
                new ShanXiNewsCrawler(),
                new ShanXIiNewsCrawler(),
                new LiaoNingNewsCrawler(),
                new JiLingNewsCrawler(),
                new HeiLongJiangNewsCrawler(),
//              new JiangSuNewsCrawler(), 这个也爬不了太难了
//              new ZheJiangNewsCrawler(),没怕成功
                new GuangXiNewsCrawler(),
//              new GuangDongNewsCrawler(),失败
//              new FuJianNewsCrawler(),这个爬不了
                new ChongQingNewsCrawler(),
                new NeiMengGuNewsCrawler(),
                new ShanDongNewsCrawler(),
                new AnHuiNewsCrawler(),
                new JiangXiNewsCrawler(),
//              云南省的卫健委官网打不开，故放弃
                new YunNanNewsCrawler(),
                new XingJiangNewsCrawler()
        );

        Map<String, List<NewsItem>> result = new LinkedHashMap<>();

        for (NewsCrawler crawler : crawlers) {
            List<NewsItem> allItems = new ArrayList<>();

            for (int i = 0; i < days; i++) {
                LocalDate date = LocalDate.now().minusDays(i);
                List<NewsItem> items = crawler.fetchNewsByDate(date);
                allItems.addAll(items);
            }

            result.put(crawler.getProvinceName(), allItems);
        }
        return result;
    }


    //异步处理
//    @Async("taskExecutor") // 使用自定义的线程池
    //这里发现用线程池和比不用线程池慢好几秒
    @Async
    public CompletableFuture<List<NewsItem>> fetchNewsForProvince(NewsCrawler crawler,int days){
        List<NewsItem>result =new ArrayList<>();

        for (int i = 0; i < days; i++) {
            LocalDate date = LocalDate.now().minusDays(i);
            List<NewsItem> items = crawler.fetchNewsByDate(date);
            result.addAll(items);
        }

        return CompletableFuture.completedFuture(result);
    }

    //虽然是异步函数，但实质上还是顺序执行的
    @Async
    public CompletableFuture <Map<String ,List<NewsItem>>>fetchDaysNews(int days){
        SSLUtil.disableSslVerification(); //信任所有证书
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);  // 接受所有 cookie（包括 HttpOnly 和 Secure）
        CookieHandler.setDefault(cookieManager);

        List<NewsCrawler> crawlers = Arrays.asList(
                new BeijingNewsCrawler(),
                new HunanNewsCrawler(),
                new ShanghaiNewsCrawler(),
                new QingHaiNewsCrawler(),
//              new GanSuNewsCrawler(),这个盘爬不了，反爬技术太强了
                new SiChuanNewsCrawler(),
                new HaiNanNewsCrawler(),
                new HeNanNewsCrawler(),
                new HuBeiNewsCrawler(),
                new HeBeiNewsCrawler(),
                new ShanXiNewsCrawler(),
                new ShanXIiNewsCrawler(),
                new LiaoNingNewsCrawler(),
                new JiLingNewsCrawler(),
                new HeiLongJiangNewsCrawler(),
//              new JiangSuNewsCrawler(), 这个也爬不了太难了
//              new ZheJiangNewsCrawler(),没怕成功
                new GuangXiNewsCrawler(),
//              new GuangDongNewsCrawler(),失败
//              new FuJianNewsCrawler(),这个爬不了
                new ChongQingNewsCrawler(),
                new NeiMengGuNewsCrawler(),
                new ShanDongNewsCrawler(),
                new AnHuiNewsCrawler(),
                new JiangXiNewsCrawler(),
//              云南省的卫健委官网打不开，故放弃
                new YunNanNewsCrawler(),
                new XingJiangNewsCrawler()
        );
        Map<String, List<NewsItem>> result = new LinkedHashMap<>();
        for (NewsCrawler crawler : crawlers) {
            List<NewsItem> allItems = new ArrayList<>();

            for (int i = 0; i < days; i++) {
                LocalDate date = LocalDate.now().minusDays(i);
                List<NewsItem> items = crawler.fetchNewsByDate(date);
                allItems.addAll(items);
            }

            result.put(crawler.getProvinceName(), allItems);
        }
        return CompletableFuture.completedFuture(result); // ✅ 这句不能少！
    }

//    接下来是真正的异步并发 +缓存
    @Async("taskExecutor")// 使用自定义的线程池
//    @Async
    public CompletableFuture<Map<String, List<NewsItem>>>fetchDaysNewsPlus(int days){
        SSLUtil.disableSslVerification();
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);

        List<NewsCrawler> crawlers =Arrays.asList(
                new BeijingNewsCrawler(),
                new HunanNewsCrawler(),
                new ShanghaiNewsCrawler(),
                new QingHaiNewsCrawler(),
                new SiChuanNewsCrawler(),
                new HaiNanNewsCrawler(),
                new HeNanNewsCrawler(),
                new HuBeiNewsCrawler(),
                new HeBeiNewsCrawler(),
                new ShanXiNewsCrawler(),
                new ShanXIiNewsCrawler(),
                new LiaoNingNewsCrawler(),
                new JiLingNewsCrawler(),
                new HeiLongJiangNewsCrawler(),
                new GuangXiNewsCrawler(),
                new ChongQingNewsCrawler(),
                new NeiMengGuNewsCrawler(),
                new ShanDongNewsCrawler(),
                new AnHuiNewsCrawler(),
                new JiangXiNewsCrawler(),
                new YunNanNewsCrawler(),
                new XingJiangNewsCrawler()
        );

        //存储每个省的爬取异步任务
        List<CompletableFuture<Map.Entry<String,List<NewsItem>>>> futures=new ArrayList<>();

        for (NewsCrawler crawler :crawlers){
            // 异步调用fetchNewsForProvince并对结果封装成 Map.Entry<省名, 新闻列表>
            CompletableFuture<Map.Entry<String, List<NewsItem>>> future =fetchNewsForProvince(crawler,days)
                    .thenApply(newsList ->Map.entry(crawler.getProvinceName(),newsList));

            futures.add(future);
        }
        //等待所有任务完成 再合并成Map
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v ->{
                    Map<String,List<NewsItem>> result =new LinkedHashMap<>();
                    for (CompletableFuture<Map.Entry<String,List<NewsItem>>> future:futures){
                        Map.Entry<String ,List<NewsItem>> entry =future.join();
                        result.put(entry.getKey(),entry.getValue());
                    }
                    return result;
                });
    }
}
