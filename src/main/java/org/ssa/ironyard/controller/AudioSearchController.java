package org.ssa.ironyard.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ssa.ironyard.model.ResponseObject;
import org.ssa.ironyard.model.ResponseObject.STATUS;
import org.ssa.ironyard.service.AudiosearchService;

@RestController
@RequestMapping("/podcasts")
public class AudioSearchController {
    static final Logger LOGGER = LogManager.getLogger(AudioSearchController.class);

    private final AudiosearchService ass;

    @Autowired
    public AudioSearchController(AudiosearchService ass) {
	this.ass = ass;
    }

    @RequestMapping("episodes/trending")
    ResponseEntity<ResponseObject> getTrending() {
	LOGGER.debug("Contacting AudioSearch for trending podcasts");
	return ResponseEntity.ok()
		.body(ResponseObject.instanceOf(STATUS.SUCCESS, "Trending results returned", ass.getTrending()));
    }

    @RequestMapping("shows/chartdaily")
    ResponseEntity<ResponseObject> getChartDaily() {
	LOGGER.debug("Contacting AudioSearch for charting shows");
	return ResponseEntity.ok()
		.body(ResponseObject.instanceOf(STATUS.SUCCESS, "Daily Chart results returned", ass.getChartDaily()));
    }
    
    @RequestMapping("episodes/random")
    ResponseEntity<ResponseObject> getRandomEpisode() {
	LOGGER.debug("Contacting AudioSearch for random podcast");
	return ResponseEntity.ok()
		.body(ResponseObject.instanceOf(STATUS.SUCCESS, "Random Episode returned", ass.getRandomEpisode()));
    }
    
    @RequestMapping("episodes/search/{query}")
    ResponseEntity<ResponseObject> getEpisodesByKeywords(@PathVariable String query){
	LOGGER.debug("Search AudioSearch by keyword");
	return ResponseEntity.ok()
		.body(ResponseObject.instanceOf(STATUS.SUCCESS, "You got search results", ass.searchEpisodesByKeywords(query)));
    }
    
}
