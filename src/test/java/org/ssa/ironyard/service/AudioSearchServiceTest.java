package org.ssa.ironyard.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.service.mapper.SearchResults;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AudioSearchServiceTest {

    private static AudiosearchService ass;
    private static ObjectMapper mapper;

    @BeforeClass
    public static void initServices() {
	ass = new AudiosearchService(new AudiosearchAuthorizationService());
	mapper = new ObjectMapper(); // can reuse, share globally
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Before
    public void setup() {
    }

    @Ignore
    @Test
    public void testTasties() {
	ass.getTasties();
    }
    
    @Test
    public void testSearch(){
	String genre="Comedy";
	String query = "Hardcore History";
	Integer size = 100;
	
	System.err.println(ass.searchEpisodes(genre, query, size));
	
    }

    @Test
    public void getGenreList() {
	assertTrue(ass.getGenres().size() > 0);
	assertTrue(ass.getGenres().size() == 9);
	System.err.println(ass.getGenres());
    }

//    private String findmp3(String genreJson) throws JsonProcessingException, IOException {
//	JsonNode node = mapper.readTree(genreJson);
//	System.err.println(node.path("id"));
//	node = mapper.readTree(ass.getEpisodeById(Integer.parseInt(node.path("id").asText())));
//	System.err.println("Audio file: " + node.path("audio_files").get(0).path("url").get(0).asText());
//	if (node.has("audio_files"))
//	    if (node.path("audio_files").get(0).has("url"))
//		if (node.path("audio_files").get(0).path("url").get(0).asText().endsWith("mp3"))
//		    return node.path("audio_files").get(0).path("url").get(0).asText();
//	return "";
//    }

}
