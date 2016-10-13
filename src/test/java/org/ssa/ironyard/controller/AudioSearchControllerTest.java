package org.ssa.ironyard.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.ssa.ironyard.service.AudiosearchAuthorizationService;
import org.ssa.ironyard.service.AudiosearchService;

public class AudioSearchControllerTest {

    @Test
    public void test() {
	AudioSearchController asc = new AudioSearchController(new AudiosearchService(new AudiosearchAuthorizationService()));
	Map<String, Object> map = new HashMap<>();
	map.put("size", 42);
	asc.searchEpisodes(map);
    }

}
