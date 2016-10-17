package org.ssa.ironyard.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.ssa.ironyard.service.AudiosearchAuthorizationService;
import org.ssa.ironyard.service.AudiosearchService;

public class AudiosearchControllerTest {

    @Test
    public void test() {
	AudiosearchController asc = new AudiosearchController(new AudiosearchService(new AudiosearchAuthorizationService()));
	Map<String, Object> map = new HashMap<>();
	map.put("size", 42);
	asc.searchEpisodes(map);
    }

}
