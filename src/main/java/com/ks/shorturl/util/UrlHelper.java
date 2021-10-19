package com.ks.shorturl.util;

import com.google.common.hash.Hashing;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Component
public class UrlHelper {

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


}
