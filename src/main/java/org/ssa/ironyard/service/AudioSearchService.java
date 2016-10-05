package org.ssa.ironyard.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AudioSearchService {
    static String apiBaseUri = "https://www.audiosear.ch/api";
    static String trendingUriFragment = "/trending";

    private AuthorizationService authorizationService;

    private Logger LOGGER = LogManager.getLogger(AudioSearchService.class);

    public AudioSearchService() throws IOException {
	authorizationService = new AuthorizationService();
    }

    public String getTrending() {
	final String uri = apiBaseUri + "/trending";
	LOGGER.debug("URI: {}", uri);
	RestTemplate restTemplate = new RestTemplate();
	ResponseEntity<String> result;
	result = restTemplate.exchange(uri, HttpMethod.GET, getHeaders(), String.class);
	LOGGER.debug(result.toString());
	return result.getBody();
    }

    public String getRandomEpisode() {
	final String uri = apiBaseUri + "/random_episode";
	LOGGER.debug("URI: {}", uri);
	RestTemplate restTemplate = new RestTemplate();
	ResponseEntity<String> result;
	result = restTemplate.exchange(uri, HttpMethod.GET, getHeaders(), String.class);
	LOGGER.debug(result.toString());
	return result.getBody();
    }

    public String getChartDaily() {
	final String uri = apiBaseUri + "/chart_daily";
	LOGGER.debug("URI: {}", uri);
	RestTemplate restTemplate = new RestTemplate();
	ResponseEntity<String> result;
	result = restTemplate.exchange(uri, HttpMethod.GET, getHeaders(), String.class);
	LOGGER.debug(result.toString());
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
