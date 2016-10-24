package org.ssa.ironyard.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
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
public class AudiosearchServiceImpl implements AudiosearchService {
    private final static String apiBaseUri = "https://www.audiosear.ch/api";
    private final static String redirect = "urn:ietf:wg:oauth:2.0:oob";
    private final static String fileLocation = "src/main/resources/secrets.properties";
    private final AudiosearchAuthorizationService authorizationService;
    private final PlaylistService playlistService;
    private Logger LOGGER = LogManager.getLogger(AudiosearchServiceImpl.class);
    private final HttpEntity<String> oauth;
    private final AsyncRestTemplate asyncTemplate;

    @Autowired
    public AudiosearchServiceImpl(PlaylistService playlistService) {

	File initialFile = new File(fileLocation);

	Properties p = new Properties();
	try (InputStream inputStream = new FileInputStream(initialFile);) {
	    p.load(inputStream);
	} catch (IOException e) {
	    throw new RuntimeException(e.getMessage());
	}
	LOGGER.info("AudioSearchService is loading");
	this.authorizationService = new AudiosearchAuthorizationService(p.getProperty("appId"), p.getProperty("secret"),
		redirect);
	this.playlistService = playlistService;
	this.oauth = getHeaders();
	LOGGER.info("AudioSearchService has loaded");
	this.asyncTemplate = new AsyncRestTemplate();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.ssa.ironyard.service.AudiosearchService#searchEpisodesAlt(java.lang.
     * String, java.lang.String, java.lang.Integer)
     */
    @Override
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
	    Episode episode = Episode.builder().episodeId(episodeResult.getId())
		    .name(Jsoup.parse(episodeResult.getTitle()).text()).genreId(getGenreId(episodeResult))
		    .description(episodeResult.getDescription() == null ? null
			    : Jsoup.parse(episodeResult.getDescription()).text())
		    .duration(getDuration(episodeResult)).fileUrl(getAudioFileUrl(episodeResult))
		    .show(new Show(episodeResult.getShowId(), episodeResult.getShowTitle(),
			    getThumbnail(episodeResult)))
		    .build();
	    if (episode.getFileUrl() != null)
		episodes.add(episode);
	}

	return episodes;
    }

    @SuppressWarnings("unchecked")
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

    /*
     * (non-Javadoc)
     * 
     * @see org.ssa.ironyard.service.AudiosearchService#getTasties()
     */
    @Override
    public List<Episode> getTasties() {
	final String uri = apiBaseUri + "/tasties";
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
	    Episode episode = Episode.builder().episodeId(episodeResult.getId())
		    .name(Jsoup.parse(episodeResult.getTitle()).text())
		    .description(episodeResult.getDescription() == null ? null
			    : Jsoup.parse(episodeResult.getDescription()).text())
		    .duration(episodeResult.getAudio_files().getDuration()).fileUrl(getTastiesAudioFile(episodeResult))
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

    /*
     * (non-Javadoc)
     * 
     * @see org.ssa.ironyard.service.AudiosearchService#getGenres()
     */
    @Override
    public List<String> getGenres() {
	return Stream.of(Genre.values()).map(g -> g.getName()).collect(Collectors.toList());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.ssa.ironyard.service.AudiosearchService#getNewShowsByUserId(java.lang
     * .Integer)
     */
    @Override
    public List<Episode> getNewShowsByUserId(Integer userId) {
	final Integer maxEpisodes = 30;
	final String uri = apiBaseUri + "/shows/";
	List<Episode> episodes = new ArrayList<>();
	List<Episode> topTenShows = playlistService.getTopTenShowsByUserId(userId);
	LOGGER.info("User had {} shows in his/her top ten", topTenShows.size());
	LOGGER.trace(topTenShows);
	RestTemplate restTemplate = new RestTemplate();
	ClientHttpRequestInterceptor ri = new LoggingRequestInterceptor();
	List<ClientHttpRequestInterceptor> ris = new ArrayList<>();
	ris.add(ri);
	restTemplate.setInterceptors(ris);
	// for (Episode episode : topTenShows) {
	// LOGGER.trace("Checking for new episodes for show: {}",
	// episode.getShow().getId());
	// String showUri = uri + episode.getShow().getId().toString();
	// LOGGER.trace("Going to " + showUri);
	// ShowResult showResults;
	// try {
	// showResults = restTemplate.exchange(showUri, HttpMethod.GET, oauth,
	// ShowResult.class).getBody();
	// } catch (RestClientException r) {
	// continue;
	// }
	// LOGGER.trace("{}", showResults.getEpisode_ids());
	// episodes.addAll(showResults.getEpisode_ids().stream().filter(e -> e >
	// episode.getEpisodeId())
	// .map(e ->
	// Episode.builder().episodeId(e).build()).limit(3).collect(Collectors.toList()));
	// if (episodes.size() >= maxEpisodes)
	// break;
	// }

	Queue<ShowResult> shows = getShowEpisodes(topTenShows);
	for(ShowResult show: shows){
	    List<Integer> episodeIds = show.getEpisode_ids();
	    for(int i = 0; i < episodeIds.size() && i < 3; i++){
		episodes.add(Episode.builder().episodeId(episodeIds.get(i)).build());
	    }
	}

	episodes = episodes.subList(0, episodes.size() < maxEpisodes ? episodes.size() : maxEpisodes);
	StringJoiner joiner = new StringJoiner(" id: ", "id: ", "");
	episodes.stream().forEach(e -> joiner.add(e.getEpisodeId().toString()));
	String searchText = episodes.isEmpty() ? "*" : joiner.toString();
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
		    .genreId(getGenreId(episodeResult))
		    .description(episodeResult.getDescription() == null ? null
			    : Jsoup.parse(episodeResult.getDescription()).text())
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
		    .genreId(getGenreId(episodeResult))
		    .description(episodeResult.getDescription() == null ? null
			    : Jsoup.parse(episodeResult.getDescription()).text())
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
	HttpEntity<String> entity = new HttpEntity<>(headers);
	LOGGER.debug(headers.toString());
	return entity;
    }

    protected Queue<ShowResult> getShowEpisodes(List<Episode> shows) {
	LOGGER.info("Starting #testShows");
	final String endPoint = apiBaseUri + "/shows/";
	final AsyncRestTemplate asyncRestTemplate = this.asyncTemplate;

	final Queue<ShowResult> results = new ConcurrentLinkedQueue<>();
	final List<ListenableFuture<ResponseEntity<ShowResult>>> futures = new ArrayList<>();

	final HttpEntity<String> headers = getHeaders();
	for (int index = 1; index < shows.size(); index++) {
	    final Episode episode = shows.get(index);
	    ListenableFuture<ResponseEntity<ShowResult>> futureResponse = asyncRestTemplate.exchange(
		    endPoint + episode.getShow().getId().toString(), HttpMethod.GET, headers, ShowResult.class);
	    futures.add(futureResponse);
	    futureResponse.addCallback(new ListenableFutureCallback<ResponseEntity<ShowResult>>() {
		@Override
		public void onSuccess(ResponseEntity<ShowResult> response) {
		    ShowResult sr = response.getBody();
		    sr.setEpisode(episode);
		    sr.filter();
		    results.add(sr);
		}

		@Override
		public void onFailure(Throwable thrwbl) {
		    LOGGER.warn("show request {} failed -> {}", episode.getShow(), thrwbl);
		}
	    });
	}
	/*
	 * Always have the join thread do some work as well, use up it's CPU
	 * time slice/quantum
	 * 
	 */
	final Episode episode = shows.get(0);
	try {
	    ResponseEntity<ShowResult> response = asyncRestTemplate.getRestOperations().exchange(
		    endPoint + episode.getShow().getId().toString(), HttpMethod.GET, headers, ShowResult.class);
	    ShowResult body = response.getBody();
	    body.setEpisode(episode);
	    body.filter();
	    results.add(body);
	} catch (Exception ex) {
	    LOGGER.info(ex);
	}
	for (ListenableFuture<?> future : futures) {
	    try {
		future.get(1_000L, TimeUnit.MILLISECONDS); // we naiively block,
							   // but we're a joiner
	    } catch (TimeoutException timeout) {
		LOGGER.info(timeout);
		future.cancel(true);
	    } catch (Exception ignore) {

	    }
	}

	LOGGER.info("{}", results);
	LOGGER.info("episodes -> {}", results.stream().flatMap(sr -> sr.getEpisode_ids().stream()).distinct().count());
	return results;
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
