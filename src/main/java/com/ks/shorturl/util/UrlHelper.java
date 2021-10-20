package com.ks.shorturl.util;

import com.google.common.hash.Hashing;
import com.ks.shorturl.model.Url;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;


@Component
public class UrlHelper {

    @Value("${redis.ttl}")
    public static long timeToLive = 600;

    @Autowired
    Jedis jedis;

    @Autowired
    public RedisTemplate<String, Url> redisTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlHelper.class);


    public UrlHelper() {
    }

    public boolean isValidUrl(String url){
        LOGGER.info("Validating the input URL");
        String validProtocols[] = new String[]{"http","https"};
        UrlValidator urlValidator = new UrlValidator(validProtocols);
        return urlValidator.isValid(url);
    }

    public String generateHash(String url){
        LOGGER.info("Generating unique short URL");
        String hash = Hashing.murmur3_32().hashString(url, Charset.defaultCharset()).toString();
        return hash;
    }

    public void persistData(Url url){
        LOGGER.info("Persisting Data in Redis");
        redisTemplate.opsForValue().set(url.getShortUrl(), url, timeToLive, TimeUnit.SECONDS);
    }

}
