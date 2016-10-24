
package org.ssa.ironyard.service.sandbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.ssa.ironyard.model.AuthToken;
import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.Show;
import org.ssa.ironyard.service.AudiosearchAuthorizationService;
import org.ssa.ironyard.service.AudiosearchService;
import org.ssa.ironyard.service.AudiosearchServiceImpl;
import org.ssa.ironyard.service.mapper.ShowResult;

/**
 *
 * @author thurston
 */
public class AsyncAudioService implements AudiosearchService
{
    static final Logger LOGGER = LogManager.getLogger(AudiosearchService.class);

    private final static String apiBaseUri = "https://www.audiosear.ch/api";
    final AudiosearchAuthorizationService authorizer;
    final AuthToken token;
    final AsyncRestTemplate asyncTemplate;
    public AsyncAudioService(AudiosearchAuthorizationService authorizer)
    {
        this.authorizer = authorizer;
        this.token = authorizer.getAccessToken();
        this.asyncTemplate = new AsyncRestTemplate();
    }

    
    @Override
    public List<Episode> searchEpisodesAlt(String genre, String searchText, Integer size)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Episode> getTasties()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getGenres()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    /**
     * This is the one I want to implement
     */
    public List<Episode> getNewShowsByUserId(Integer userId)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    HttpEntity<String> getHeaders() {
	HttpHeaders headers = new HttpHeaders();
	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	headers.setContentType(MediaType.APPLICATION_JSON);
	headers.set("Authorization", "Bearer " + this.token.getAccess_token());
	HttpEntity<String> entity = new HttpEntity<>(headers);
	//LOGGER.debug(headers.toString());
	return entity;
    }
    
    
    void testShows(List<Episode> shows) throws Exception
    {
        LOGGER.info("Starting #testShows");
        final String endPoint = apiBaseUri + "/shows/";
        final AsyncRestTemplate asyncRestTemplate = this.asyncTemplate;
        
        
        final Queue<ShowResult> results = new ConcurrentLinkedQueue<>();
        final List<ListenableFuture<ResponseEntity<ShowResult>>> futures = new ArrayList<>();
        
        final HttpEntity<String> headers = getHeaders();
        for (int index = 1; index < shows.size(); index++)
        {
            final Episode episode = shows.get(index);
            ListenableFuture<ResponseEntity<ShowResult>> futureResponse = asyncRestTemplate.exchange(endPoint + episode.getShow().getId().toString(), 
                                                                                                     HttpMethod.GET, 
                                                                                                     headers,
                                                                                                     ShowResult.class);
            futures.add(futureResponse);
            futureResponse.addCallback(new ListenableFutureCallback<ResponseEntity<ShowResult>>() 
                {
                    @Override
                    public void onSuccess(ResponseEntity<ShowResult> response)
                    {
                        ShowResult sr = response.getBody();
                        sr.setEpisode(episode);
                        sr.filter();
                        results.add(sr);
                    }

                    @Override
                    public void onFailure(Throwable thrwbl)
                    {
                        LOGGER.warn("show request {} failed -> {}", episode.getShow(), thrwbl);
                    }
                });
        }
        /*
        *  Always have the join thread do some work as well, use up it's CPU time slice/quantum
        *  
        */
        final Episode episode = shows.get(0);
        try
        {
            ResponseEntity<ShowResult> response = asyncRestTemplate.getRestOperations().exchange(endPoint + episode.getShow().getId().toString(), 
                                                                                                 HttpMethod.GET,
                                                                                                 headers,
                                                                                                 ShowResult.class);
            ShowResult body = response.getBody();
            body.setEpisode(episode);
            body.filter();
            results.add(body);
        }
        catch (Exception ex)
        {
            LOGGER.info(ex);
        }
        for (ListenableFuture<?> future : futures)
        {
            try
            {
                future.get(1_000L, TimeUnit.MILLISECONDS); // we naiively block, but we're a joiner
            }
            catch (TimeoutException timeout)
            {
                LOGGER.info(timeout);
                future.cancel(true);
            }
            catch (Exception ignore)
            {
                
            }
        }
        
        LOGGER.info("{}", results);
        LOGGER.info("episodes -> {}", results.stream().flatMap(sr -> sr.getEpisode_ids().stream()).distinct().count());
        
    }
}