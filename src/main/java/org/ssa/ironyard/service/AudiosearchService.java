package org.ssa.ironyard.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.ssa.ironyard.interceptor.LoggingRequestInterceptor;
import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.Genre;
import org.ssa.ironyard.model.Show;
import org.ssa.ironyard.service.mapper.AudioFile;
import org.ssa.ironyard.service.mapper.Category;
import org.ssa.ironyard.service.mapper.EpisodeQueryResult;
import org.ssa.ironyard.service.mapper.EpisodeResult;
import org.ssa.ironyard.service.mapper.ShowResult;
import org.ssa.ironyard.service.mapper.TastiesAudioFile;
import org.ssa.ironyard.service.mapper.TastiesEpisodeResult;
import org.ssa.ironyard.service.mapper.TastiesResult;

import com.google.gson.Gson;

@Service
public class AudiosearchService {
    static String apiBaseUri = "https://www.audiosear.ch/api";

    private final AudiosearchAuthorizationService authorizationService;
    private final PlaylistService playlistService;
    private Logger LOGGER = LogManager.getLogger(AudiosearchService.class);
    private final HttpEntity<String> oauth;

    @Autowired
    public AudiosearchService(AudiosearchAuthorizationService authorizationService, PlaylistService playlistService) {
	LOGGER.info("AudioSearchService is loading");
	this.authorizationService = authorizationService;
	this.playlistService = playlistService;
	this.oauth = getHeaders();
	LOGGER.info("AudioSearchService has loaded");
    }

    public List<Episode> searchEpisodesAlt(String genre, String searchText, Integer size) {

	String uri = apiBaseUri + "/search/episodes/" + searchText + "?";
	if (genre != null && !genre.isEmpty())
	    uri += "&filters[categories.id]=" + Genre.getInstance(genre).getId();
	if (size != null)
	    uri += "&size=" + size + "&from=0";
	LOGGER.debug("searchUrl: {}", uri);
	RestTemplate restTemplate = new RestTemplate();

	ClientHttpRequestInterceptor ri = new LoggingRequestInterceptor();
	List<ClientHttpRequestInterceptor> ris = new ArrayList<ClientHttpRequestInterceptor>();
	ris.add(ri);
	restTemplate.setInterceptors(ris);
	ResponseEntity<EpisodeQueryResult> response;
	try {
	    response = restTemplate.exchange(uri, HttpMethod.GET, oauth, EpisodeQueryResult.class);
	} catch (RestClientException r) {
	    throw new RestClientException("Unable to retrieve results from Audiosear.ch");
	}
	List<Episode> episodes = new ArrayList<>();
	for (EpisodeResult episodeResult : response.getBody().getResults()) {
	    Episode episode = Episode.builder().episodeId(episodeResult.getId()).name(episodeResult.getTitle())
		    .genreId(getGenreId(episodeResult)).description(episodeResult.getDescription())
		    .duration(getDuration(episodeResult)).fileUrl(getAudioFileUrl(episodeResult))
		    .show(new Show(episodeResult.getShowId(), episodeResult.getShowTitle(),
			    getThumbnail(episodeResult)))
		    .build();
	    if (episode.getFileUrl() != null)
		episodes.add(episode);
	}

	return episodes;
    }

    private String getThumbnail(EpisodeResult episodeResult) {
	if (episodeResult.getImageUrls() != null && episodeResult.getImageUrls() instanceof Map)
	    return ((Map<String, String>) episodeResult.getImageUrls()).get("thumb");
	return null;
    }

    private String getAudioFileUrl(EpisodeResult episodeResult) {
	List<String> audioFiles = new ArrayList<>();
	for (AudioFile audioFile : episodeResult.getAudioFiles()) {
	    if (audioFile.getMp3() != null && !audioFile.getMp3().isEmpty())
		audioFiles.add(audioFile.getMp3());
	    if (audioFile.getUrl() != null && audioFile.getUrl().size() > 0 && !audioFile.getUrl().get(0).isEmpty())
		for (int i = 0; i < audioFile.getUrl().size(); i++)
		    audioFiles.add(audioFile.getUrl().get(i));
	    if (audioFile.getUrls() != null && audioFile.getUrls().size() > 0 && !audioFile.getUrls().get(0).isEmpty())
		for (int i = 0; i < audioFile.getUrls().size(); i++)
		    audioFiles.add(audioFile.getUrls().get(i));
	    if (audioFile.getFilename() != null && !audioFile.getFilename().isEmpty())
		audioFiles.add(audioFile.getFilename());
	    if (audioFile.getUrlTitle() != null && !audioFile.getUrlTitle().isEmpty())
		audioFiles.add(audioFile.getUrlTitle());
	}
	for (String audioFile : audioFiles) {
	    if (audioFile.endsWith("mp3"))
		return audioFile;
	}
	LOGGER.debug("No acceptable audio files found {}: {}", episodeResult.getId(), episodeResult.getTitle());
	return null;
    }

    private String getTastiesAudioFile(TastiesEpisodeResult t) {
	List<String> audioFiles = new ArrayList<String>();
	TastiesAudioFile audioFile = t.getAudio_files();
	if (audioFile.getUrls() != null && audioFile.getUrls().size() > 0 && !audioFile.getUrls().get(0).isEmpty())
	    for (int i = 0; i < audioFile.getUrls().size(); i++)
		audioFiles.add(audioFile.getUrls().get(i));
	LOGGER.debug("There are {} candidate audio files", audioFiles.size());
	for (String audioFileCandidate : audioFiles) {
	    if (audioFileCandidate.endsWith("mp3"))
		return audioFileCandidate;
	}
	LOGGER.debug("No acceptable audio files found {}: {}", t.getId(), t.getTitle());
	return null;
    }

    private Integer getDuration(EpisodeResult episodeResult) {
	if (episodeResult.getDuration() != null && episodeResult.getDuration() != 0)
	    return episodeResult.getDuration();
	return null;
    }

    private Integer getGenreId(EpisodeResult episodeResult) {
	List<Integer> categoryIds = new ArrayList<>();
	for (Category category : episodeResult.getCategories()) {
	    if (category.getId() != null)
		categoryIds.add(category.getId());
	}
	categoryIds.sort(Comparator.naturalOrder());
	return categoryIds.size() > 0 ? categoryIds.get(0) : null;
    }

    public List<Episode> getTasties() {
	final String uri = apiBaseUri + "/tasties";
	Integer maxEpisodes = 20;
	LOGGER.debug("tastiesUrl: {}", uri);
	RestTemplate restTemplate = new RestTemplate();
	ClientHttpRequestInterceptor ri = new LoggingRequestInterceptor();
	List<ClientHttpRequestInterceptor> ris = new ArrayList<ClientHttpRequestInterceptor>();
	ris.add(ri);
	restTemplate.setInterceptors(ris);
	String tastiesList;
	try {
	    tastiesList = restTemplate.getForObject(uri, String.class);
	    // tastiesList = restTemplate.exchange(uri, HttpMethod.GET, oauth,
	    // String.class).getBody();
	} catch (Exception e) {
	    LOGGER.debug("getTasties: {}", e.getMessage());
	    throw new RuntimeException("No response received from audiosear.ch");
	}
	LOGGER.debug("Response received and loaded");

	Gson gson = new Gson();
	System.out.println(tastiesList);
	TastiesResult[] tasties = gson.fromJson(tastiesList, TastiesResult[].class);

	LOGGER.debug("Received {} tasties", tasties.length);

	List<TastiesEpisodeResult> episodeResults = new ArrayList<>();
	for (TastiesResult t : tasties) {
	    if (t.getEpisode() == null)
		System.err.println("Null episode results");
	    else
		episodeResults.add(t.getEpisode());
	    LOGGER.debug(t.getEpisode());
	}
	List<Episode> episodes = new ArrayList<>();
	for (TastiesEpisodeResult episodeResult : episodeResults) {
	    Episode episode = Episode.builder().episodeId(episodeResult.getId()).name(episodeResult.getTitle())
		    .description(episodeResult.getDescription()).duration(episodeResult.getAudio_files().getDuration())
		    .fileUrl(getTastiesAudioFile(episodeResult))
		    .show(new Show(episodeResult.getShow_id(), episodeResult.getShow_title(), null)).build();
	    if (episode.getFileUrl() != null)
		episodes.add(episode);
	}
	StringJoiner joiner = new StringJoiner(" id: ", "id: ", "");
	if (episodes.size() == 0)
	    try {
		throw new RuntimeException("Tasties contained no valid audio files");
	    } catch (Exception e1) {
		throw new RuntimeException("Tasties contained no valid audio files");
	    }
	episodes.stream().forEach(e -> joiner.add(e.getEpisodeId().toString()));
	String searchText = joiner.toString();
	episodes.clear();
	LOGGER.trace("Search text: " + searchText);
	String searchUri = apiBaseUri + "/search/episodes/" + searchText;
	searchUri += maxEpisodes > 10 ? "?size=" + maxEpisodes + "&from=0" : "";
	LOGGER.debug("searchUrl: {}", searchUri);
	ResponseEntity<EpisodeQueryResult> response;
	try {
	    response = restTemplate.exchange(searchUri, HttpMethod.GET, oauth, EpisodeQueryResult.class);
	} catch (Exception e) {
	    LOGGER.debug("getTasties: {}", e.getMessage());
	    throw new RuntimeException("No response from audiosear.ch");
	}
	LOGGER.info("Response contained {} results", response.getBody().getResults().size());
	for (EpisodeResult episodeResult : response.getBody().getResults()) {
	    Episode episode = Episode.builder().episodeId(episodeResult.getId()).name(episodeResult.getTitle())
		    .genreId(getGenreId(episodeResult)).description(episodeResult.getDescription())
		    .duration(getDuration(episodeResult)).fileUrl(getAudioFileUrl(episodeResult))
		    .show(new Show(episodeResult.getShowId(), episodeResult.getShowTitle(),
			    getThumbnail(episodeResult)))
		    .build();
	    if (episode.getFileUrl() != null)
		episodes.add(episode);
	}
	LOGGER.trace(episodes);
	LOGGER.debug("{} valid episodes found", episodes.size());
	return episodes;
    }

    public List<String> getGenres() {
	return Stream.of(Genre.values()).map(g -> g.getName()).collect(Collectors.toList());
    }

    public List<Episode> getNewShowsByUserId(Integer userId) {
	final Integer maxEpisodes = 30;
	final String uri = apiBaseUri + "/shows/";
	List<Episode> episodes = new ArrayList<>();
	List<Episode> topTenShows = playlistService.getTopTenShowsByUserId(userId);
	LOGGER.info("User had {} shows in his/her top ten", topTenShows.size());
	LOGGER.trace(topTenShows);
	RestTemplate restTemplate = new RestTemplate();
	ClientHttpRequestInterceptor ri = new LoggingRequestInterceptor();
	List<ClientHttpRequestInterceptor> ris = new ArrayList<ClientHttpRequestInterceptor>();
	ris.add(ri);
	restTemplate.setInterceptors(ris);
	for (Episode episode : topTenShows) {
	    LOGGER.trace("Checking for new episodes for show: {}", episode.getShow().getId());
	    String showUri = uri + episode.getShow().getId().toString();
	    LOGGER.trace("Going to " + showUri);
	    ShowResult showResults;
	    try {
		showResults = restTemplate.exchange(showUri, HttpMethod.GET, oauth, ShowResult.class).getBody();
	    } catch (RestClientException r) {
		continue;
	    }
	    LOGGER.trace("{}", showResults.getEpisode_ids());
	    episodes.addAll(showResults.getEpisode_ids().stream().filter(e -> e > episode.getEpisodeId())
		    .map(e -> Episode.builder().episodeId(e).build()).collect(Collectors.toList()));
	    if (episodes.size() >= maxEpisodes)
		break;
	}

	episodes = episodes.subList(0, episodes.size() < maxEpisodes ? episodes.size() : maxEpisodes);
	StringJoiner joiner = new StringJoiner(" id: ", "id: ", "");
	episodes.stream().forEach(e -> joiner.add(e.getEpisodeId().toString()));
	String searchText = episodes.size() == 0 ? "*" : joiner.toString();
	episodes.clear();
	LOGGER.trace("Search text: " + searchText);
	String searchUri = apiBaseUri + "/search/episodes/" + searchText;
	searchUri += maxEpisodes > 10 ? "?size=" + maxEpisodes + "&from=0" : "";
	LOGGER.debug("searchUrl: {}", searchUri);
	ResponseEntity<EpisodeQueryResult> response;
	try {
	    response = restTemplate.exchange(searchUri, HttpMethod.GET, oauth, EpisodeQueryResult.class);
	} catch (Exception e) {
	    LOGGER.debug("getNewShowsByUserId: {}", e.getMessage());
	    throw new RuntimeException("No response from audiosear.ch");
	}
	LOGGER.info("Response contained {} results", response.getBody().getResults().size());
	for (EpisodeResult episodeResult : response.getBody().getResults()) {
	    Episode episode = Episode.builder().episodeId(episodeResult.getId()).name(episodeResult.getTitle())
		    .genreId(getGenreId(episodeResult)).description(episodeResult.getDescription())
		    .duration(getDuration(episodeResult)).fileUrl(getAudioFileUrl(episodeResult))
		    .show(new Show(episodeResult.getShowId(), episodeResult.getShowTitle(),
			    getThumbnail(episodeResult)))
		    .build();
	    if (episode.getFileUrl() != null)
		episodes.add(episode);
	}
	LOGGER.trace(episodes);
	LOGGER.debug("{} valid episodes found", episodes.size());
	return episodes;

    }

    protected List<Episode> searchEpisodesByHundreds(Integer page) {

	String uri = apiBaseUri + "/search/episodes/*" + "?";
	uri += "sort_by=date_added&sort_order=desc&size=100&from=" + page * 100 + "";
	LOGGER.debug("searchUrl: {}", uri);
	RestTemplate restTemplate = new RestTemplate();
	ResponseEntity<EpisodeQueryResult> response;
	response = restTemplate.exchange(uri, HttpMethod.GET, oauth, EpisodeQueryResult.class);
	if (!response.getStatusCode().is2xxSuccessful())
	    return null;
	List<Episode> episodes = new ArrayList<>();
	for (EpisodeResult episodeResult : response.getBody().getResults()) {
	    Episode episode = Episode.builder().episodeId(episodeResult.getId()).name(episodeResult.getTitle())
		    .genreId(getGenreId(episodeResult)).description(episodeResult.getDescription())
		    .duration(getDuration(episodeResult)).fileUrl(getAudioFileUrl(episodeResult))
		    .show(new Show(episodeResult.getShowId(), episodeResult.getShowTitle(),
			    getThumbnail(episodeResult)))
		    .build();
	    if (episode.getFileUrl() != null)
		episodes.add(episode);
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

    protected boolean urlHeadRequestSucceeds(String url) {
	RestTemplate restTemplate = new RestTemplate();
	HttpHeaders headers = new HttpHeaders();
	headers.setAccept(Arrays.asList(MediaType.ALL));
	HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

	try {
	    restTemplate.exchange(url, HttpMethod.HEAD, entity, String.class);
	} catch (Exception e) {
	    LOGGER.debug("Exception thrown getting headers for resource: {}", url);
	    LOGGER.debug(e.getMessage());
	    return false;
	}
	return true;
    }
}
