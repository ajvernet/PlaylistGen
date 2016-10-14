package org.ssa.ironyard.controller;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "search", method = RequestMethod.POST)
    ResponseEntity<ResponseObject> searchEpisodes(@RequestBody Map<String, Object> map) {
	String query = map.get("query") == null ? "*"
		: ((String) map.get("query")).isEmpty() ? "*" : (String) map.get("query");
	Integer limit = map.get("size") == null ? 50
		: ((Integer) map.get("size") > 0 && (Integer) map.get("size") <= 100) ? (Integer) map.get("size") : 50;
	LOGGER.debug("Search AudioSearch by keyword");
	return ResponseEntity.ok().body(ResponseObject.instanceOf(STATUS.SUCCESS, "You got search results",
		ass.searchEpisodes((String) map.get("genre"), query, limit)));
    }

    @RequestMapping(value = "genres", method = RequestMethod.GET)
    ResponseEntity<ResponseObject> getGenres() {
	return ResponseEntity.ok()
		.body(ResponseObject.instanceOf(STATUS.SUCCESS, "Current genres retrieved", ass.getGenres()));
    }

}
