package com.ks.shorturl.controller;

import com.ks.shorturl.model.Url;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;




@Controller
@RequestMapping(value = "/urlShortener")
public class UrlController {

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity postUrl(@RequestBody Url url) {
        return ResponseEntity.ok(url);

    }

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity health() {
        return ResponseEntity.ok("Up");

    }

}
