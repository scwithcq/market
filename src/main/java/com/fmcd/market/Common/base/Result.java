package com.fmcd.market.Common.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private boolean success;
    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(true, 20000, "成功", data);
    }

    public static <T> Result<T> success(String message,T data){
        return new Result<>(true,20000,message,data);
    }

    public static <T> Result<T> fail(String message) {
        return new Result<>(false, 50000, message, null);
    }

}
