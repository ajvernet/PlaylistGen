package org.ssa.ironyard.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ssa.ironyard.model.ResponseObject;
import org.ssa.ironyard.model.ResponseObject.STATUS;
import org.ssa.ironyard.service.AudioSearchService;

@RestController
@RequestMapping("/podcasts")
public class AudioSearchController {
    static final Logger LOGGER = LogManager.getLogger(AudioSearchController.class);

    private final AudioSearchService ass;

    @Autowired
    public AudioSearchController(AudioSearchService ass) {
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
	LOGGER.debug("Contacting AudioSearch for trending podcasts");
	return ResponseEntity.ok()
		.body(ResponseObject.instanceOf(STATUS.SUCCESS, "Daily Chart results returned", ass.getChartDaily()));
    }
}
