package com.healthdata.service;

public interface OcrService {
    /**
     * 通过图片公网URL识别表格内容
     */
    String ocr(String imageUrl);

    /**
     * 通过图片Base64字符串识别表格内容
     */
    String ocrByBase64(String imageBase64);
}
