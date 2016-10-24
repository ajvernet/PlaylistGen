package org.ssa.ironyard.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.ResponseObject;
import org.ssa.ironyard.model.ResponseObject.STATUS;
import org.ssa.ironyard.service.AudiosearchService;

@RestController
@RequestMapping("/podcasts")
public class AudiosearchController {
    static final Logger LOGGER = LogManager.getLogger(AudiosearchController.class);

    private final AudiosearchService ass;

    @Autowired
    public AudiosearchController(AudiosearchService ass) {
	this.ass = ass;
    }

    @RequestMapping(value = "search", method = RequestMethod.POST)
    ResponseEntity<ResponseObject> searchEpisodes(@RequestBody Map<String, Object> map) {
	String query = map.get("query") == null ? "*"
		: ((String) map.get("query")).isEmpty() ? "*" : (String) map.get("query");
	Integer limit = map.get("size") == null ? 50
		: ((Integer) map.get("size") > 0 && (Integer) map.get("size") <= 100) ? (Integer) map.get("size") : 50;
	LOGGER.debug("Search AudioSearch by keyword");
	List<Episode> episodes = new ArrayList<>(); 
	String msg = "You got search results";
	STATUS status = STATUS.SUCCESS;
	try{
	    episodes = ass.searchEpisodesAlt((String) map.get("genre"), query, limit);
	}catch(RestClientException r){
	    msg = r.getMessage();
	    status = STATUS.ERROR;
	}
	if(episodes.size()==0)
	    msg = "Search returned no results";
	return ResponseEntity.ok().body(ResponseObject.instanceOf(status, msg, episodes));
    }

    @RequestMapping(value = "genres", method = RequestMethod.GET)
    ResponseEntity<ResponseObject> getGenres() {
	return ResponseEntity.ok()
		.body(ResponseObject.instanceOf(STATUS.SUCCESS, "Current genres retrieved", ass.getGenres()));
    }
    
    @RequestMapping(value="tasties", method=RequestMethod.GET)
    ResponseEntity<ResponseObject> getTasties(){
	STATUS status = STATUS.SUCCESS;
	String msg = "Tasties results returned";
	List<Episode> episodes = new ArrayList<>();
	try{
	episodes = ass.getTasties();}
	catch (Exception e){
	    status = STATUS.ERROR;
	    msg = e.getMessage();
	}
	return ResponseEntity.ok()
		.body(ResponseObject.instanceOf(status, msg, episodes));
    }
    
    @RequestMapping(value="user/{userId}/newshows")
    ResponseEntity<ResponseObject> getNewShows(@PathVariable Integer userId){STATUS status = STATUS.SUCCESS;
	String msg = "New(er) episodes returned";
	List<Episode> episodes = new ArrayList<>();
	try{
	    episodes = ass.getNewShowsByUserId(userId);
	}catch(Exception e){
	    status = STATUS.ERROR;
	    msg = e.getMessage();
	}
	return ResponseEntity.ok()
		.body(ResponseObject.instanceOf(status, msg, episodes));
    }

}
