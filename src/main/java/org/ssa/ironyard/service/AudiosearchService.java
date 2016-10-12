package org.ssa.ironyard.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.Genre;
import org.ssa.ironyard.model.Show;

@Service
public class AudiosearchService {
    static String apiBaseUri = "https://www.audiosear.ch/api";
    static String trendingUriFragment = "/trending";

    private final AudiosearchAuthorizationService authorizationService;
    private Logger LOGGER = LogManager.getLogger(AudiosearchService.class);
    private final HttpEntity<String> oauth;

    @Autowired
    public AudiosearchService(AudiosearchAuthorizationService authorizationService) {
	LOGGER.info("AudioSearchService is loading");
	this.authorizationService = authorizationService;
	this.oauth = getHeaders();
    }

    public List<Episode> searchEpisodes(String genre, String searchText, Integer size) {

	String uri = apiBaseUri + "/search/episodes/" + searchText + "?";
	if (genre != null && !genre.isEmpty())
	    uri += "&filters[categories.id]=" + Genre.getInstance(genre).getId();
	if (size != null)
	    uri += "&size=" + size + "&from=0";
	uri += "&sort_by=date_added&sort_order=desc";
	LOGGER.debug("searchUrl: {}", uri);
	RestTemplate restTemplate = new RestTemplate();
	ParameterizedTypeReference<Map<String, Object>> typeRef = new ParameterizedTypeReference<Map<String, Object>>() {
	};
	ResponseEntity<Map<String, Object>> response;
	response = restTemplate.exchange(uri, HttpMethod.GET, oauth, typeRef);
	List<Map<String, Object>> results = (List<Map<String, Object>>) response.getBody().get("results");
	return results.stream().map(result -> {

	    if ((List<Map<String, String>>) result.getOrDefault("audio_files", null) == null)
		return null;
	    LOGGER.debug("PodcastID: {}", result.get("id"));
	    LOGGER.debug("Audio: {}", result.get("audio_files"));
	    String fileUrl = ((List<Map<String, String>>) result.get("audio_files")).get(0).getOrDefault("mp3", "");
	    if (fileUrl == "")
		fileUrl = ((List<Map<String, List<String>>>) result.get("audio_files")).get(0).get("url").get(0);
	    if (fileUrl == null || !fileUrl.endsWith("mp3"))
		return null;

	    return Episode.builder().episodeId((Integer) result.get("id")).name((String) result.get("title"))
		    .description((String) result.get("description")).duration((Integer) result.get("duration"))
		    .fileUrl(fileUrl).show(new Show((Integer) result.get("show_id"), (String) result.get("show_title"),
			    ((Map<String, String>) result.get("image_urls")).get("thumb")))
		    .build();
	}).filter(episode -> episode != null).collect(Collectors.toList());
    }

    public void getTasties() {
	final String uri = apiBaseUri + "/tasties/1";
	RestTemplate restTemplate = new RestTemplate();
	ParameterizedTypeReference<List<Map<String, Object>>> typeRef = new ParameterizedTypeReference<List<Map<String, Object>>>() {
	};
	ResponseEntity<List<Map<String, Object>>> result;
	result = restTemplate.exchange(uri, HttpMethod.GET, oauth, typeRef);
	// System.out.println(result.getBody().get(0).get("episode").getClass());
    }

    public List<String> getGenres() {
	return Stream.of(Genre.values()).map(g -> g.getName()).collect(Collectors.toList());
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
