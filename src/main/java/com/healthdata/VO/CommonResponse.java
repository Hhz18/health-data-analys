package com.healthdata.VO;

import lombok.Data;

@Data
public class CommonResponse<T> {
    private Integer code;
    private String message;
    private T data;

    // 成功响应
    public static <T> CommonResponse<T> success(T data, String message) {
        CommonResponse<T> response = new CommonResponse<>();
        response.setCode(200);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    // 失败响应
    public static <T> CommonResponse<T> fail(Integer code, String message) {
        CommonResponse<T> response = new CommonResponse<>();
        response.setCode(code);
        response.setMessage(message);
        response.setData(null);
        return response;
    }
}