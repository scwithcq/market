/*
package com.fmcd.market.Config;

import com.fmcd.market.Common.base.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

*/
/**
 * 全局统一响应封装
 *//*

@ControllerAdvice(basePackages = "com.example.demo.controller") // 根据你的包路径修改
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {

    */
/**
     * 是否需要执行 beforeBodyWrite()
     *//*

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 对所有接口都进行拦截（返回类型本身已经是 Result 的除外）
        return true;
    }

    */
/**
     * 对返回结果进行封装
     *//*

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        // 如果已经是我们定义的统一结果结构，则直接返回
        if (body instanceof Result) {
            return body;
        }

        // 如果返回是 String 类型，需要特殊处理，否则会报类型转换错误
        if (body instanceof String) {
            return "{\"success\":true,\"code\":20000,\"message\":\"成功\",\"data\":" + body + "}";
        }

        // 正常对象包装成标准格式
        return Result.success(body);
    }
}
*/
