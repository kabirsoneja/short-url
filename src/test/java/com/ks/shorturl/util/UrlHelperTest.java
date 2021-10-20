package com.ks.shorturl.util;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class UrlHelperTest extends TestCase {

    @InjectMocks
    UrlHelper urlHelper;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testUrlValidation_withValidUrl(){
        String testUrl = "https://validurl.com/";
        boolean check = urlHelper.isValidUrl(testUrl);
        assertEquals(true, check);
    }

    @Test
    public void testUrlValidation_withInvalidUrl(){
        String testUrl = "htts://validurl.com/";
        boolean check = urlHelper.isValidUrl(testUrl);
        assertEquals(false, check);
    }

    @Test
    public void testGenerateHash(){
        String testUrl = "https://validurl.com/";
        String hash = urlHelper.generateHash(testUrl);
        assertNotNull(hash);
    }



}