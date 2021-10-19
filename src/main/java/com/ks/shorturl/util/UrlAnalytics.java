package com.ks.shorturl.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

@Component
public class UrlAnalytics {

    @Autowired
    Jedis jedis;

    Map<String, String> dayMap = new HashMap<>();

    Map<String, String> weekMap = new HashMap<>();

    Map<String, String> allTimeMap = new HashMap<>();

    public void initializeAnalytics(String shortUrl){

        if(!jedis.exists("day")){
            dayMap.put("0","0");
            jedis.hmset("day", dayMap);
            jedis.expire("day", 86400);

        }

        if(!jedis.exists("week")){
            weekMap.put("0","0");
            jedis.hmset("week", weekMap);
            jedis.expire("week", 604800);
        }

        if(!jedis.exists("allTime")){
            allTimeMap.put("0","0");
            jedis.hmset("allTime", allTimeMap);
        }

        updateAnalytics(shortUrl);
    }

    public void updateAnalytics(String shortUrl){

        if(jedis.hget("day", shortUrl) != null){
            Integer dayCount = Integer.parseInt(jedis.hget("day", shortUrl));
            dayCount += 1;
            jedis.hset("day", shortUrl, String.valueOf(dayCount));
        }

        else{
            jedis.hset("day", shortUrl, String.valueOf(1));
        }

        if(jedis.hget("week", shortUrl) != null){
            Integer weekCount = Integer.parseInt(jedis.hget("week", shortUrl));
            weekCount += 1;
            jedis.hset("week", shortUrl, String.valueOf(weekCount));
        }
        else{
            jedis.hset("week", shortUrl, String.valueOf(1));
        }

        if(jedis.hget("allTime", shortUrl) != null){
            Integer count = Integer.parseInt(jedis.hget("allTime", shortUrl));
            count += 1;
            jedis.hset("allTime", shortUrl, String.valueOf(count));
        }
        else{
            jedis.hset("allTime", shortUrl, String.valueOf(1));
        }

    }

    public String getResult(String shortUrl){
        Integer dayCount = 0;
        if(jedis.hget("day", shortUrl) != null){
            dayCount = Integer.parseInt(jedis.hget("day", shortUrl));
        }

        Integer weekCount = 0;
        if(jedis.hget("week", shortUrl) != null){
             weekCount = Integer.parseInt(jedis.hget("week", shortUrl));
        }

        Integer count = 0;
        if(jedis.hget("allTime", shortUrl) != null){
            count = Integer.parseInt(jedis.hget("allTime", shortUrl));
        }


        StringBuilder sb = new StringBuilder();
        sb.append("The URL "+shortUrl + " was accessed "+dayCount+" times in the last 24 hours."+"\n");
        sb.append("\n");
        sb.append("The URL "+shortUrl + " was accessed "+weekCount+" times in the last week."+"\n");
        sb.append("\n");
        sb.append("The URL "+shortUrl + " was totally accessed "+count+" times. "+"\n");
        sb.append("\n");

        return sb.toString();

    }

    public String getTopSearch(){
        int max = Integer.MIN_VALUE;
        String url = "";
        Map<String, String> map = jedis.hgetAll("allTime");
        for(String key: map.keySet()){
            if(Integer.parseInt(map.get(key)) > max){
                max = Integer.parseInt(map.get(key));
                url = key;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("The URL "+ url + " was accessed "+ max + " times.");
        return  sb.toString();
    }


}
