package com.ks.shorturl.controller;

import com.ks.shorturl.model.Url;
import com.ks.shorturl.util.UrlHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(value = "/urlShortener")
public class UrlController {

    @Autowired
    private UrlHelper urlHelper;

    public Map<String, String> cache = new HashMap<>();


    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity generateShortUrl(@RequestBody Url url) {

        if(urlHelper.isValidUrl(url.getUrl())){
                String shortUrl = urlHelper.generateHash(url.getUrl());
                url.setShortUrl(shortUrl);
                url.setCreatedAt(LocalDateTime.now());
                cache.put(url.getShortUrl(), url.getUrl());
                return ResponseEntity.ok(url.getShortUrl());
            }
        else{
            return ResponseEntity.ok("Invalid URL");
        }

    }

    @RequestMapping(value = "/getLongUrl/{url}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getLongUrl(@PathVariable String url) {
        if(cache.containsKey(url)){
            return ResponseEntity.ok(cache.get(url));
        }
        else{
            return ResponseEntity.ok("Url does not exist");
        }

    }


    @RequestMapping(value = "/health", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity health() {
        return ResponseEntity.ok("Up");

    }

}
