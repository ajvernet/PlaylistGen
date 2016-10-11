package org.ssa.ironyard.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
import org.ssa.ironyard.model.Show;
import org.ssa.ironyard.service.mapper.AudioFile;
import org.ssa.ironyard.service.mapper.ImageUrl;
import org.ssa.ironyard.service.mapper.PodcastEpisode;
import org.ssa.ironyard.service.mapper.SearchResults;

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

    public String searchEpisodesByShowName(String showName) {
	final String uri = apiBaseUri + "/episodes/" + showName;
	RestTemplate restTemplate = new RestTemplate();
	ResponseEntity<String> result;
	result = restTemplate.exchange(uri, HttpMethod.GET, oauth, String.class);

	return result.getBody();
    }

    public List<Episode> searchEpisodesByGenre(String genre) throws UnsupportedEncodingException {
	final String uri = apiBaseUri + "/search/episodes/" + "filters[categories.name]=" + genre;
	RestTemplate restTemplate = new RestTemplate();
	ResponseEntity<SearchResults> result;
	result = restTemplate.exchange(uri, HttpMethod.GET, oauth, SearchResults.class);
	return processSearchResults(result.getBody());
    }
    
    public List<Episode> searchEpisodesByKeywords(String searchText){
	final String uri = apiBaseUri + "/search/episodes/" + searchText;
	RestTemplate restTemplate = new RestTemplate();
	ResponseEntity<SearchResults> result;
	result = restTemplate.exchange(uri, HttpMethod.GET, oauth, SearchResults.class);
	return processSearchResults(result.getBody());
    }
    
    public void getTasties(){
	final String uri = apiBaseUri + "/tasties/1";
	RestTemplate restTemplate = new RestTemplate();
	ParameterizedTypeReference<List<Map<String, Object>>> typeRef = new ParameterizedTypeReference<List<Map<String, Object>>>() {};
	ResponseEntity<List<Map<String, Object>>> result;
	LOGGER.debug("{}", restTemplate.exchange(uri,HttpMethod.GET, oauth, String.class));
	result = restTemplate.exchange(uri, HttpMethod.GET, oauth, typeRef);
	System.out.println(result.getBody().get(0).get("episode").getClass());
    }

    private List<Episode> processSearchResults(SearchResults searchResults){
	List<Episode> episodes = new ArrayList<>();
	for(PodcastEpisode p : searchResults.getResults()){
	    String fileUrl = "";
	    for(AudioFile a : p.getAudio_files()){
		if(a.getMp3()!=null)
		fileUrl = a.getMp3();
		else if(a.getUrl()!=null)
		    fileUrl = a.getUrl();
	    }
	    String fullImgUrl = "";
	    String thumbImgUrl = "";
	    for(ImageUrl u : p.getImg_urls()){
		if(u.getFull()!= null)
		    fullImgUrl = u.getFull();
		if(u.getThumb()!=null)
		    thumbImgUrl = u.getThumb();
	    }	    
	    Episode e = Episode.builder()
		    .episodeId(p.getId())
		    .name(p.getTitle())
		    .duration(p.getDuration())
		    .fileUrl(fileUrl)
		    .build();
	    if(e.getFileUrl().endsWith("mp3"))
		episodes.add(e);
	}
	return episodes;
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
