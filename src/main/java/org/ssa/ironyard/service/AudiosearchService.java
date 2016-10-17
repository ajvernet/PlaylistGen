package org.ssa.ironyard.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.Genre;
import org.ssa.ironyard.model.Show;
import org.ssa.ironyard.service.mapper.AudioFile;
import org.ssa.ironyard.service.mapper.Category;
import org.ssa.ironyard.service.mapper.EpisodeQueryResult;
import org.ssa.ironyard.service.mapper.EpisodeResult;
import org.ssa.ironyard.service.mapper.TastiesAudioFile;
import org.ssa.ironyard.service.mapper.TastiesEpisodeResult;
import org.ssa.ironyard.service.mapper.TastiesResult;

import com.google.gson.Gson;

@Service
public class AudiosearchService {
    static String apiBaseUri = "https://www.audiosear.ch/api";

    private final AudiosearchAuthorizationService authorizationService;
    private Logger LOGGER = LogManager.getLogger(AudiosearchService.class);
    private final HttpEntity<String> oauth;

    @Autowired
    public AudiosearchService(AudiosearchAuthorizationService authorizationService) {
	LOGGER.info("AudioSearchService is loading");
	this.authorizationService = authorizationService;
	this.oauth = getHeaders();
    }

    public List<Episode> searchEpisodesAlt(String genre, String searchText, Integer size) {

	String uri = apiBaseUri + "/search/episodes/" + searchText + "?";
	if (genre != null && !genre.isEmpty())
	    uri += "&filters[categories.id]=" + Genre.getInstance(genre).getId();
	if (size != null)
	    uri += "&size=" + size + "&from=0";
	LOGGER.debug("searchUrl: {}", uri);
	RestTemplate restTemplate = new RestTemplate();
	ResponseEntity<EpisodeQueryResult> response;
	response = restTemplate.exchange(uri, HttpMethod.GET, oauth, EpisodeQueryResult.class);
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
	LOGGER.debug("searchUrl: {}", uri);
	RestTemplate restTemplate = new RestTemplate();
	String tastiesList = restTemplate.getForObject(uri, String.class, oauth);
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

	return episodes;
    }

    public List<String> getGenres() {
	return Stream.of(Genre.values()).map(g -> g.getName()).collect(Collectors.toList());
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
}
