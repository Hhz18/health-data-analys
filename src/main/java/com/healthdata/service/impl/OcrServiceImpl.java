package com.healthdata.service.impl;

import com.healthdata.service.OcrService;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
public class OcrServiceImpl implements OcrService {
    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().readTimeout(300, TimeUnit.SECONDS).build();

    @Value("${baidu.ocr.accessToken}")
    private String accessToken;

    @Override
    public String ocr(String imageUrl) {
        try {
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            String bodyStr = "url=" + java.net.URLEncoder.encode(imageUrl, "UTF-8") + "&cell_contents=false&return_excel=false";
            RequestBody body = RequestBody.create(mediaType, bodyStr);
            Request request = new Request.Builder()
                    .url("https://aip.baidubce.com/rest/2.0/ocr/v1/table?access_token=" + accessToken)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("Accept", "application/json")
                    .build();
            Response response = HTTP_CLIENT.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String ocrByBase64(String imageBase64) {
        try {
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            String bodyStr = "image=" + java.net.URLEncoder.encode(imageBase64, "UTF-8") + "&cell_contents=false&return_excel=false";
            RequestBody body = RequestBody.create(mediaType, bodyStr);
            Request request = new Request.Builder()
                    .url("https://aip.baidubce.com/rest/2.0/ocr/v1/table?access_token=" + accessToken)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("Accept", "application/json")
                    .build();
            Response response = HTTP_CLIENT.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
