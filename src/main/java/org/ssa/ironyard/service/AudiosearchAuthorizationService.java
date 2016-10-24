package org.ssa.ironyard.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.ssa.ironyard.model.AuthToken;

@Service
public class AudiosearchAuthorizationService {

    private final static String oauthBaseUri =  "https://www.audiosear.ch/oauth/token";
    private final String redirect;
    private final String appId;
    private final String secret;
    private volatile AuthToken authToken;
//    private final String code;

    Logger LOGGER = LogManager.getLogger(AudiosearchAuthorizationService.class);

    protected AudiosearchAuthorizationService(){
	redirect = null;
	appId = null;
	secret = null;	
    }
    
    public AudiosearchAuthorizationService(String appId, String secret, String redirect) {
	LOGGER.info("Authorization Service is loading");
	
	this.appId = appId;
	this.secret = secret;
	this.redirect = redirect;
    }

    public AuthToken getNewAccessToken() {
	Map<String, String> oauthConfig = new HashMap<>();
	oauthConfig.put("grant_type", "client_credentials");
	// oauthConfig.put("code", code);
	oauthConfig.put("redirect_uri", redirect);
	oauthConfig.put("client_id", appId);
	oauthConfig.put("client_secret", secret);
	RestTemplate restTemplate = new RestTemplate();
	return this.authToken = restTemplate.postForEntity(oauthBaseUri, oauthConfig, AuthToken.class).getBody();
    }
    
    public AuthToken getAccessToken(){
	
	    return null == this.authToken? getNewAccessToken(): this.authToken;
    }
}
