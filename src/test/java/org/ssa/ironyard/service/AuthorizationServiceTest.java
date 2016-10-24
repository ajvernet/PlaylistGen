package org.ssa.ironyard.service;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.ssa.ironyard.model.AuthToken;

public class AuthorizationServiceTest {
    private final static String apiBaseUri = "https://www.audiosear.ch/api";
    private final static String redirect = "urn:ietf:wg:oauth:2.0:oob";
    private final static String fileLocation = "src/main/resources/secrets.properties";
    AudiosearchAuthorizationService as;
    
    @Before
    public void init(){
	File initialFile = new File(fileLocation);

	Properties p = new Properties();
	try (InputStream inputStream = new FileInputStream(initialFile);) {
	    p.load(inputStream);
	} catch (IOException e) {
	    throw new RuntimeException(e.getMessage());
	}
	as = new AudiosearchAuthorizationService(p.getProperty("appId"), p.getProperty("secret"), redirect);
    }

    @Test
    public void testGetNewAccessToken() throws IOException  {
	as.getNewAccessToken();
    }
    
    @Test
    public void testGetAccessToken() throws IOException  {
	AuthToken a = as.getNewAccessToken();
	assertEquals(a, as.getAccessToken());
    }

}
