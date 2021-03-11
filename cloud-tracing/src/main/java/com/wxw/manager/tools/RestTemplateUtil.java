package com.wxw.manager.tools;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @program: api
 * @description:
 * @author: wxw
 * @create: 2019-06-04 18:00
 */
public class RestTemplateUtil {
    /**
     * 发送表单参数的post请求
     *
     * @param url      请求url
     * @param param    参数
     * @param respType 返回类型
     * @return T
     */
    public static <T> T postForm(String url, Map<String, List<Object>> param, Class<T> respType) {
        return getRestInstance().postForEntity(url, getHttpEntity(param, false), respType).getBody();
    }

    /**
     * 发送表单参数的异步post请求
     *
     * @param url      请求url
     * @param callback 回调接口
     * @param respType 返回类型
     */
    public static <T> void asyncPostForm(String url, Map<String, List<Object>> param,
                                         Class<T> respType, ListenableFutureCallback<ResponseEntity<T>> callback) {
        getAsyncRestInstance().postForEntity(url, getHttpEntity(param, false), respType).addCallback(callback);
    }

    /**
     * 发送表单有参数get请求
     *
     * @param url      请求url
     * @param param    参数对象
     * @param respType 返回类型
     * @return T
     */
    public static <T> T getForm(String url, Class<T> respType, Map<String,String> param) {
        return getRestInstance().getForEntity(url, respType, param).getBody();
    }

    /**
     * @Description: 发送表单无参数的get请求
     * @Param: [url, param, respType]
     * @return: T
     * @Author: tonyzhang
     * @Date: 2019-01-18 17:23
     */
    public static <T> T getForm(String url, Class<T> respType) {
        return getRestInstance().getForObject(url, respType);
    }


    /**
     * 获取HttpEntity实例对象
     *
     * @param param  参数对象
     * @param isJson true 发送json请求,false发送表单请求
     * @return HttpEntity
     */
    private static <P> HttpEntity<P> getHttpEntity(P param, boolean isJson) {
        HttpHeaders headers = new HttpHeaders();
        if (isJson) {
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        } else {
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        }

        return new HttpEntity<>(param, headers);
    }

    /*-----------------生产单例对象，方便自定义如何构造对象------------------*/

    private static RestTemplate restInit() {
        //设置连接超时和读取超时时间
        SimpleClientHttpRequestFactory factory=new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        RestTemplate restTemplate = new RestTemplate(factory);
        FormHttpMessageConverter fastConverter = new FormHttpMessageConverter();
        MessageConverter wmc = new MessageConverter();
        restTemplate.getMessageConverters().add(fastConverter);
        restTemplate.getMessageConverters().add(wmc);
        return restTemplate;
    }



    private static AsyncRestTemplate asyncRestInit() {
        return new AsyncRestTemplate();
    }

    private static RestTemplate getRestInstance() {
        return RestSingle.INSTANCE;
    }

    private static AsyncRestTemplate getAsyncRestInstance() {
        return AsyncRestSingle.INSTANCE;
    }

    private static class RestSingle {
        private static final RestTemplate INSTANCE = restInit();
    }

    private static class AsyncRestSingle {
        private static final AsyncRestTemplate INSTANCE = asyncRestInit();
    }
}
