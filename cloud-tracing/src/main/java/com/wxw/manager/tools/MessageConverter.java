package com.wxw.manager.tools;// 增加一个MessageConverter

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: api
 * @description: 封装转换器, 添加更多类型的支持
 * @author: wxw
 * @create: 2019-06-05 11:13
 */
public class MessageConverter extends MappingJackson2HttpMessageConverter {
    public MessageConverter(){
        List<MediaType> mediaTypes=new ArrayList<>();
        //添加text/html类型的支持
        mediaTypes.add(MediaType.TEXT_HTML);
        //添加text/plain类型的支持.微信接口会用到
        mediaTypes.add(MediaType.TEXT_PLAIN);
        setSupportedMediaTypes(mediaTypes);
    }
}