package com.ks.shorturl.util;

import com.google.common.hash.Hashing;
import com.ks.shorturl.model.Url;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.nio.charset.Charset;


@Component
public class UrlHelper {

    @Value("${redis.ttl}")
    public static long timeToLive = 20;

    @Autowired
    Jedis jedis;

    @Autowired
    public RedisTemplate<String, Url> redisTemplate;

    public UrlHelper() {
    }

    public boolean isValidUrl(String url){
        String validProtocols[] = new String[]{"http","https"};
        UrlValidator urlValidator = new UrlValidator(validProtocols);
        return urlValidator.isValid(url);
    }

    public String generateHash(String url){

        String hash = Hashing.murmur3_32().hashString(url, Charset.defaultCharset()).toString();
        return hash;
    }

    public void persistData(Url url){
        redisTemplate.opsForValue().set(url.getShortUrl(), url);
    }

    public void analytics(){
    }


}
