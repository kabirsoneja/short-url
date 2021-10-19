package com.ks.shorturl.util;

import org.apache.commons.validator.routines.UrlValidator;

public class UrlHelper {

    public boolean isValidUrl(String url){
        String validProtocols[] = new String[]{"http","https"};
        UrlValidator urlValidator = new UrlValidator(validProtocols);
        return urlValidator.isValid(url);
    }


}
