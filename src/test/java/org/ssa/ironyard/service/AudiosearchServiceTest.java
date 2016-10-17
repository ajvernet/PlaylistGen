package org.ssa.ironyard.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.Genre;
import org.ssa.ironyard.service.mapper.SearchResults;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AudiosearchServiceTest {

    private static AudiosearchService ass;
    private static ObjectMapper mapper;
    
    String keyword;

    
    @BeforeClass
    public static void initServices() {
	ass = new AudiosearchService(new AudiosearchAuthorizationService());
	mapper = new ObjectMapper(); // can reuse, share globally
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

   
    @Before
    public void setup() {
        keyword = "a";
    }

    @Test
    public void searchEpisodesAltTest()
    {
        assertTrue(ass.searchEpisodesAlt(null,keyword, 50).size() >= 0);
        assertTrue(ass.searchEpisodesAlt(null, keyword, 50).size() < 51);
       
    }
    
    @Test
    public void searchEpisodesWithGenre()
    {
        assertTrue(ass.searchEpisodesAlt(Genre.ARTS.getName(), keyword, 50).size() >= 0);
        assertTrue(ass.searchEpisodesAlt(Genre.ARTS.getName(), keyword, 50).size() < 51);
        assertTrue(ass.searchEpisodesAlt(Genre.ARTS.getName(), keyword, 50).size() <
                ass.searchEpisodesAlt(null, keyword, 50).size());

    }

    @Test
    public void getGenresTest() 
    {   
        for(String s : ass.getGenres())
            assertTrue(Genre.getInstance(s).getClass().equals(Genre.class));
    }
    
    @Test
    public void testTasties(){
	System.out.println(ass.getTasties());
    }

}
