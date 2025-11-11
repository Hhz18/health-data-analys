package com.healthdata.service.impl;

import com.healthdata.service.ChatService;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class ChatServiceImpl implements ChatService {

    private final OkHttpClient httpClient;

    @Value("${chat.api.key}")
    private String apiKey;

    @Value("${chat.api.url}")
    private String apiUrl;

    @Autowired
    public ChatServiceImpl(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Flux<String> streamChat(String userMessage) {
        return Flux.create(fluxSink -> {
            JSONObject json = new JSONObject();
            json.put("model", "deepseek-r1-250528");
            JSONArray messages = new JSONArray();
            messages.put(new JSONObject().put("role", "system").put("content", "You are a helpful assistant."));
            messages.put(new JSONObject().put("role", "user").put("content", userMessage));
            json.put("messages", messages);
            json.put("stream", true);
            json.put("temperature", 0.6);

            RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json"));

            Request request = new Request.Builder()
                    .url(apiUrl)
                    .addHeader("Authorization", "Bearer " + apiKey)
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();

            httpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    fluxSink.error(e);
                }

                @Override
                public void onResponse(Call call, Response response) {
                    if (!response.isSuccessful()) {
                        fluxSink.error(new IOException("Unexpected code " + response));
                        return;
                    }

                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body().byteStream()))) {
                        String line;
                        while ((line = reader.readLine()) != null && !fluxSink.isCancelled()) {
                            if (line.startsWith("data: ")) {
                                String data = line.substring(6).trim();
                                if (data.equals("[DONE]")) {
                                    break;
                                }
                                fluxSink.next(data + "\n\n");
                            }
                        }
                        fluxSink.complete();
                    } catch (Exception e) {
                        fluxSink.error(e);
                    }
                }
            });

            fluxSink.onCancel(() -> {
                // Cancel the OkHttp call if the Flux subscription is cancelled
                for (Call call : httpClient.dispatcher().runningCalls()) {
                    if (call.request().equals(request)) {
                        call.cancel();
                        break;
                    }
                }
            });
        });
    }
}

