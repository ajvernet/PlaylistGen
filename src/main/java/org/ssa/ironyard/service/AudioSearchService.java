package org.ssa.ironyard.service;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;

@Service
public class AudioSearchService {
    static String apiBaseUri = "https://www.audiosear.ch/api";
    static String trendingUriFragment = "/trending";

    private final AuthorizationService authorizationService;
    private Logger LOGGER = LogManager.getLogger(AudioSearchService.class);
    private final HttpEntity<String> oauth;

    @Autowired
    public AudioSearchService(AuthorizationService authorizationService) {
	LOGGER.info("AudioSearchService is loading");
	this.authorizationService = authorizationService;
	this.oauth = getHeaders();
    }

    public String getTrending() {
	final String uri = apiBaseUri + "/trending";
	RestTemplate restTemplate = new RestTemplate();
	ResponseEntity<String> result;
	result = restTemplate.exchange(uri, HttpMethod.GET, oauth, String.class);
	return result.getBody();
    }

    public String getRandomEpisode() {
	final String uri = apiBaseUri + "/random_episode";
	RestTemplate restTemplate = new RestTemplate();
	ResponseEntity<String> result;
	result = restTemplate.exchange(uri, HttpMethod.GET, oauth, String.class);
	return result.getBody();
    }

    public String getEpisodeById(Integer id) {
	final String uri = apiBaseUri + "/episodes/" + id;
	RestTemplate restTemplate = new RestTemplate();
	ResponseEntity<String> result;
	result = restTemplate.exchange(uri, HttpMethod.GET, oauth, String.class);
	return result.getBody();
    }

    public String getChartDaily() {
	final String uri = apiBaseUri + "/chart_daily";
	RestTemplate restTemplate = new RestTemplate();
	ResponseEntity<String> result;
	result = restTemplate.exchange(uri, HttpMethod.GET, oauth, String.class);
	return result.getBody();
    }
    
    public String searchEpisodesByShowName(String showName){
	final String uri = apiBaseUri + "/episodes/" + HtmlUtils.htmlEscape(showName);
	RestTemplate restTemplate = new RestTemplate();
	ResponseEntity<String> result;
	result = restTemplate.exchange(uri, HttpMethod.GET, oauth, String.class);
	
	return result.getBody();
    }

    private HttpEntity<String> getHeaders() {
	HttpHeaders headers = new HttpHeaders();
	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	headers.setContentType(MediaType.APPLICATION_JSON);
	headers.set("Authorization", "Bearer " + authorizationService.getAccessToken().getAccess_token());
	HttpEntity<String> entity = new HttpEntity<String>(headers);
	LOGGER.debug(headers.toString());
	return entity;
    }
}
