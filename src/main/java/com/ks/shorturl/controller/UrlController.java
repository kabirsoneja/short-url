package com.ks.shorturl.controller;

import com.ks.shorturl.model.Url;
import com.ks.shorturl.util.UrlAnalytics;
import com.ks.shorturl.util.UrlHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.time.LocalDateTime;

@Controller
@RequestMapping(value = "/urlShortener")
public class UrlController {

    @Autowired
    Jedis jedis;

    @Autowired
    UrlAnalytics urlAnalytics;

    @Autowired
    private UrlHelper urlHelper;

    @Autowired
    public RedisTemplate<String, Url> redisTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlController.class);


    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity generateShortUrl(@RequestBody Url url) {

        if(urlHelper.isValidUrl(url.getUrl())){
                String shortUrl = urlHelper.generateHash(url.getUrl());
                url.setShortUrl(shortUrl);
                url.setTimestamp(LocalDateTime.now().toString());
                urlHelper.persistData(url);
                LOGGER.info("Short URL generated");
                return ResponseEntity.ok(url.getShortUrl());
            }
        else{
            LOGGER.info("Invalid URL");
            return ResponseEntity.ok("Invalid URL");
        }

    }

    @RequestMapping(value = "/getLongUrl/{url}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getLongUrl(@PathVariable String url) {
        if(redisTemplate.hasKey(url)){
            urlAnalytics.initializeAnalytics(url);
            String longUrl = redisTemplate.opsForValue().get(url).getUrl();
            LOGGER.info("Long URL: "+ longUrl);
            return ResponseEntity.ok(redisTemplate.opsForValue().get(url));
        }
        else{
            LOGGER.info("Short URL does not exist in the database");
            return ResponseEntity.ok("Short URL does not exist!");
        }

    }


    @RequestMapping(value = "/deleteShortUrl/{url}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity deleteShortUrl(@PathVariable String url) {
        if(redisTemplate.hasKey(url)){
            redisTemplate.delete(url);
            LOGGER.info("Short URL deleted from database");
            return ResponseEntity.ok("Short Url deleted!");
        }
        else{
            LOGGER.info("Short URL does not exist in the database");
            return ResponseEntity.ok("Short URL does not exist!");
        }

    }

    @RequestMapping(value = "/analytics/{url}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity analytics(@PathVariable String url) {
        LOGGER.info("Generating analytics for: "+ url);
        String result = urlAnalytics.getResult(url);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/getTopSearch", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getTopSearch() {
        LOGGER.info("Generating Top Searched term");
        String result = urlAnalytics.getTopSearch();
        return ResponseEntity.ok(result);
    }


    @RequestMapping(value = "/health", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity health() {
        LOGGER.info("Health Check");
        return ResponseEntity.ok("Up");
    }



}
