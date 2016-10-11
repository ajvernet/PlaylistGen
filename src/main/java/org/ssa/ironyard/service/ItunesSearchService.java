package org.ssa.ironyard.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ItunesSearchService {
    private static String iTunesSearchUri = "https://itunes.apple.com/search?&media=podcast&entity=podcast";
    Logger LOGGER = LogManager.getLogger(ItunesSearchService.class);
    
    public String searchByTerm(String term) {
	LOGGER.debug("Searching iTunes store");
	RestTemplate restTemplate = new RestTemplate();
	ResponseEntity<String> result;
	return restTemplate.getForObject(iTunesSearchUri + "&term="+ term, String.class).replace("\n", "").replace("\\\"", "");
    }
}
