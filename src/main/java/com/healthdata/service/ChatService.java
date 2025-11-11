package com.healthdata.service;

import reactor.core.publisher.Flux;

public interface ChatService {
    Flux<String> streamChat(String userMessage);
}

